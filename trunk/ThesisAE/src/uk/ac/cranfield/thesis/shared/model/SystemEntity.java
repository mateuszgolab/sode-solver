package uk.ac.cranfield.thesis.shared.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class SystemEntity implements Serializable
{
    
    @Id
    private Long id;
    private String name;
    private List<String> equations;
    
    public SystemEntity()
    {
        
    }
    
    public SystemEntity(String name, List<String> equations)
    {
        this.name = name;
        this.equations = equations;
    }
    
    
    /**
     * @return the name
     */
    public final String getName()
    {
        return name;
    }
    
    
    /**
     * @param name the name to set
     */
    public final void setName(String name)
    {
        this.name = name;
    }
    
    
    /**
     * @return the equations
     */
    public final List<String> getEquations()
    {
        return equations;
    }
    
    
    /**
     * @param equations the equations to set
     */
    public final void setEquations(List<String> equations)
    {
        this.equations = equations;
    }
    
    
    /**
     * @param id the id to set
     */
    public final void setId(Long id)
    {
        this.id = id;
    }
}
