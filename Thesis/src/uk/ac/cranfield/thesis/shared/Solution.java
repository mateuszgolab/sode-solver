package uk.ac.cranfield.thesis.shared;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class Solution implements Serializable
{
    
    
    private List<Double> results;
    private int xAxis;
    private int yAxis;
    
    public Solution(List<Double> results, int coordinate)
    {
        this.results = results;
        this.xAxis = coordinate;
        this.yAxis = coordinate + 1;
    }
    
    public Solution()
    {
        results = null;
        xAxis = 0;
        yAxis = 1;
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
    
    
    /**
     * @return the xAxis
     */
    public final int getxAxis()
    {
        return xAxis;
    }
    
    
    /**
     * @param xAxis the xAxis to set
     */
    public final void setxAxis(int xAxis)
    {
        this.xAxis = xAxis;
    }
    
    
    /**
     * @return the yAxis
     */
    public final int getyAxis()
    {
        return yAxis;
    }
    
    
    /**
     * @param yAxis the yAxis to set
     */
    public final void setyAxis(int yAxis)
    {
        this.yAxis = yAxis;
    }
    
    public int size()
    {
        return results.size();
    }
}
