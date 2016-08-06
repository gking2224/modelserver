package me.gking2224.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.Version;
import me.gking2224.model.utils.JsonUtil;
 
@Component
public class ModelObjectConverters {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JsonUtil jsonUtil;
    
    public List<Converter<?,?>> getConverters() {
        return Arrays.asList(
                getJsonToModel()
                );
    }
    
    @Bean
    public Converter<String, Model> getJsonToModel() {
        return new Converter<String,Model>() {
            @Override
            public Model convert(String source) {
                try {
                    return jsonUtil.parseJson(source, Model.class);
                } catch (IOException e) {
                    logger.error("Error converting json to Model object", e);
                    return null;
                }
            }
        };
    }
//    @javax.persistence.Converter
//    @Component
    public static class BigDecimalToVersionConverter implements Converter<BigDecimal, Version> {

        @Override
        public Version convert(BigDecimal source) {
            return Version.fromString(source.stripTrailingZeros().toString());
        }
        
    }
    
}
