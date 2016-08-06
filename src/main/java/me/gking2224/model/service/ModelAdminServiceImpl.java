package me.gking2224.model.service;

import java.util.List;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.ModelDao;
import me.gking2224.model.jpa.ModelManifest;
import me.gking2224.model.jpa.ModelManifestRepository;
import me.gking2224.model.jpa.ModelRepository;
import me.gking2224.model.jpa.ModelType;
import me.gking2224.model.jpa.ModelTypeRepository;
import me.gking2224.model.jpa.ModelVariable;
import me.gking2224.model.jpa.ModelVariableId;
import me.gking2224.model.jpa.Variable;
import me.gking2224.model.jpa.VariableRepository;
import me.gking2224.model.jpa.Version;
import me.gking2224.model.jpa.Version.IncrementType;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
public class ModelAdminServiceImpl implements ModelAdminService {

    @Autowired
    protected ModelDao modelDao;

    @Autowired
    protected ModelRepository modelRepository;
    
    @Autowired
    protected ModelManifestRepository manifestRepository;
    
    @Autowired
    protected ModelTypeRepository typeRepository;
    
    @Autowired
    protected VariableRepository variableRepository;

    @Override
    public ModelManifest createModelManifest(ModelManifest manifest) {
        ModelType type = typeRepository.findOne(manifest.getType().getId());
        manifest.setType(type);
        return manifestRepository.save(manifest);
    }

    @Override
    public Model createNewModel(Model model, IncrementType incType) {
        ModelManifest manifest = model.getManifest();
        Version v = modelDao.getNextVersion(manifest.getType().getName(), manifest.getName(), incType==IncrementType.MAJOR);
        model.setVersion(v);
        return createNewModel(model);
    }

    protected Model createNewModel(Model m) {
        ModelManifest manifest = manifestRepository.findOne(m.getManifest().getId());
        
        m.setManifest(manifest);
        for (ModelVariable mv : m.getVariables()) {
            Variable v = variableRepository.findByNameAndType(mv.getName(), mv.getType());
            if (v == null) {
                v = variableRepository.save(new Variable(mv.getName(), mv.getType()));
            }
            mv.setPk(new ModelVariableId(m, v));
        }
        
//        m.setVariables(variables);
        return modelRepository.save(m);
    }

    @Override
    public List<ModelType> findAllModelTypes() {
        return typeRepository.findAll();
    }

    @Override
    public List<ModelManifest> findModelManifestByType(String typeName) {
        return manifestRepository.findByTypeName(typeName);
    }

    @Override
    public List<Model> getModelsByManifest(Long manifestId) {
        return modelRepository.findByManifestId(manifestId);
    }

    @Override
    public Model getModelByTypeAndNameAndVersion(String typeName, String name, Version version) {
        Model m = modelRepository.findByManifestTypeNameAndManifestNameAndVersionMajorVersionAndVersionMinorVersion(
                typeName, name, version.getMajorVersion(), version.getMinorVersion());
        Hibernate.initialize(m.getVariables());
        return m;
    }

    @Override
    public Model getModelById(Long id) {
        Model m = modelRepository.findOne(id);
        Hibernate.initialize(m.getVariables());
        return m;
    }

    @Override
    public ModelType createModelType(ModelType type) {
        return typeRepository.save(type);
    }

    @Override
    public ModelType updateModelType(ModelType type) {
        return typeRepository.save(type);
    }

    @Override
    public void deleteModelType(Long id) {
        typeRepository.delete(id);
    }

    @Override
    public ModelType findTypeById(Long id) {
        return typeRepository.findOne(id);
    }

    @Override
    public ModelManifest updateModelManifest(ModelManifest manifest) {
        return manifestRepository.save(manifest);
    }

    @Override
    public void deleteModelManifest(Long id) {
        manifestRepository.delete(id);
    }

    @Override
    public ModelManifest findManifestById(Long id) {
        return manifestRepository.findOne(id);
    }

    @Override
    public Model updateModel(Model model) {
        return modelRepository.save(model);
    }
}
