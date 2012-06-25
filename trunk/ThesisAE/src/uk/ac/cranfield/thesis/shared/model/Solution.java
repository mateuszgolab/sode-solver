package uk.ac.cranfield.thesis.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



import com.googlecode.objectify.annotation.Entity;


@SuppressWarnings("serial")
@Entity
public class Solution implements Serializable
{
    
    private List<Double> results;
    private EquationParameterEntity parameter;
    
    public Solution(List<Double> results, double min, double max, double step)
    {
        this.results = results;
        parameter = new EquationParameterEntity(min, max, step);
    }
    
    public Solution(double min, double max, double step)
    {
        this.results = new ArrayList<Double>((int) ((max - min) / step));
        parameter = new EquationParameterEntity(min, max, step);
    }
    
    public Solution()
    {
    }
    
    public void addResult(double result)
    {
        results.add(result);
    }
    
    /**
     * @return the results
     */
    public final List<Double> getResults()
    {
        return results;
    }
    
    /**
     * @param results the results to set
     */
    public final void setResults(List<Double> results)
    {
        this.results = results;
    }
    
    
    public double getResult(int i)
    {
        return results.get(i);
    }
    
    
    public int size()
    {
        return results.size();
    }
    
    
    /**
     * @return the start
     */
    public final double getMin()
    {
        return parameter.getMin();
    }
    
    
    /**
     * @param start the start to set
     */
    public final void setMin(double min)
    {
        parameter.setMin(min);
    }
    
    
    /**
     * @return the stop
     */
    public final double getMax()
    {
        return parameter.getMax();
    }
    
    
    /**
     * @param stop the stop to set
     */
    public final void setMax(double max)
    {
        parameter.setMax(max);
    }
    
    
    /**
     * @return the h
     */
    public final double getStep()
    {
        return parameter.getStep();
    }
    
    
    /**
     * @param h the h to set
     */
    public final void setStep(double step)
    {
        parameter.setStep(step);
    }
    
    
}
