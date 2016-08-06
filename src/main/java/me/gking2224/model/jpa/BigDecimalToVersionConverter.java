package me.gking2224.model.jpa;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import javax.persistence.AttributeConverter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

@javax.persistence.Converter(autoApply=true)
@Component
public class BigDecimalToVersionConverter implements GenericConverter, AttributeConverter<BigDecimal, Version> {

    public BigDecimalToVersionConverter() {
        super();
    }
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(BigDecimal.class, Version.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return Version.fromString(((BigDecimal)source).stripTrailingZeros().toString());
    }
    @Override
    public Version convertToDatabaseColumn(BigDecimal attribute) {
        return Version.fromString(((BigDecimal)attribute).stripTrailingZeros().toString());
    }
    @Override
    public BigDecimal convertToEntityAttribute(Version dbData) {
        return new BigDecimal(dbData.asVersionString());
    }

    
}