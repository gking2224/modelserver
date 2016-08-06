package me.gking2224.model.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.stereotype.Component;

import me.gking2224.model.dao.AbstractDaoImpl;

@Component
public class ModelDaoImpl
extends AbstractDaoImpl<Version> implements ModelDao {

    private static final String GET_NEXT_VERSION = "{call get_next_version(?, ?, ?)}";

    protected static final String VERSION = "o_version";

    @Autowired
    ModelRowMapper rowMapper;
    
    public ModelDaoImpl() {
    }



    @Override
    public Version getNextVersion(String modelType, String modelName, boolean majorVersionIncrement) {
        return super.fetchComplex(GET_NEXT_VERSION, new ComplexFetchCallback<Version>() {

            @Override
            public void addAdditionalOutputParameters(List<SqlParameter> parameters) {
                parameters.add(new SqlReturnResultSet(VERSION, rowMapper));
            }

            @Override
            public Version getComplexObject(Map<String, Object> call) {
                return getResultSetAsObject(call, VERSION);
            }
        }, modelType, modelName, majorVersionIncrement);
    }

}
