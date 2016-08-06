package me.gking2224.model.controller;

import static me.gking2224.model.controller.ModelAdminController.MANIFESTS;
import static me.gking2224.model.controller.ModelAdminController.MODELS;
import static me.gking2224.model.controller.ModelAdminController.TYPES;
import static me.gking2224.utils.test.TestUtils.jsonFilterPath;
import static me.gking2224.utils.test.mvc.JsonMvcTestHelper.doGet;
import static me.gking2224.utils.test.mvc.JsonMvcTestHelper.doPost;
import static me.gking2224.utils.test.mvc.JsonMvcTestHelper.responseContent;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.ModelManifest;
import me.gking2224.model.jpa.ModelType;
import me.gking2224.model.jpa.ModelVariable;
import me.gking2224.model.jpa.ModelVariable.Direction;
import me.gking2224.model.jpa.Version.IncrementType;
import me.gking2224.model.mvc.WebAppTestConfigurer;
import me.gking2224.model.utils.JsonUtil;
import me.gking2224.utils.test.mvc.JsonMvcTestHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(classes=WebAppTestConfigurer.class)
@Transactional
@Rollback
@Sql
public class ModelAdminControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    private JsonUtil json;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        json = new JsonUtil();
    }

    @Test
    public void testGetTypes() throws Exception {
        ResultActions result = doGet(this.mockMvc, TYPES, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.length()").value(greaterThan(0)))
            .andDo((m)->assertEquals("/model/admin/type/100", json.getFilterValue(responseContent(m), "$.[?(@.name=='Model Type')].location")));
    }

    @Test
    public void testGetTypesWithExpect() throws Exception {
        ResultActions result = doGet(this.mockMvc, TYPES, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.length()").value(greaterThan(0)))
            .andExpect(jsonFilterPath("$.[?(@.name=='Model Type')]").arrayValue(arrayWithSize(1)))
            .andExpect(jsonFilterPath("$.[?(@.name=='Model Type')].location").singleValue(equalTo("/model/admin/type/100")));
    }

    @Test
    public void testCreateModelType() throws Exception {
        ModelType mt = new ModelType("NewModelType");
        ResultActions result = JsonMvcTestHelper.doPost(this.mockMvc, mt, TYPES, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.name").value("NewModelType"))
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.location").isNotEmpty());
    }

    @Test
    public void testCreateModelType_ExistingConflict() throws Exception { // test failing as integrity constraints not applied until commit(?)
        ModelType mt = new ModelType("Demonstration"); // TODO change this to a persistent, static value loaded in real build(e.g. 'Default')
        ResultActions result = JsonMvcTestHelper.doPost(this.mockMvc, mt, TYPES, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.errorCode").value(200));
        ResultActions result2 = JsonMvcTestHelper.doPost(this.mockMvc, mt, TYPES, JsonMvcTestHelper::expect4xx);
        result2
            .andExpect(jsonPath("$.errorCode").value(409));
    }
    
    ResultActions checkError(ResultActions r) {
        return r;
    }

    @Test
    public void testNewModelManifest() throws Exception {
        ModelType mt = new ModelType(100L, "Model Type");
        ModelManifest mf = new ModelManifest(200L, "NewModel", mt);
        ResultActions result = doPost(this.mockMvc, mf, MANIFESTS, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.name").value("NewModel"))
            .andExpect(jsonPath("$.type.name").value("Model Type"))
            .andExpect(jsonPath("$.type.id").value(100))
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.location").isNotEmpty());
    }

    @Test
    public void testNewModel() throws Exception {
        ModelType mt = new ModelType(100L, "Model Type");
        ModelManifest mf = new ModelManifest(200L, "Test Model", mt);
        Model m = new Model();
        m.setManifest(mf);
        m.addVariable(new ModelVariable("existingVar", "java.lang.Integer", Direction.IN));
        ModelCreationRequest req = new ModelCreationRequest();
        req.setEntity(m);
        req.setIncrementType(IncrementType.MAJOR);
        ResultActions result = doPost(this.mockMvc, req, MODELS, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.location").isNotEmpty());
    }

//    @Test
//    public void testNewModelJson_NewVariable() throws Exception {
//        String json = "{\"versionIncrement\":\"MINOR\",\"entity\":{\"natures\":[\"groovy.shell.v2\"],\"variables\":[{\"mandatory\":true,\"direction\":\"IN\",\"name\":\"testModelJsonNewVar\",\"type\":\"java.lang.Integer\"}],\"manifest\":{\"name\":\"Test Model\",\"type\":{\"name\":\"Model Type\"}},\"version\":{\"majorVersion\":0,\"minorVersion\":7},\"script\":\"def executeModel() { output.givenValue = input.a  }\"}}";
//        
//        this.mockMvc.perform(
//                post(ModelAdminController.MODELS)
//                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                .content(json)
//                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType("application/json"))
//            .andExpect(jsonPath("$.id").isNotEmpty());
//    }
//
//    @Test
//    public void testNewModelJson_ExistingVariable() throws Exception {
//        String json = "{\"versionIncrement\":\"MINOR\",\"entity\":{\"natures\":[\"groovy.shell.v2\"],\"variables\":[{\"mandatory\":true,\"direction\":\"IN\",\"name\":\"existingVar\",\"type\":\"java.lang.String\"}],\"manifest\":{\"name\":\"Test Model\",\"type\":{\"name\":\"Model Type\"}},\"version\":{\"majorVersion\":0,\"minorVersion\":7},\"script\":\"def executeModel() { output.givenValue = input.a  }\"}}";
//        
//        this.mockMvc.perform(
//                post(ModelAdminController.MODELS)
//                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                .content(json)
//                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType("application/json"))
//            .andExpect(jsonPath("$.id").isNotEmpty());
//    }

}