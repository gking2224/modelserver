package me.gking2224.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VariableRepository extends JpaRepository<Variable, Long> {

    Variable findByNameAndType(String name, String type);
    
}
