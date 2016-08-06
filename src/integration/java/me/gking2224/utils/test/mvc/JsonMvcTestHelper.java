package me.gking2224.utils.test.mvc;

import static org.junit.Assert.fail;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.function.Function;

import me.gking2224.model.controller.ModelAdminController;
import me.gking2224.model.jpa.ModelType;
import me.gking2224.model.utils.JsonUtil;
import net.minidev.json.JSONArray;

import org.junit.Assert;
import org.junit.internal.runners.statements.Fail;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;

public class JsonMvcTestHelper {

    private static JsonUtil json = new JsonUtil();

    public static ResultActions doGet(MockMvc mockMvc, String url, Function<ResultActions, ResultActions> checks) throws Exception {
        ResultActions result = mockMvc.perform(get(url).header(CONTENT_TYPE, APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE));
        return checks.apply(result);
    }

    public static ResultActions doPost(MockMvc mockMvc, Object obj, String url, Function<ResultActions, ResultActions> checks)
            throws JsonProcessingException, Exception {
        ResultActions result = mockMvc.perform(post(url).content(json.objectToJson(obj)).header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));
        return checks.apply(result);
    }
    public static ResultActions expect4xx(ResultActions t) {
        try {
            return t.andExpect(status().is4xxClientError()).andExpect(content().contentType(APPLICATION_JSON_VALUE));
        } catch (Exception e) {
            fail(e.getMessage());
            return null;
        }
    }
    public static ResultActions expectOK(ResultActions t) {
        try {
            return t.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_VALUE));
        } catch (Exception e) {
            fail(e.getMessage());
            return null;
        }
    }
    
    public static String responseContent(MvcResult m) throws UnsupportedEncodingException {
        return m.getResponse().getContentAsString();
    }
}
