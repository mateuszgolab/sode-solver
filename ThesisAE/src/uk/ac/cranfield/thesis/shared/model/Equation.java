package uk.ac.cranfield.thesis.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.api.users.User;


@Entity
public class Equation implements Serializable
{
    
    @Id
    private Long id;
    private User owner;
    private List<Double> initValues;
    private String equationContent;
    private char independentVariable;
    private char functionVariable;
    private int order;
    
    public Equation(String equation)
    {
        equationContent = equation;
        initValues = new ArrayList<Double>();
        // setStatus();
    }
    
    public Equation(String equation, List<Double> init)
    {
        equationContent = equation;
        initValues = init;
        // setStatus();
    }
    
    public Equation()
    {
        this("");
    }
    
    
    public int getOrder()
    {
        return order;
    }
    
    public void setEquation(String equation)
    {
        equationContent = equation;
    }
    
    public String getEquation()
    {
        return equationContent;
    }
    
    public char getIndependentVariable()
    {
        return independentVariable;
    }
    
    // private void setStatus()
    // {
    // status = (equationContent.length() > 0) ? EquationStatus.CORRECT : EquationStatus.EMPTY;
    // }
    //
    // public EquationStatus getStatus()
    // {
    // return status;
    // }
    //
    // public boolean isEmpty()
    // {
    // return status == EquationStatus.EMPTY;
    // }
    
    /**
     * @param independentVariable the independentVariable to set
     */
    public final void setIndependentVariable(char independentVariable)
    {
        this.independentVariable = independentVariable;
    }
    
    
    /**
     * @return the initValues
     */
    public final List<Double> getInitValues()
    {
        return initValues;
    }
    
    
    /**
     * @param initValues the initValues to set
     */
    public final void setInitValues(List<Double> initValues)
    {
        this.initValues = initValues;
    }
    
    
    /**
     * @return the functionVariable
     */
    public final char getFunctionVariable()
    {
        return functionVariable;
    }
    
    
    /**
     * @param functionVariable the functionVariable to set
     */
    public final void setFunctionVariable(char functionVariable)
    {
        this.functionVariable = functionVariable;
    }
    
    
    /**
     * @param order the order to set
     */
    public final void setOrder(int order)
    {
        this.order = order;
    }
    
    
    /**
     * @return the id
     */
    public final Long getId()
    {
        return id;
    }
    
    
    /**
     * @param id the id to set
     */
    public final void setId(Long id)
    {
        this.id = id;
    }
    
    
    /**
     * @return the owner
     */
    public final User getOwner()
    {
        return owner;
    }
    
    
    /**
     * @param owner the owner to set
     */
    public final void setOwner(User owner)
    {
        this.owner = owner;
    }
    
}