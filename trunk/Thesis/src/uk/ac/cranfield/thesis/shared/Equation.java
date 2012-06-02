package uk.ac.cranfield.thesis.shared;

import java.io.Serializable;


public class Equation implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2898285997338072912L;
    
    
    private String equationContent;
    private String independentVariable;
    private String dependentVariable;
    private EquationStatus status;
    
    public Equation(String equation)
    {
        equationContent = equation;
        setStatus();
    }
    
    public void setEquation(String equation)
    {
        equationContent = equation;
    }
    
    public String getEquation()
    {
        return equationContent;
    }
    
    public String getIndependentVariable()
    {
        return independentVariable;
    }
    
    /**
     * @param independentVariable the independentVariable to set
     */
    public final void setIndependentVariable(String independentVariable)
    {
        this.independentVariable = independentVariable;
    }
    
    private void setStatus()
    {
        status = (equationContent.length() > 0) ? EquationStatus.CORRECT : EquationStatus.EMPTY;
    }
    
    public EquationStatus getStatus()
    {
        return status;
    }
    
    public boolean isEmpty()
    {
        return status == EquationStatus.EMPTY;
    }
    
    
    /**
     * @return the dependentVariable
     */
    public final String getDependentVariable()
    {
        return dependentVariable;
    }
    
    
    /**
     * @param dependentVariable the dependentVariable to set
     */
    public final void setDependentVariable(String dependentVariable)
    {
        this.dependentVariable = dependentVariable;
    }
}
