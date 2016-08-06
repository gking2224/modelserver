package me.gking2224.model.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="MODEL_MANIFEST")
public class ModelManifest {


    private Long id;

    private String name;

    private ModelType type;

    private String location;

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String modelName) {
        this.name = modelName;
    }

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=ModelType.class )
    @JoinColumn(name="model_type_id", referencedColumnName="model_type_id")
    public ModelType getType() {
        return type;
    }

    public void setType(ModelType type) {
        this.type = type;
    }

    public ModelManifest() {
        super();
    }

    public ModelManifest(Long id, String name, ModelType type) {
        this();
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public ModelManifest(String name, ModelType type) {
        this(null, name, type);
    }

    @Override
    public String toString() {
        return "ModelManifest [name=" + name + ", type=" + type + "]";
    }
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "MODEL_MANIFEST_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long modelManifestId) {
        this.id = modelManifestId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Transient
    public String getLocation() {
        return this.location;
    }

}
