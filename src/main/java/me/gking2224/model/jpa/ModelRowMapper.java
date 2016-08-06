package me.gking2224.model.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelRowMapper implements RowMapper<Version> {
    private static final String O_MAJOR = "o_major_version";
    private static final String O_MINOR = "o_minor_version";
    
    Logger logger = LoggerFactory.getLogger(ModelRowMapper.class);

    @Override
    public Version mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Integer major = rs.getInt(O_MAJOR);
        Integer minor = rs.getInt(O_MINOR);
        return new Version(major, minor);
    }
}
