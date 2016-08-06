package me.gking2224.model.controller;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import me.gking2224.model.jpa.Version;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;
import me.gking2224.model.service.ModelService;

@org.springframework.web.bind.annotation.RestController
public class ModelController {

    static final String EXECUTE = "/model/execute";

    private static Logger logger = LoggerFactory.getLogger(ModelController.class);
	
	@Autowired
	ModelService modelService;
	
    @RequestMapping(value=EXECUTE, method=RequestMethod.POST, consumes=APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ModelExecutionResponse> execute(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam Version version,
            @RequestParam ModelExecutionRequest request
            ) {
        logger.debug(EXECUTE);
        
        ModelExecutionResponse m = modelService.executeModel(name, type, version, request);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new ResponseEntity<ModelExecutionResponse>(m, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value=EXECUTE, method=RequestMethod.POST, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelExecutionResponse> executeJson(
            @RequestBody ModelExecutionRequest request
            ) {
        logger.debug(EXECUTE);
        
        ModelExecutionResponse m = modelService.executeModel(request);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new ResponseEntity<ModelExecutionResponse>(m, headers, HttpStatus.OK);
    }

}
