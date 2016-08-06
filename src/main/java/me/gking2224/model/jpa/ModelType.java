package me.gking2224.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="MODEL_TYPE")
public class ModelType {
    
    private Long id;
    
    private String name;
    
    private boolean enabled = false;

    private String location;

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelType() {
        
    }

    public ModelType(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public ModelType(String name) {
        this(null, name);
    }

    @Override
    public String toString() {
        return "ModelType [name=" + name + "]";
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "MODEL_TYPE_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long modelTypeId) {
        this.id = modelTypeId;
    }

    @Column
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Transient
    public String getLocation() {
        return this.location;
    }

}
