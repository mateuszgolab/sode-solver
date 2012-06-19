package uk.ac.cranfield.thesis.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EquationsSystem implements Serializable
{
    
    private List<Equation> equations;
    
    
    public EquationsSystem()
    {
        this.equations = new ArrayList<Equation>();
    }
    
    public EquationsSystem(List<Equation> equations)
    {
        this.equations = equations;
    }
    
    
    public final List<Equation> getEquations()
    {
        return equations;
    }
    
    /**
     * @param equations the equations to set
     */
    public final void setEquations(List<Equation> equations)
    {
        this.equations = equations;
    }
    
    public List<List<Double>> getInitValues()
    {
        List<List<Double>> result = new ArrayList<List<Double>>();
        for (Equation eq : equations)
        {
            result.add(eq.getInitValues());
        }
        
        return result;
    }
    
    public List<Character> getFunctionVariables()
    {
        List<Character> result = new ArrayList<Character>(equations.size());
        
        for (Equation eq : equations)
        {
            result.add(eq.getFunctionVariable());
        }
        
        return result;
    }
    
    public char getIndependentVariable()
    {
        return equations.get(0).getIndependentVariable();
    }
    
}
