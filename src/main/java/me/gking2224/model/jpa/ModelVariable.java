package me.gking2224.model.jpa;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="model_assigned_variable")
@AssociationOverrides({
    @AssociationOverride(name = "pk.model", 
        joinColumns = @JoinColumn(name = "model_id")),
    @AssociationOverride(name = "pk.variable", 
        joinColumns = @JoinColumn(name = "model_variable_id")) })
public class ModelVariable {
    @EmbeddedId
    private ModelVariableId pk;
    
    @Column
    private boolean mandatory;
    
    @Column
    private char direction;

    public ModelVariable(ModelVariableId id, boolean mandatory) {
        super();
        this.pk = id;
        this.mandatory = mandatory;
    }
    
    public ModelVariable() {
        this(new ModelVariableId(), false);
    }
    
    public ModelVariable(String name, String type, Direction d) {
        this();
        setName(name);
        setType(type);
        setDirection(d);
    }
    @JsonIgnore
    public ModelVariableId getPk() {
        return pk;
    }
    public void setPk(ModelVariableId pk) {
        this.pk = pk;
    }
    public boolean isMandatory() {
        return mandatory;
    }
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    @Transient
    @JsonInclude
    public String getName() {
        return getPk().getVariable().getName();
    }
    @Transient
    @JsonInclude
    public String getType() {
        return getPk().getVariable().getType();
    }
    
    @Transient
    @JsonInclude
    public Direction getDirection() {
        return Direction.fromChar(this.direction);
    }
    @JsonAnySetter
    public void setOther(String param, Object value) {
        @SuppressWarnings("unused")
        Object o = null;
        
    }
    
    public void setType(String type) {
        this.pk.getVariable().setType(type);
    }
    
    public void setName(String name) {
        this.pk.getVariable().setName(name);
    }
    
    public void setDirection(Direction d) {
        this.direction = d.toChar();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ModelVariable other = (ModelVariable) obj;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (!pk.equals(other.pk))
            return false;
        return true;
    }
    
    public static enum Direction {
        IN('i'),
        OUT('o'),
        INOUT('b');

        private char c;
        
        Direction(char c) {
            this.c = c;
        }
        
        public char toChar() {
            return c;
        }
        
        static Direction fromChar(char c) {
            switch (c) {
            case 'i':
            case 'I': return IN;
            case 'o':
            case 'O': return OUT;
            case 'b':
            case 'B': return INOUT;
            default: throw new IllegalArgumentException(""+c);
            }
        }
    }
}