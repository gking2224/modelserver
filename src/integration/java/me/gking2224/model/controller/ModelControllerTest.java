package me.gking2224.model.controller;

import static me.gking2224.model.controller.ModelController.EXECUTE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import me.gking2224.model.execution.ModelExecutionException;
import me.gking2224.model.utils.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(classes=me.gking2224.model.mvc.WebAppTestConfigurer.class)
@Transactional
@Rollback
public class ModelControllerTest {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    @Autowired
    private JsonUtil json;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Sql
    public void testExecuteModel() throws Exception {
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("testExecuteModel_a", "7");
        inputs.put("testExecuteModel_b", "9");
        request.put("inputArgs", inputs);
        
        this.mockMvc.perform(
        
                post(ModelController.EXECUTE)
                .param("type",  "Model Type")
                .param("name",  "Test Model")
                .param("version",  "1.0")
                .param("request",  json.mapToJson(request))
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isOk());
    }

    @Test
    @Sql("ModelControllerTest.testExecuteModel.sql")
    public void testExecuteModel_Json() throws Exception {
        String jsonRequest = "{\"modelId\":\"300\", \"inputParams\":{\"testExecuteModel_a\":\"7\",\"testExecuteModel_b\":\"9\"}}";
        this.mockMvc.perform(
                post(ModelController.EXECUTE)
                .content(jsonRequest)
                .header("Content-Type", APPLICATION_JSON_VALUE)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andDo((m)->printOutput(m))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Sql("ModelControllerTest.testExecuteModel.sql")
    public void testExecuteModel_MissingMandatory() throws Exception {
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("testExecuteModel_a", "7");
        request.put("inputArgs", inputs);
        
        this.mockMvc.perform(
                post(EXECUTE)
                .param("type",  "Model Type")
                .param("name",  "Test Model")
                .param("version",  "1.0")
                .param("request",  json.mapToJson(request))
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andDo((m)->printOutput(m))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.error.message").value("Missing mandatory variable: testExecuteModel_b"))
            .andExpect(jsonPath("$.error.exceptionClass").value(ModelExecutionException.class.getName()))
            .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Sql("ModelControllerTest.testExecuteModel.sql")
    public void testExecuteModel_WrongInputType() throws Exception {
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("testExecuteModel_a", "abc");
        inputs.put("testExecuteModel_b", "7");
        request.put("inputArgs", inputs);
        
        this.mockMvc.perform(
                post(ModelController.EXECUTE)
                .param("type",  "Model Type")
                .param("name",  "Test Model")
                .param("version",  "1.0")
                .param("request",  json.mapToJson(request))
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andDo((m)->printOutput(m))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.error.message").value("For input string: \"abc\""))
            .andExpect(jsonPath("$.error.exceptionClass").value(NumberFormatException.class.getName()))
            .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Sql()
    public void testExecuteModel_StaticDataProvider() throws Exception {
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("testExecuteModel_a", "3");
        inputs.put("testExecuteModel_b", "7");
        request.put("inputArgs", inputs);
        
        this.mockMvc.perform(
                post(ModelController.EXECUTE)
                .param("type",  "Model Type")
                .param("name",  "Test Model")
                .param("version",  "1.0")
                .param("request",  json.mapToJson(request))
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andDo((m)->printOutput(m))
            .andExpect(status().isOk());
    }

    void printOutput(MvcResult m) throws UnsupportedEncodingException {
        String content = m.getResponse().getContentAsString();
        logger.debug(content);
    }
}