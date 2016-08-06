package me.gking2224.model.jpa;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="MODEL")
//@NamedNativeQuery(name="Model.getNextVersion", query="{call get_next_version(?1, ?2, ?3)}", resultClass=Number.class,
//resultSetMapping="Model.getNextVersion")
//@SqlResultSetMapping(name = "Model.getNextVersion", columns={@ColumnResult(name = "o_version", type=Version.class)})
public class Model implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2707606975268392692L;

    private Long id;

    private ModelManifest manifest;
    
    private Version version;
    
    private Set<String> natures = new HashSet<String>();
    
    private Set<ModelVariable> variables = new HashSet<ModelVariable>(0);

    private String script;

    private String location;
    
    private boolean enabled = false;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="model_manifest_id", referencedColumnName="model_manifest_id")
    public ModelManifest getManifest() {
        return manifest;
    }
    
    public Model() {
        
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "model_id")
    public Long getId() {
        return id;
    }

    public void setId(Long modelId) {
        this.id = modelId;
    }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="majorVersion", column=@Column(name="major_version")),
        @AttributeOverride(name="minorVersion", column=@Column(name="minor_version"))
    })
    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version= version;
    }

    public void setManifest(ModelManifest manifest) {
        this.manifest = manifest;
    }

    @Override
    public String toString() {
        return "ModelVersion [id=" + id + ", manifest=" + manifest + ", version="
                + version + "]";
    }

    @Column(name="nature") // needed?
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name = "model_nature",
        joinColumns=@JoinColumn(name = "model_id", referencedColumnName = "model_id")
    )
    public Set<String> getNatures() {
        return natures;
    }

    public void addNature(String nature) {
        this.natures.add(nature);
    }

    public void addNatures(String... natures) {
        Collections.addAll(this.natures, natures);
    }

    public void addNatures(Collection<String> natures) {
        this.natures.addAll(natures);
    }
    
    public boolean hasNature(String nature) {
        return this.natures.contains(nature);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Model other = (Model) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy="pk.model", targetEntity=ModelVariable.class, fetch=FetchType.LAZY)
    public Set<ModelVariable> getVariables() {
        return variables;
    }
    
    public void addVariable(ModelVariable v) {
        variables.add(v);
    }

    @Column(columnDefinition="MEDIUMTEXT")
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setNatures(Set<String> natures) {
        this.natures = natures;
    }

    public void setVariables(Set<ModelVariable> variables) {
        this.variables = variables;
    }

    @Transient
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @Column
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
