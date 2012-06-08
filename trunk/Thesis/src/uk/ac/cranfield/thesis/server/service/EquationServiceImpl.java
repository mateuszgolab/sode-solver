package uk.ac.cranfield.thesis.server.service;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.EquationsService;
import uk.ac.cranfield.thesis.shared.Equation;
import uk.ac.cranfield.thesis.shared.Solution;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;


@SuppressWarnings("serial")
public class EquationServiceImpl extends RemoteServiceServlet implements EquationsService
{
    
    // private ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    
    
    @Override
    public Solution evaluate(Equation equation, List<Double> points) throws UnknownFunctionException,
            UnparsableExpressionException
    {
        if (equation == null)
            return null;
        
        List<Double> results = new ArrayList<Double>();
        
        for (Double p : points)
        {
            Calculable calc = new ExpressionBuilder(equation.getEquation()).withVariable(
                    String.valueOf(equation.getIndependentVariable()), p).build();
            results.add(calc.calculate());
        }
        
        return new Solution(results, 0, 10, 1);
    }
}
