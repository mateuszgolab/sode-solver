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
    private String name;
    private List<String> equations;
    private double min;
    private double max;
    private double step;
    
    
    public SystemEntity()
    {
        
    }
    
    public SystemEntity(String name, List<String> equations, double min, double max, double step)
    {
        this.name = name;
        this.equations = equations;
        this.min = min;
        this.max = max;
        this.step = step;
    }
    
    
    /**
     * @param min the min to set
     */
    public final void setMin(double min)
    {
        this.min = min;
    }
    
    
    /**
     * @param max the max to set
     */
    public final void setMax(double max)
    {
        this.max = max;
    }
    
    
    /**
     * @param step the step to set
     */
    public final void setStep(double step)
    {
        this.step = step;
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
     * @return the min
     */
    public final double getMin()
    {
        return min;
    }
    
    
    /**
     * @return the max
     */
    public final double getMax()
    {
        return max;
    }
    
    
    /**
     * @return the step
     */
    public final double getStep()
    {
        return step;
    }
    
    
}
