package me.gking2224.model.jpa;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class ModelVariableId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 183121946793942067L;

    @ManyToOne()
    private Model model;

    @ManyToOne
    private Variable variable;

    @JsonIgnore
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public ModelVariableId() {
        super();
        this.variable = new Variable();
        this.model = new Model();
    }

    // public ModelVariableId(Model model, Variable variable, char direction,
    // boolean mandatory) {
    // this(model, variable, Direction.fromChar(direction), mandatory);
    // }
    // public ModelVariableId(Model model, Variable variable, Direction
    // direction, boolean mandatory) {
    // super();
    // this.model = model;
    // this.variable = variable;
    // }
    public ModelVariableId(Model model, Variable variable) {
        super();
        this.model = model;
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "ModelVariableId [model=" + model + ", variable=" + variable + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((variable == null) ? 0 : variable.hashCode());
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
        ModelVariableId other = (ModelVariableId) obj;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (variable == null) {
            if (other.variable != null)
                return false;
        } else if (!variable.equals(other.variable))
            return false;
        return true;
    }

}
