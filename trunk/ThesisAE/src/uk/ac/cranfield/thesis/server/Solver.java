package uk.ac.cranfield.thesis.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.System;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;


@SuppressWarnings("serial")
public class Solver extends RemoteServiceServlet
{
    
    protected List<String> getFunctionVector(Equation equation) throws IncorrectODEEquationException
    {
        List<String> f = new ArrayList<String>(equation.getOrder());
        
        for (int i = 1; i < equation.getOrder(); i++)
        {
            f.add(equation.getFunctionVariable() + Integer.valueOf(i).toString());
        }
        
        f.add(parseFunctionEquation(equation));
        // f.add(parseFunctionEquation2(equation));
        
        
        return f;
    }
    
    protected List<List<String>> getFunctionVector(System system) throws IncorrectODEEquationException
    {
        List<List<String>> f = new ArrayList<List<String>>();
        List<Equation> equations = system.getEquations();
        
        for (Equation eq : equations)
        {
            f.add(getFunctionVector(eq));
        }
        
        return f;
    }
    
    protected String parseFunctionEquation(Equation equation) throws IncorrectODEEquationException
    {
        String[] eq = equation.getEquation().split("=");
        
        if (eq.length < 2)
            throw new IncorrectODEEquationException("Given equation has incorrect form , lack of assignment sign ( = )");
        
        int i = 0;
        int len = eq[1].length();
        String result = "";
        while (i < len)
        {
            char ch = eq[1].charAt(i);
            if (Character.isLetter(ch) && ch != equation.getIndependentVariable())
            {
                i++;
                int k = 0;
                while (i < len && eq[1].charAt(i) == '\'')
                {
                    k++;
                    i++;
                }
                
                result += ch + Integer.valueOf(k).toString();
            }
            
            if (i < len)
            {
                result += eq[1].charAt(i);
            }
            
            i++;
        }
        
        return result;
    }
    
    protected List<Double> evaluate(List<String> function, double h, Map<String, Double> map)
            throws UnknownFunctionException, UnparsableExpressionException
    {
        if (function.isEmpty())
            return null;
        
        List<Double> results = new ArrayList<Double>();
        
        for (String f : function)
        {
            Calculable calc = new ExpressionBuilder(f).withVariables(map).build();
            results.add(calc.calculate() * h);
        }
        
        return results;
    }
    
    protected List<List<Double>> evaluateSystem(List<List<String>> functions, double h, Map<String, Double> map)
            throws UnknownFunctionException, UnparsableExpressionException
    {
        if (functions.isEmpty())
            return null;
        
        List<List<Double>> results = new ArrayList<List<Double>>();
        
        for (List<String> function : functions)
        {
            List<Double> result = new ArrayList<Double>(function.size());
            for (String f : function)
            {
                Calculable calc = new ExpressionBuilder(f).withVariables(map).build();
                result.add(calc.calculate() * h);
            }
            results.add(result);
        }
        
        return results;
    }
    
    
    protected Map<String, Double> getSum(final List<Double> y, final List<Double> v, final double h, final char f)
    {
        Map<String, Double> map = new HashMap<String, Double>(y.size());
        int k = 0;
        
        for (Double val : y)
        {
            map.put(String.valueOf(f) + k, val + h * v.get(k));
            k++;
        }
        
        return map;
    }
    
    /**
     * returns following vector wich is the result of the following equation y + h*v
     * @param y - vector to add
     * @param v - vector to add
     * @param h - step
     * @return y + h*v
     */
    protected List<Double> getSum(final List<Double> y, final List<Double> v, final double h)
    {
        List<Double> list = new ArrayList<Double>(y.size());
        int k = 0;
        
        for (Double val : y)
        {
            list.add(val + h * v.get(k));
            k++;
        }
        
        return list;
    }
    
    protected Map<String, Double> getSum(final List<List<Double>> functions, final List<List<Double>> vectors,
            final double h, final List<Character> f)
    {
        Map<String, Double> map = new HashMap<String, Double>();
        int v = 0;
        for (List<Double> function : functions)
        {
            int k = 0;
            List<Double> vector = vectors.get(v);
            for (Double val : function)
            {
                map.put(String.valueOf(f.get(v)) + k, val + h * vector.get(k));
                k++;
            }
            v++;
        }
        
        return map;
    }
    
    protected Map<String, Double> getMap(final List<Double> y, final char f)
    {
        Map<String, Double> map = new HashMap<String, Double>(y.size());
        int k = 0;
        
        for (Double val : y)
        {
            map.put(String.valueOf(f) + k, val);
            k++;
        }
        
        return map;
    }
    
    protected Map<String, Double> getMap(final List<List<Double>> initVal, List<Character> f)
    {
        Map<String, Double> map = new HashMap<String, Double>();
        
        int fc = 0;
        for (List<Double> initValues : initVal)
        {
            int k = 0;
            for (Double val : initValues)
            {
                map.put(String.valueOf(f.get(fc)) + k, val);
                k++;
            }
            fc++;
        }
        
        return map;
    }
    
    protected List<Double> evaluateFunction(List<Double> y, final List<Double> k1, final List<Double> k2,
            final List<Double> k3, List<Double> k4)
    {
        for (int i = 0; i < y.size(); i++)
            y.set(i, y.get(i) + (k1.get(i) + 2 * k2.get(i) + 2 * k3.get(i) + k4.get(i)) / 6.0);
        
        return y;
    }
    
    protected List<List<Double>> evaluateFunctions(List<List<Double>> functions, final List<List<Double>> k1,
            final List<List<Double>> k2, final List<List<Double>> k3, List<List<Double>> k4)
    {
        for (int k = 0; k < functions.size(); k++)
        {
            for (int i = 0; i < functions.get(k).size(); i++)
            {
                functions.get(k).set(
                        i,
                        functions.get(k).get(i)
                                + (k1.get(k).get(i) + 2 * k2.get(k).get(i) + 2 * k3.get(k).get(i) + k4.get(k).get(i))
                                / 6.0);
            }
        }
        return functions;
    }
}
