package uk.ac.cranfield.thesis.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import uk.ac.cranfield.thesis.client.service.EquationsService;
import uk.ac.cranfield.thesis.shared.Solution;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;


public class EquationServiceImpl extends RemoteServiceServlet implements EquationsService
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8876483999017753125L;
    
    
    private String equation;
    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    
    @Override
    public void setEquation(String equation)
    {
        this.equation = equation;
    }
    
    @Override
    public Solution getValues(String var, List<Double> points, int coordinate) throws UnknownFunctionException,
            UnparsableExpressionException
    {
        if (equation == null)
            return null;
        
        List<Double> results = new ArrayList<Double>();
        Map<String, Object> map = new HashMap<String, Object>();
        
        for (Double p : points)
        {
            map.clear();
            map.put(var, p);
            
            // results.add((Double) engine.eval(equation, new SimpleBindings(map)));
            Calculable calc = new ExpressionBuilder(equation).withVariable(var, p).build();
            results.add(calc.calculate());
        }
        
        return new Solution(results, coordinate);
    }
    
    
}
