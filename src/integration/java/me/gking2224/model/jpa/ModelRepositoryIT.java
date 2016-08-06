package me.gking2224.model.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.model.jpa.ModelVariable.Direction;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(classes=me.gking2224.model.mvc.WebAppTestConfigurer.class)
@TestPropertySource("/test.properties")
@Transactional()
@EnableJpaRepositories
@Rollback
public class ModelRepositoryIT {

    @Autowired
    protected ModelRepository modelRepository;
    
    @Autowired ModelDao modelDao;
    
    @Test
    @Sql
    public void testFindByManifestTypeNameAndVersion() {
        
        String manifestTypeName = "Type 1";
        String name = "Test Model 1.1";
        Integer majorVersion = 1;
        Integer minorVersion = 3;
        
        Model m = modelRepository.findByManifestTypeNameAndManifestNameAndVersionMajorVersionAndVersionMinorVersion(manifestTypeName, name, majorVersion, minorVersion);
        
        assertNotNull(m);
        assertNotNull(m.getManifest());
        assertEquals(manifestTypeName, m.getManifest().getType().getName());
        assertEquals(name, m.getManifest().getName());
        assertTrue(m.getVersion().equalsString("1.3"));
    }
    
    @Test
    @Sql
    public void testFindOne() {

        
        Model m = modelRepository.findOne(300L);
        
        assertNotNull(m);
        
        Set<ModelVariable> variables = m.getVariables();
        assertNotNull(variables);
        assertEquals(1, variables.size());
        
        ModelVariable var = variables.iterator().next();
        assertNotNull(var);
        
        String name = var.getName();
        assertNotNull(name);
        assertEquals("countryCode", name);
        
        String type = var.getType();
        assertNotNull(type);
        assertEquals("java.lang.String", type);
        
        assertEquals(Direction.INOUT, var.getDirection());
        
    }
    
    @Test
    @Sql
    public void testGetNextVersion() {

        
        Version v = modelDao.getNextVersion("Type 1", "Test Model 1.1", true);
        assertNotNull(v);
//        assertEquals("2", v.stripTrailingZeros().toString());
        assertEquals((Integer)2, v.getMajorVersion());
        assertEquals((Integer)0, v.getMinorVersion());
        
    }
    
    @Test
    @Sql("ModelRepositoryIT.testGetNextVersion.sql")
    public void testGetNextVersion_Minor() {
        Version v = modelDao.getNextVersion("Type 1", "Test Model 1.1", false);
        assertNotNull(v);
//        assertEquals("1.04", v.stripTrailingZeros().toPlainString());
        assertEquals((Integer)1, v.getMajorVersion());
        assertEquals((Integer)4, v.getMinorVersion());
        
    }
}
