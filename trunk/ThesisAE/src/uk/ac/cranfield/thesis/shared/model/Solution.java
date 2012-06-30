package uk.ac.cranfield.thesis.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Solution implements Serializable
{
    
    private List<Double> results;
    private double min;
    private double max;
    private double step;
    
    public Solution(List<Double> results, double min, double max, double step)
    {
        this.results = results;
        this.min = min;
        this.max = max;
        this.step = step;
    }
    
    public Solution(double min, double max, double step)
    {
        this.results = new ArrayList<Double>((int) ((max - min) / step));
        this.min = min;
        this.max = max;
        this.step = step;
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
