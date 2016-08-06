package me.gking2224.model.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelManifestRepository extends JpaRepository<ModelManifest, Long> {
    
    List<ModelManifest> findByTypeName(String typeName);
    
    ModelManifest findByName(String name);
}
