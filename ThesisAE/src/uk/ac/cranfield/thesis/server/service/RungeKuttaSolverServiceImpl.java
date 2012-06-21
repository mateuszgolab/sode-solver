/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uk.ac.cranfield.thesis.server.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.cranfield.thesis.client.service.RungeKuttaSolverService;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.EquationsSystem;
import uk.ac.cranfield.thesis.shared.model.Solution;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

@SuppressWarnings("serial")
public class RungeKuttaSolverServiceImpl extends RemoteServiceServlet implements RungeKuttaSolverService
{
    
    @Override
    public Solution solve(Equation equation, double step, double start, double stop)
            throws IncorrectODEEquationException, UnknownFunctionException, UnparsableExpressionException
    {
        
        List<Double> k1 = new ArrayList<Double>();
        List<Double> k2 = new ArrayList<Double>();
        List<Double> k3 = new ArrayList<Double>();
        List<Double> k4 = new ArrayList<Double>();
        List<Double> y = equation.getInitValues();
        List<String> f = getFunctionVector(equation);
        Solution solution = new Solution(start, stop, step);
        
        // map contains derivative and initial value
        // <y0, 0.0> , <y1, 0.0> , ... , <z0, 0.5> , <z1, 0.5> , ...
        Map<String, Double> map = null;
        solution.addResult(y.get(y.size() - 1));
        
        for (double i = start; i < stop; i += step)
        {
            // add <y0, val>, <y1, val2> ....
            map = getMap(y, equation.getFunctionVariable());
            // add <x, val3>
            map.put(String.valueOf(equation.getIndependentVariable()), i);
            k1 = evaluate(f, step, map);
            
            map = getSum(y, k1, 0.5, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step / 2);
            k2 = evaluate(f, step, map);
            
            map = getSum(y, k2, 0.5, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step / 2);
            k3 = evaluate(f, step, map);
            
            map = getSum(y, k3, 1.0, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step);
            k4 = evaluate(f, step, map);
            
            y = evaluateFunction(y, k1, k2, k3, k4);
            
            solution.addResult(y.get(y.size() - 1));
        }
        
        return solution;
    }
    
    @Override
    public List<Solution> solveSystem(EquationsSystem system, double step, double start, double stop)
            throws IncorrectODEEquationException, UnknownFunctionException, UnparsableExpressionException
    {
        List<List<Double>> k1 = new ArrayList<List<Double>>();
        List<List<Double>> k2 = new ArrayList<List<Double>>();
        List<List<Double>> k3 = new ArrayList<List<Double>>();
        List<List<Double>> k4 = new ArrayList<List<Double>>();
        List<List<Double>> functions = system.getInitValues();
        List<List<String>> f = getFunctionVector(system);
        List<Solution> result = new ArrayList<Solution>();
        
        // Solution for y,z, ... at start point
        for (List<Double> list : functions)
        {
            Solution solution = new Solution(start, stop, step);
            solution.addResult(list.get(list.size() - 1));
            result.add(solution);
        }
        
        for (double i = start; i < stop; i += step)
        {
            // map contains derivative and initial value
            // <y0, 0.0> , <y1, 0.0> , ...
            Map<String, Double> map = null;
            
            map = getMap(functions, system.getFunctionVariables());
            map.put(String.valueOf(system.getIndependentVariable()), i);
            k1 = evaluateSystem(f, step, map);
            
            map = getSum(functions, k1, 0.5, system.getFunctionVariables());
            map.put(String.valueOf(system.getIndependentVariable()), i + step / 2);
            k2 = evaluateSystem(f, step, map);
            
            map = getSum(functions, k2, 0.5, system.getFunctionVariables());
            map.put(String.valueOf(system.getIndependentVariable()), i + step / 2);
            k3 = evaluateSystem(f, step, map);
            
            map = getSum(functions, k3, 1.0, system.getFunctionVariables());
            map.put(String.valueOf(system.getIndependentVariable()), i + step);
            k4 = evaluateSystem(f, step, map);
            
            functions = evaluateFunctions(functions, k1, k2, k3, k4);
            
            // adding result after step
            int index = 0;
            for (List<Double> list : functions)
            {
                result.get(index).addResult(list.get(list.size() - 1));
                index++;
            }
        }
        
        return result;
    }
    
    private List<String> getFunctionVector(Equation equation) throws IncorrectODEEquationException
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
    
    private List<List<String>> getFunctionVector(EquationsSystem system) throws IncorrectODEEquationException
    {
        List<List<String>> f = new ArrayList<List<String>>();
        List<Equation> equations = system.getEquations();
        
        for (Equation eq : equations)
        {
            f.add(getFunctionVector(eq));
        }
        
        return f;
    }
    
    private String parseFunctionEquation2(Equation equation) throws IncorrectODEEquationException
    {
        String[] eq = equation.getEquation().split("=");
        
        if (eq.length < 2)
            throw new IncorrectODEEquationException("Given equation has incorrect form , lack of assignment sign ( = )");
        
        int i = 0;
        String result = "";
        while (i < eq[1].length())
        {
            if (eq[1].charAt(i) == equation.getFunctionVariable())
            {
                i++;
                int k = 0;
                while (i < eq[1].length() && eq[1].charAt(i) == '\'')
                {
                    k++;
                    i++;
                }
                
                result += equation.getFunctionVariable() + Integer.valueOf(k).toString();
            }
            
            result += eq[1].charAt(i);
            i++;
        }
        
        return result;
    }
    
    private String parseFunctionEquation(Equation equation) throws IncorrectODEEquationException
    {
        String[] eq = equation.getEquation().split("=");
        
        if (eq.length < 2)
            throw new IncorrectODEEquationException("Given equation has incorrect form , lack of assignment sign ( = )");
        
        int i = 0;
        String result = "";
        while (i < eq[1].length())
        {
            char ch = eq[1].charAt(i);
            if (Character.isLetter(ch) && ch != equation.getIndependentVariable())
            {
                i++;
                int k = 0;
                while (i < eq[1].length() && eq[1].charAt(i) == '\'')
                {
                    k++;
                    i++;
                }
                
                result += ch + Integer.valueOf(k).toString();
            }
            
            result += eq[1].charAt(i);
            i++;
        }
        
        return result;
    }
    
    private List<Double> evaluate(List<String> function, double h, Map<String, Double> map)
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
    
    private List<List<Double>> evaluateSystem(List<List<String>> functions, double h, Map<String, Double> map)
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
    
    private Map<String, Double> getSum(final List<Double> y, final List<Double> v, final double h, final char f)
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
    
    private Map<String, Double> getSum(final List<List<Double>> functions, final List<List<Double>> vectors,
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
    
    private Map<String, Double> getMap(final List<Double> y, final char f)
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
    
    private Map<String, Double> getMap(final List<List<Double>> initVal, List<Character> f)
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
    
    private List<Double> evaluateFunction(List<Double> y, final List<Double> k1, final List<Double> k2,
            final List<Double> k3, List<Double> k4)
    {
        for (int i = 0; i < y.size(); i++)
            y.set(i, y.get(i) + (k1.get(i) + 2 * k2.get(i) + 2 * k3.get(i) + k4.get(i)) / 6.0);
        
        return y;
    }
    
    private List<List<Double>> evaluateFunctions(List<List<Double>> functions, final List<List<Double>> k1,
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
