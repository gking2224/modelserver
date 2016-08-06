package me.gking2224.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="model_variable")
public class Variable implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5305219960965196873L;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "MODEL_VARIABLE_ID")
    private Long modelVariableId;

    @Column
    private String name;
    
    @Column
    private String type;
    
    public Variable(Long modelVariableId, String name, String type) {
        this(name, type);
        this.modelVariableId = modelVariableId;
    }
    
    public Variable(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    public Variable() {
        super();
    }

    public Long getModelVariableId() {
        return modelVariableId;
    }

    public void setModelVariableId(Long modelVariableId) {
        this.modelVariableId = modelVariableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
