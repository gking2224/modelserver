package me.gking2224.model.execution.groovy;

import java.lang.reflect.Constructor
import java.lang.reflect.Method

import org.codehaus.groovy.runtime.NullObject


@SuppressWarnings("rawtypes")
public class ReflectionHelper {

    def logger
    static ReflectionHelper instance
    
    def ReflectionHelper(def logger) {
        this.logger = logger;
    }
    
    static getInstance(def logger) {
        if (instance == null) instance = new ReflectionHelper(logger)
        return instance
    }
    
    def _getNormalizedArgsAndClasses(def a) {
        
        def args = _getNormalizedArgs(a)
        def Class<?>[] clzz = _getNormalizedArgClasses(args)
        if (clzz == null) {
            args = null
        }
        return forceToArray([args,clzz])
    }
    
    // handle arrays / collections
    def getArrayLength(def arr) {
        def rv
        if (arr == null) {
            rv = 0
        }
        else if (Collection.isAssignableFrom(arr.getClass())) {
            rv = arr.size()
        }
        else {
            rv = arr.length
        }
        rv
    }
    
    // look in a single class for method then property, determine class types
    def getMethodOrPropertyOnClass(def obj, String name) {
        getMethodOrPropertyOnClass(obj, name, null)
    }
    
    // look in a single class for method then property, determine class types
    def getMethodOrPropertyOnClass(def obj, String name, def a) {
        def nag = _getNormalizedArgsAndClasses(a)
        def args = nag[0]
        def clzz = nag[1]
//        int length = getArrayLength(args)
        def d = obj
        def rv = null
        while (rv == null && d != null) {
            rv = _getMethodOrPropertyOnClass(d, name, clzz, args)
            if (rv == null) rv = _handleDynamicMethod(d, name, clzz, args)
            d = getPropertyOnClass(d, "delegate")
        }
        return rv
    }
    
    // look in a single class for method then property
    def _getMethodOrPropertyOnClass(def obj, String name, Class<?>[] clzz, def args) {
        
        def rv = _getMethodValueOnClass(obj, name, clzz, args)
        if (rv != null) return rv
        
        else if (args == null || getArrayLength(args) == 0) {
            return getPropertyOnClass(obj, name)
        }
        getLogger().debug("ReflectionHelper not found method or property matching $name( $args ) on $obj")
    }
    
    def _handleDynamicMethod(def obj, String name, Class<?>[] clzz, def args) {
        Class<?>[] clzz2 = forceToArray([String.class, Object.class])
        
        _getMethodValueOnClass(obj, "doGetMethodValue", clzz2, forceToArray([name, args]))
    }
    
    // get method value if any on a single object
    def getMethodValueOnClass(def obj, String name, def a) {
        def nag = _getNormalizedArgsAndClasses(a);
        def args = nag[0]
        def clzz = nag[1]
        return _getMethodValueOnClass(obj, name, clzz, args);
    }
    
    // get method value if any on a single object
    def _getMethodValueOnClass(def obj, String name, Class<?>[] clzz, def args) {
        try {
            def clz = obj.getClass()
//            def method = clz.getMethod(name, clzz)
            Method[] mm = _getMatchingMethods(obj, name, clzz)
            if (mm == null) return null
            int ln = getArrayLength(mm)
            if (ln == 0) return null
            Method method = null
            if (ln == 1) method = mm[0]
            getLogger().debug("ReflectionHelper: found $name($clzz) on obj ($clz)")
            if (method != null) return method.invoke(obj, args);
        }
        catch (NoSuchMethodException e) {
            return null;
        } 
    }
    
    def Method[] _getMatchingMethods(def obj, String name, Class<?>[] clzz) {
        def methods = obj.getClass().getMethods()
        def rv = new ArrayList<Method>()
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].name == name && argsMatch(methods[i], clzz))
                rv.add(methods[i])
        }
        return rv
    }
    
    def boolean argsMatch(Method m, Class<?>[] c) {
        def rv = true
        def Class<?>[] clzz = (c == null)?[]:c
        def pt = m.getParameterTypes()
        if (getArrayLength(clzz) != getArrayLength(pt)) return false
        for (int i = 0; rv && i < getArrayLength(clzz); i++) {
            rv = rv && pt[i].isAssignableFrom(clzz[i])
        }
        rv
    }
    
    def matchTypes(def obj1, def obj2) {
        // either null - do nothing
        if (obj1 == null || obj2 == null) return [obj1, obj2]
        if (obj1 == NullObject.nullObject || obj2 == NullObject.nullObject) return [obj1, obj2]
        // equal - do nothing
        if (obj1 == obj2) return [obj1, obj2]
        
        // same type already - do nothing
        if (obj1.getClass().equals(obj2.getClass())) return [obj1, obj2]
        
        if (!String.isAssignableFrom(obj1.getClass())) {
            return [obj1, tryAndCoeerce(obj2, obj1.getClass())]
        }
        else if (!String.isAssignableFrom(obj2.getClass())) {
            return [tryAndCoeerce(obj1, obj2.getClass()), obj2]
        }
        
        else return [obj1, obj2]
    }
    
    def tryAndCoeerce(def obj, Class clz) {
        if (String.isAssignableFrom(clz)) return obj.toString()
        else if (Number.isAssignableFrom(clz)) {
            try {
                Constructor cstr = clz.getConstructor(String.class)
                return cstr.newInstance(obj.toString())
            } catch (Throwable t) {
                return obj
            }
        }
        else return obj
    }
    
    def forceToArray(def arrayOrCollection) {
        if (arrayOrCollection == null) return null
        if (Collection.isAssignableFrom(arrayOrCollection.getClass())) {
            return arrayOrCollection.toArray()
        }
        else {
            return arrayOrCollection
        }
    }
    
    // get property valeu if any on a single object
    def getPropertyOnClass(def obj, String name) {
        List<Class> params = new ArrayList<Class>(2);
        params.add(String.class); params.add(Boolean.class); // direct method?
        def m = null;
        def rv = obj.getProperty(new String("\$:$name")); // send a signal not to recurse - review // CHECK
        return rv
    }
    
    // get classes, turning single-value null elements and empty arrays to null
    def Class<?>[] _getNormalizedArgClasses(def args) {
        
        int length = getArrayLength(args)
        List<Class> clzz1= new ArrayList<Class>(length)
        
        for (int i = 0; i < length; i++) {
            def c = args[i].getClass()
            clzz1.add(c)
        }
        
        Class[] clzz = clzz1.toArray()
        
        if (clzz != null && (clzz.length == 1 && clzz[0] == NullObject.class) || clzz.length == 0) {
            clzz = null
            args = null
        }
        
        return forceToArray(clzz)
    }
    
    def _getNormalizedArgs(def args) {
        if (args == null) return [] // or should it be null?
        int length = (Collection.isAssignableFrom(args.getClass()))?args.size():args.length
        if (length == 1 && args[0] == null) args = [] // let's not have a single-null array
        forceToArray(args)
    }
}
