package me.gking2224.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelTypeRepository extends JpaRepository<ModelType, Long> {

    ModelType findByName(String type);
    
}
