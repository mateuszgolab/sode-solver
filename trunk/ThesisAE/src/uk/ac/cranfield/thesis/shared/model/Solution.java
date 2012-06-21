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
    private double minValue;
    private double maxValue;
    
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
     * @return the start
     */
    public final double getMin()
    {
        return min;
    }
    
    
    /**
     * @param start the start to set
     */
    public final void setMin(double min)
    {
        this.min = min;
    }
    
    
    /**
     * @return the stop
     */
    public final double getMax()
    {
        return max;
    }
    
    
    /**
     * @param stop the stop to set
     */
    public final void setMax(double max)
    {
        this.max = max;
    }
    
    
    /**
     * @return the h
     */
    public final double getStep()
    {
        return step;
    }
    
    
    /**
     * @param h the h to set
     */
    public final void setStep(double step)
    {
        this.step = step;
    }
    
    
    /**
     * @return the minValue
     */
    public final double getMinValue()
    {
        return minValue;
    }
    
    
    /**
     * @param minValue the minValue to set
     */
    public final void setMinValue(double minValue)
    {
        this.minValue = minValue;
    }
    
    
    /**
     * @return the maxValue
     */
    public final double getMaxValue()
    {
        return maxValue;
    }
    
    
    /**
     * @param maxValue the maxValue to set
     */
    public final void setMaxValue(double maxValue)
    {
        this.maxValue = maxValue;
    }
    
}
