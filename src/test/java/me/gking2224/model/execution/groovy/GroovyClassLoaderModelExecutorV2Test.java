package me.gking2224.model.execution.groovy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.WebApplicationContext;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.ModelManifest;
import me.gking2224.model.jpa.ModelType;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;
import me.gking2224.model.service.ModelExecutionWarning;

public class GroovyClassLoaderModelExecutorV2Test {

    private Model m;
    private GroovyClassLoaderModelExecutorV2 executor;
    private ModelExecutionRequest request;
    private ModelExecutionResponse response;
    private Map<String, Object> input;
    private Mockery mky;
    private WebApplicationContext wac;
    private ServletContext sc;
    protected URL dummyUrl;
    @Before
    public void setup() throws MalformedURLException {
        
        mky = new Mockery();
        wac = mky.mock(WebApplicationContext.class);
        sc = mky.mock(ServletContext.class);
        
        m = new Model();
        ModelManifest mm = new ModelManifest();
        ModelType mt = new ModelType();
        mm.setName("testModel");
        mt.setName("testType");
        mm.setType(mt);
        dummyUrl = new File(System.getProperty("user.home"), "Documents/Projects/STS-workspace2/modelserver/build/classes").toURI().toURL();
        m.setManifest(mm);
        
        executor = new GroovyClassLoaderModelExecutorV2();
        
        request = new ModelExecutionRequest();
        response = new ModelExecutionResponse();
        
        input = new HashMap<String,Object>();
        input.put("log.level", "TRACE");
        request.setInputParams(input);
        

        mky.checking(new Expectations() {{
            oneOf (wac).getServletContext(); will(returnValue(sc));
            oneOf (sc).getResource("/WEB-INF/classes"); will(returnValue(dummyUrl));
        }});
        executor.setApplicationContext(wac);
        
    }
    @After
    public void tearDown() {
        
        System.out.println(response.getStdout());
        System.err.println(response.getStderr());
        
        mky.assertIsSatisfied();
        
    }
    @Test
    public void testExecuteSimple() {
        
        input.put("a", "World!");
        
        m.setScript("s = input[a]\n\noutput.result = \"Hello, ${s}\"");
        
        executor.executeModel(m, request, response);
        
        Object result = response.getOutputs().get("result");
        assertNotNull(result);
        assertEquals("Hello, World!", result.toString());
    }
    @Test
    public void testSetOutput() throws MalformedURLException {

        m.setScript("output[result] = {3*2}");
        
        executor.executeModel(m, request, response);
        
        Object result = response.getOutputs().get("result");
        assertNotNull(result);
        assertEquals(result, 6);
    }
    @Test
    public void testSetOutput2() {

        m.setScript("output result {3*2}");
        
        executor.executeModel(m, request, response);
        
        Object result = response.getOutputs().get("result");
        assertNotNull(result);
        assertEquals(result, 6);
    }
    @Test
    public void testSetOutput3() {
        
        input.put("n1", 3);

        m.setScript("output.result {input.n1 * input.n1}");
        
        executor.executeModel(m, request, response);
        
        Object result = response.getOutputs().get("result");
        assertNotNull(result);
        assertEquals(result, 9);
    }
    @Test
    public void testSetOutput4() {
        
        input.put("n1", 3);

        m.setScript("output.result {n1 * n1}");
        
        executor.executeModel(m, request, response);
        
        Object result = response.getOutputs().get("result");
        assertNotNull(result);
        assertEquals(result, 9);
    }
    @Test
    public void testSetOutput5() {
        
        input.put("n1", 3);

        m.setScript("result {n1 * n1}");
        
        executor.executeModel(m, request, response);
        
        Object result = response.getOutputs().get("result");
        assertNotNull(result);
        assertEquals(result, 9);
    }
    
    @Test
    public void testExecuteWarn0() {
        
        
        m.setScript("warn stop");
        
        executor.executeModel(m, request, response);
        
        assertNotNull(response.getWarnings());
        assertTrue(!response.getWarnings().isEmpty());
    }
    @Test
    public void testExecuteWarn() {
        
        input.put("a", "World!");
        
        m.setScript("warn(\"abc\") end");
        
        executor.executeModel(m, request, response);
        
        assertNotNull(response.getWarnings());
        assertTrue(!response.getWarnings().isEmpty());
    }
    
    @Test
    public void testExecuteWarn2() {
        
        
        m.setScript("warn() stop()");
        
        executor.executeModel(m, request, response);
        
        assertNotNull(response.getWarnings());
        assertTrue(!response.getWarnings().isEmpty());
    }
    
    @Test
    public void testExecuteWarn3() {
        
        
        m.setScript("addWarning()");
        
        executor.executeModel(m, request, response);
        
        assertNotNull(response.getWarnings());
        assertTrue(!response.getWarnings().isEmpty());
    }
    
    @Test
    public void testExecuteWarn4() {
        
        
        m.setScript("addWarning(\"abc\")");
        
        executor.executeModel(m, request, response);
        
        assertNotNull(response.getWarnings());
        assertTrue(!response.getWarnings().isEmpty());
    }
    
    @Test
    public void testExecuteWarn5() {
        
        
        m.setScript("addWarning(99, \"abc\")");
        
        executor.executeModel(m, request, response);
        
        assertNotNull(response.getWarnings());
        assertTrue(!response.getWarnings().isEmpty());
    }
    
    @Test
    public void testExists_Positive_Spaces() throws IOException {
        input.put("abc", "should not be");

        m.setScript("condition input.abc exists then warn end");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    
    @Test
    public void testExists_Positive_Spacesa() throws IOException {
        m.setScript("condition input.abc does not exist then warn end");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }

    @Test
    public void testExists_Negative_Spaces() throws IOException {

        m.setScript("condition input.abc exists then warn end");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }

    @Test
    public void testExists_Negative_Spaces1() throws IOException {

        m.setScript("condition input.abc does exist then warn");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }

    @Test
    public void testDoesNotExist_Positive_Spaces() throws IOException {

        m.setScript("condition input.abc does not exist then warn end");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }

    @Test
    public void testDoesNotExist_Positive_Spacesa() throws IOException {

        m.setScript("condition input.abc does not exist then warn end");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }

    @Test
    public void testDoesNotExist_Negative_Spaces() throws IOException {
        input.put("abc", "should not be");

        m.setScript("condition input.abc does not exist then warn end");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }

    @Test
    public void testDoesNotExist_Negative_Spaces1() throws IOException {
        input.put("abc", "should not be");

        m.setScript("condition input.abc does not exist then warn end");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }
    // END SPACES
    
    
    @Test
    public void testGreaterThanInputPositive() throws IOException {
        input.put("a", "9");
        input.put("b", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition b greaterThan a then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    
    
    @Test
    public void testGreaterThanInputPositiveb() throws IOException {
        input.put("a", "9");
        input.put("b", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.b greaterThan input.a then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    @Test
    public void testGreaterThanInput_Negative() throws IOException {
        input.put("a", "10");
        input.put("b", "9");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan input.a then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }
    
    @Test
    public void testGreaterThanPositive() throws IOException {
        input.put("a", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan 9 then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    @Test
    public void testGreaterThan_Negative() throws IOException {
        input.put("a", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan 11 then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }
    @Test
    public void testGreaterThan_NegativeEqual() throws IOException {
        input.put("a", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan 10 then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }
    @Test
    public void testLessThanPositive() throws IOException {
        input.put("a", "1");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a lessThan 9 then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    @Test
    public void testLessThan_Negative() throws IOException {
        input.put("a", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a lessThan 9 then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }
    @Test
    public void testLessThan_NegativeEquals() throws IOException {
        input.put("a", "9");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a lessThan 9 then warn");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }

    @Test
    public void testGreaterThanOrEquals_PositiveGreater() throws IOException {
        input.put("a", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan or equalTo 9 then warn");
//        m.setScript("condition inputabc.doesNot exist then warn end");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }

    @Test
    public void testGreaterThanOrEquals_PositiveEqual() throws IOException {
        input.put("a", "9");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan or equalTo 9 then warn");
//        m.setScript("condition inputabc.doesNot exist then warn end");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }

    @Test
    public void testGreaterThanOrEquals_Negative() throws IOException {
        input.put("a", "8");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan or equalTo 9 then warn");
//        m.setScript("condition inputabc.doesNot exist then warn end");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }

    @Test
    public void testLessThanOrEquals_PositiveGreater() throws IOException {
        input.put("a", "10");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a less than or equalTo 20 then warn end");
//        m.setScript("condition inputabc.doesNot exist then warn end");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }

    @Test
    public void testLessThanOrEquals_PositiveEqual() throws IOException {
        input.put("a", "9");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a greaterThan or equalTo 9 then warn");
//        m.setScript("condition inputabc.doesNot exist then warn end");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }

    @Test
    public void testLessThanOrEquals_Negative() throws IOException {
        input.put("a", "20");

        StringBuilder sb = new StringBuilder();
        sb.append("condition input.a lessThan or equalTo 9 then warn end");
//        m.setScript("condition inputabc.doesNot exist then warn end");
        m.setScript(sb.toString());
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(0, warnings.size());
    }

//    @Test
//    public void testLogicalAnd_P() throws IOException {
//        input.put("abc", "seven");
//        input.put("defg", "eight");
//
//        m.setScript("condition input.abc equalTo seven and input defg equalTo eight then K");
//        executor.executeModel(m, request, response);
//        
//        System.out.println(response.getStdout());
//        System.err.println(response.getStderr());
//        
//        List<ModelExecutionWarning> warnings = response.getWarnings();
//        assertNotNull(warnings);
//        assertEquals(1, warnings.size());
//        ModelExecutionWarning warning = warnings.get(0);
//        assertEquals("<no message>", warning.getSummary());
//    }
    
    @Test
    public void testConditionBuilder_InputEqualsConstantThenWarn_Negative() {
        input.put("abc", "xyz");

        m.setScript("condition input.abc equalTo xyz then warn");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    
    @Test
    public void testConditionBuilder_InputEqualsConstantThenWarn_Positivea() {
        input.put("abc", "defx");

        m.setScript("condition input.abc equalTo defx then warn");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    
    @Test
    public void testConditionBuilder_InputEqualsConstantThenWarn_Positive_b() {
        input.put("abc", "defx");

        m.setScript("condition input.abc equalTo 'defx' then warn");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    
    @Test
    public void testConditionBuilder_InputEqualsConstantThenWarn_Positive_c() {
        input.put("abc", "defx");

        m.setScript("condition input.abc equalTo \"defx\" then warn");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    
    @Test
    public void testConditionBuilder_InputDoesNotEqualConstantThenWarn_Positive() {
        input.put("abc", "5");

        m.setScript("condition input.abc does not equal 3 then warn");
        executor.executeModel(m, request, response);
        
        List<ModelExecutionWarning> warnings = response.getWarnings();
        assertNotNull(warnings);
        assertEquals(1, warnings.size());
    }
    
}
