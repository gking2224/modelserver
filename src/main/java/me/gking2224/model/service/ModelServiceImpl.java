package me.gking2224.model.service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.model.execution.ModelExecutionException;
import me.gking2224.model.execution.ModelExecutor;
import me.gking2224.model.execution.ModelExecutorProvider;
import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.ModelManifestRepository;
import me.gking2224.model.jpa.ModelRepository;
import me.gking2224.model.jpa.ModelTypeRepository;
import me.gking2224.model.jpa.ModelVariable;
import me.gking2224.model.jpa.ModelVariable.Direction;
import me.gking2224.model.jpa.Version;

@Component
@Transactional
public class ModelServiceImpl implements ModelService {

    // 0-99    initialization
    // 100-199 inputs
    // 200-299 execution (model executor)
    // 300-399 post-execution
    // 400+    script domain
    private static final int MISSING_MANDATORY_OUTPUT_WARNING = 401;

    @Autowired
    DateTimeFormatter dateTimeFormatter;
    
    @Autowired
    protected ModelRepository modelRepository;
    
    @Autowired
    protected ModelManifestRepository manifestRepository;
    
    @Autowired
    protected ModelTypeRepository typeRepository;
    
    @Autowired
    protected ModelExecutorProvider modelExecutionHandler;
    
    @Autowired ConfigurableConversionService conversionService;

    private Map<String,String> javaTypeAliases = new HashMap<String,String>();

    @Override
    public ModelExecutionResponse executeModel(ModelExecutionRequest request) {
        Long modelId = request.getModelId();
        return executeModel(modelId, request);
    }
    
    public ModelServiceImpl() {
        javaTypeAliases.put("int", "java.lang.Integer");
        javaTypeAliases.put("string", "java.lang.String");
        javaTypeAliases.put("double", "java.lang.Double");
        javaTypeAliases.put("float", "java.lang.Float");
        javaTypeAliases.put("char", "java.lang.Character");
        javaTypeAliases.put("boolean", "java.lang.Boolean");
    }

    @Override
    public ModelExecutionResponse executeModel(Long modelId, ModelExecutionRequest request) {
        ModelExecutionResponse response = new ModelExecutionResponse();
        try {
            captureRequestTime(response);
            Model model = modelRepository.findOne(modelId);
            innerExecuteModel(model, request, response);

        }
        catch (Throwable t) {
            response.captureException(t, request.isTraverseNestedExceptions());
        }
        finally {
            captureRequestDuration(response);
        }
        return response;
    }

    @Override
    public ModelExecutionResponse executeModel(
            String name, String type, Version version, ModelExecutionRequest request) {
        ModelExecutionResponse response = new ModelExecutionResponse();
        try {
            captureRequestTime(response);
            Model model = modelRepository.findByManifestTypeNameAndManifestNameAndVersionMajorVersionAndVersionMinorVersion(
                    type, name, version.getMajorVersion(), version.getMinorVersion());
            innerExecuteModel(model, request, response);
        }
        catch (Throwable t) {
            response.captureException(t, request.isTraverseNestedExceptions());
        }
        finally {
            captureRequestDuration(response);
        }
        return response;
    }
    
    protected void innerExecuteModel(
            Model model, ModelExecutionRequest request, final ModelExecutionResponse response) {

        if (model == null ) {
            throw new NullPointerException("Model could not be found"+request.getModelId());
        }
        this.checkInputsAndExecuteModel(model, request, response);

        response.setSuccess(true);
    }
    
    protected void checkInputsAndExecuteModel(
            Model model, ModelExecutionRequest request, final ModelExecutionResponse response) {
        
        Set<ModelVariable> variables = model.getVariables();
        Map<Direction, List<ModelVariable>> mandatoryVariables = variables.stream()
                .filter(v->v.isMandatory())
                .collect(Collectors.groupingBy(ModelVariable::getDirection));
        Map<Direction, List<ModelVariable>> allVariables = variables.stream()
                .collect(Collectors.groupingBy(ModelVariable::getDirection));
        
        validateMandatoryInputVariables(mandatoryVariables, request, response);
        
        resolveInputTypes(allVariables, request.getInputParams(), response);
        
        findExecutorAndExecuteModel(model, request, response);
        
        validateMandatoryVariables(mandatoryVariables, response);
        
        resolveOutputTypes(allVariables, response);
        
    }
    
    private void resolveOutputTypes(Map<Direction, List<ModelVariable>> allVariables, ModelExecutionResponse response) {
        resolveTypes(allVariables.get(Direction.INOUT), response.getOutputs());
    }

    private void validateMandatoryVariables(Map<Direction, List<ModelVariable>> mandatoryVariables,
            ModelExecutionResponse response) {
        validateMandatoryVariables(mandatoryVariables.get(Direction.OUT), response.getOutputs(), response.getWarnings());
        validateMandatoryVariables(mandatoryVariables.get(Direction.INOUT), response.getOutputs(), response.getWarnings());
    }

    private void resolveInputTypes(Map<Direction, List<ModelVariable>> allVariables, Map<String, Object> inputParams, ModelExecutionResponse response) {
        
        resolveTypes(allVariables.get(Direction.IN), inputParams);
        resolveTypes(allVariables.get(Direction.INOUT), inputParams);
    }

    protected void validateMandatoryInputVariables(
            Map<Direction, List<ModelVariable>> mandatoryVariables,
            ModelExecutionRequest request,
            ModelExecutionResponse response) {
        
        validateMandatoryVariables(mandatoryVariables.get(Direction.IN), request.getInputParams(), null);
        validateMandatoryVariables(mandatoryVariables.get(Direction.INOUT), request.getInputParams(), null);
    }

    protected void findExecutorAndExecuteModel(Model model, ModelExecutionRequest request,
            ModelExecutionResponse response) {

        ModelExecutor executor = modelExecutionHandler.getExecutor(model, request);
        response.setExecutorClassName(executor.getClass().getName());
        executor.executeModel(model, request, response);
    }

    protected void captureRequestTime(ModelExecutionResponse response) {
        Instant requestTime = Clock.systemUTC().instant();
        ZonedDateTime atZone = requestTime.atZone(ZoneId.systemDefault());
        response.setRequestTime(dateTimeFormatter.format(atZone));
        response.setRequestTimeAsInstant(requestTime);
    }
    
    protected void captureRequestDuration(ModelExecutionResponse response) {
        response.setDuration(Duration.between(response.getRequestTimeAsInstant(), Clock.systemUTC().instant()));
    }

    private void resolveTypes(List<ModelVariable> variables, Map<String, Object> map) {
        if (variables == null) return;
        variables.stream()
            .filter(v->v.getDirection() != Direction.OUT)
            .forEach((v)->convertType(v, map));
        
    }
    
    private void convertType(ModelVariable variable, Map<String, Object> map) {
        Object value = map.get(variable.getName());
        Object converted = coerceType(value, variable.getType());
        map.put(variable.getName(), converted);
    }
    

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Object coerceType(Object value, String type) {
        if (value == null) return null;
        try {
            String javaType = toJavaType(type);
            Class clazz = Class.forName(javaType);
            Object rv = conversionService.convert(value, clazz);
            return rv;
        } catch (ClassNotFoundException e) {
            throw new ModelExecutionException(e);
        }
    }

    private String toJavaType(String type) {
        String converted = javaTypeAliases.get(type);
        return (converted == null)?type:converted;
    }

    private void validateMandatoryVariables(Collection<ModelVariable> variables, Map<String, Object> map, List<ModelExecutionWarning> warnings) {
        if (variables == null) return;
        variables.stream().forEach(v->validateMandatoryInput(v, map, warnings));
    }

    private void validateMandatoryInput(ModelVariable v, Map<String, Object> map, List<ModelExecutionWarning> warnings) {
        String name = v.getName();
        Direction d = v.getDirection();
        if (!map.containsKey(name) || "".equals(map.get(name)) || map.get(name) == null) {
            if (warnings != null) {
                warnings.add(new ModelExecutionWarning(
                        MISSING_MANDATORY_OUTPUT_WARNING, 
                        "Mandatory variable missing",
                        d.toString()+" variable '"+name+"' is missing"));
            }
            else {
                throw new ModelExecutionException("Missing mandatory variable: "+name);
            }
        }
    }
}
