package uk.ac.cranfield.thesis.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Parameter implements Serializable
{
    
    private double min;
    private double max;
    private double step;
    
    /**
     * @return the min
     */
    public final double getMin()
    {
        return min;
    }
    
    /**
     * @param min the min to set
     */
    public final void setMin(double min)
    {
        this.min = min;
    }
    
    /**
     * @return the max
     */
    public final double getMax()
    {
        return max;
    }
    
    /**
     * @param max the max to set
     */
    public final void setMax(double max)
    {
        this.max = max;
    }
    
    /**
     * @return the step
     */
    public final double getStep()
    {
        return step;
    }
    
    /**
     * @param step the step to set
     */
    public final void setStep(double step)
    {
        this.step = step;
    }
}
