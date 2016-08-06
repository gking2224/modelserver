package me.gking2224.model.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.ModelManifest;
import me.gking2224.model.jpa.ModelType;
import me.gking2224.model.jpa.Version;
import me.gking2224.model.service.ModelAdminService;
import me.gking2224.model.utils.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class ModelAdminController {

    static final String TYPES = "/model/admin/types";
    static final String TYPE_STEM = "/model/admin/type/";
    static final String TYPE      = TYPE_STEM+"{id}";
    
    static final String MANIFESTS =       "/model/admin/manifests";
    static final String MANIFEST_STEM =       "/model/admin/manifest/";
    static final String MANIFEST =       MANIFEST_STEM+"{id}";
    static final String SEARCH_MANIFESTS_BY_TYPE = "/model/admin/manifests/type/{type}";

    static final String MODELS =             "/model/admin/models";
    static final String MODEL_STEM =             "/model/admin/model/";
    static final String MODEL      = MODEL_STEM+"{id}";
    static final String SEARCH_VERSIONS_BY_MANIFEST = "/model/admin/models/manifest/{manifestId}";
    
    static final String FIND_MODEL_BY_TYPE_NAME_AND_VERSION = "/model/admin/model/type/{type}/name/{name}/version/{version}";
    
    private static Logger logger = LoggerFactory.getLogger(ModelAdminController.class);
	
	@Autowired
	ModelAdminService adminService;
	
	@Autowired
	JsonUtil jsonUtil;
    
	// get types (all)
    @RequestMapping(value=TYPES, method=RequestMethod.GET)
    public ResponseEntity<List<ModelType>> getAllTypes(
    ) {
        List<ModelType> mt = adminService.findAllModelTypes();
        mt = mt.stream().map(this::enrichType).collect(toList());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<List<ModelType>>(mt, headers, HttpStatus.OK);
    }
	
	// create type (post to types)
    @RequestMapping(value=TYPES, method=RequestMethod.POST, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelType> newType(
            @RequestBody ModelType type) {

        ModelType mt = adminService.createModelType(type);
        mt = enrichType(mt);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<ModelType>(mt, headers, HttpStatus.OK);
    }
    
    // update type (put at type)
    @RequestMapping(value=TYPE, method=RequestMethod.PUT, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelType> modifyType(
            @PathVariable("id") final Long id,
            @RequestBody final ModelType type) {
        Long typeId = type.getId();
        if (typeId == null) type.setId(id);
        else if (typeId != id) 
            throw new IllegalArgumentException("Illegal attempt to change immutable field (id)");
        ModelType mt = adminService.updateModelType(type);
        mt = enrichType(mt);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<ModelType>(mt, headers, HttpStatus.OK);
    }
    
    // delete type by id
    @RequestMapping(value=TYPE, method=RequestMethod.DELETE, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteType(
            @PathVariable("id") final Long id) {
        logger.debug(TYPE);
        
        adminService.deleteModelType(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
    
    // get type by id
    @RequestMapping(value=TYPE, method=RequestMethod.GET)
    public ResponseEntity<ModelType> getType(
            @PathVariable("id") final Long id) {
        ModelType mt = adminService.findTypeById(id);
        mt = enrichType(mt);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<ModelType>(mt, HttpStatus.OK);
    }

    private ModelType enrichType(ModelType m) {
        m.setLocation(TYPE_STEM + m.getId());
        return m;
    }

    @RequestMapping(value=SEARCH_MANIFESTS_BY_TYPE, method=RequestMethod.GET)
    public ResponseEntity<List<ModelManifest>> getManifests(
            @PathVariable("type") String typeName
    ) {
        List<ModelManifest> mt = adminService.findModelManifestByType(typeName);
        mt.stream().map(this::enrichManifest).collect(toList());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<List<ModelManifest>>(mt, headers, HttpStatus.OK);
    }
    
    ModelManifest enrichManifest(ModelManifest mm) {
        mm.setLocation(MANIFEST_STEM+mm.getId());
        return mm;
    }
    
    @RequestMapping(value=MANIFESTS, method=RequestMethod.POST, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelManifest> newModelManifest(
            @RequestBody final ModelManifest manifest) {
        ModelManifest mm = adminService.createModelManifest(manifest);
        mm = enrichManifest(mm);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<ModelManifest>(mm, headers, HttpStatus.OK);
    }
    
    // update manifest (put at manifest)
    @RequestMapping(value=MANIFEST, method=RequestMethod.PUT, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelManifest> modifyManifest(
            @PathVariable("id") final Long id,
            @RequestBody final ModelManifest manifest) {
        logger.debug(MANIFEST);
        
        Long manifestId = manifest.getId();
        if (manifestId == null) manifest.setId(id);
        else if (manifestId != id) 
            throw new IllegalArgumentException("Illegal attempt to change immutable field (id)");
        ModelManifest mt = adminService.updateModelManifest(manifest);
        mt = enrichManifest(mt);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<ModelManifest>(mt, headers, HttpStatus.OK);
    }
    
    // delete manifest by id
    @RequestMapping(value=MANIFEST, method=RequestMethod.DELETE, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteManifest(
            @PathVariable("id") final Long id) {
        adminService.deleteModelManifest(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
    
    // get manifest by id
    @RequestMapping(value=MANIFEST, method=RequestMethod.GET)
    public ResponseEntity<ModelManifest> getManifest(
            @PathVariable("id") final Long id) {
        ModelManifest mt = adminService.findManifestById(id);
        mt = enrichManifest(mt);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<ModelManifest>(mt, HttpStatus.OK);
    }
    
    @RequestMapping(value=MODELS, method=RequestMethod.POST, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Model> createNewVersionFromJson(
            @RequestBody ModelCreationRequest request
            ) {
        Model model = request.cleanEntity();
        Model m = adminService.createNewModel(model, request.getIncrementType());
        m = enrichModel(m);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Model>(m, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value=SEARCH_VERSIONS_BY_MANIFEST, method=RequestMethod.GET)
    public ResponseEntity<List<Version>> getModelVersionsByTypeAndName(
            @PathVariable Long manifestId
            ) {
        List<Model> m = adminService.getModelsByManifest(manifestId);
        List<Version>v = m.stream().map(this::enrichVersion).collect(toList());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<List<Version>>(v, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value=FIND_MODEL_BY_TYPE_NAME_AND_VERSION, method=RequestMethod.GET)
    public ResponseEntity<Model> getModelByTypeNameAndVersion(
            @PathVariable("type") String typeName,
            @PathVariable String name,
            @PathVariable Version version
            ) {
        Model m = adminService.getModelByTypeAndNameAndVersion(typeName, name, version);
        m = enrichModel(m);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new ResponseEntity<Model>(m, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value=MODEL, method=RequestMethod.GET)
    public ResponseEntity<Model> getModelById(
            @PathVariable Long id
            ) {
        Model m = adminService.getModelById(id);
        m = enrichModel(m);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new ResponseEntity<Model>(m, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value=MODEL, method=RequestMethod.PUT)
    public ResponseEntity<Model> updateModel(
            @RequestBody Model model
            ) {
        Model m = adminService.updateModel(model);
        m = enrichModel(m);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new ResponseEntity<Model>(m, headers, HttpStatus.OK);
    }

    private Model enrichModel(Model m) {
        m.setLocation(MODEL_STEM+m.getId());
        return m;
    }

    private Version enrichVersion(Model m) {
        Version v = m.getVersion();
        v.setEnabled(m.isEnabled());
        v.setLocation(MODEL_STEM+m.getId());
        return v;
    }
}
