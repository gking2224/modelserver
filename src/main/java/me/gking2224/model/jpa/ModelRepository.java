package me.gking2224.model.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    
    Model findByManifestTypeNameAndManifestNameAndVersionMajorVersionAndVersionMinorVersion(String manifestType, String manifestName, Integer majorVersion, Integer minorVersion);
    
    List<Model> findByManifestTypeNameAndManifestName(String typeName, String name);
    
    List<Model> findByManifestId(Long manifestId);
}
