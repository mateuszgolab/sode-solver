package uk.ac.cranfield.thesis.shared;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class Solution implements Serializable
{
    
    private List<Double> results;
    private double start;
    private double stop;
    private double step;
    
    public Solution(List<Double> results, double start, double stop, double step)
    {
        this.results = results;
        this.start = start;
        this.stop = stop;
        this.step = step;
    }
    
    public Solution()
    {
        this.start = 0.0;
        this.stop = 0.0;
        this.step = 0.0;
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
    
    
    public Double getResult(int i)
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
    public final double getStart()
    {
        return start;
    }
    
    
    /**
     * @param start the start to set
     */
    public final void setStart(double start)
    {
        this.start = start;
    }
    
    
    /**
     * @return the stop
     */
    public final double getStop()
    {
        return stop;
    }
    
    
    /**
     * @param stop the stop to set
     */
    public final void setStop(double stop)
    {
        this.stop = stop;
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
    
}
