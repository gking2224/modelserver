package me.gking2224.model.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(classes=me.gking2224.model.mvc.WebAppTestConfigurer.class)
@TestPropertySource("/test.properties")
@Transactional()
@EnableJpaRepositories
@Rollback
public class ModelManifestRepositoryIT {

    @Autowired
    protected ModelManifestRepository manifestRepository;
    
    @Test
    public void testSave() {
        String modelName = "model1";
        
        ModelType modelType = new ModelType("type1");
        ModelManifest mm = new ModelManifest(modelName, modelType);
        ModelManifest saved = manifestRepository.save(mm);
        assertNotNull(saved);
    }
    
    @Test
    @Sql
    public void testFindOne() {
        
        ModelManifest mm = manifestRepository.findOne(100L);
        
        assertNotNull(mm);
        assertEquals("Test Model", mm.getName());
        assertEquals("Default Type", mm.getType().getName());
        
//        List<Model> versions = mm.getVersions();
//        assertNotNull(versions);
//        assertEquals(3, versions.size());
        
//        Model model = versions.get(0);
//        assertTrue(model.getVersion().equalsString("1.0"));
//        Set<String> natures = model.getNatures();
//        assertNotNull(natures);
//        assertEquals(1, natures.size());
//        assertEquals("me.gking2224.modelserver.DefaultNature", natures.iterator().next());
    }
    
    @Test
    @Sql
    public void testFindByTypeName() {
        
        List<ModelManifest> mm = manifestRepository.findByTypeName("Type 1");
        
        assertNotNull(mm);
        assertEquals(2, mm.size());
        assertEquals("Test Model 1.1", mm.get(0).getName());
        assertEquals("Test Model 1.2", mm.get(1).getName());
    }
}
