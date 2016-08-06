package me.gking2224.utils.test;


public class TestUtils {


    public static JsonPathFilterExpressionMatcher jsonFilterPath(String path) {
        return new JsonPathFilterExpressionMatcher(path);
    }

}
