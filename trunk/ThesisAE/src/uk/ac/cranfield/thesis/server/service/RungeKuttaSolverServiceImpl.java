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
import uk.ac.cranfield.thesis.shared.Solution;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;

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
        List<Double> result = new ArrayList<Double>((int) ((stop - start) / step));
        
        // map contains derivative and initial value
        // <y0, 0.0> , <y1, 0.0> , ...
        Map<String, Double> map = null;
        result.add(y.get(y.size() - 1));
        
        for (double i = start; i < stop; i += step)
        {
            map = getMap(y, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i);
            k1 = evaluate(f, map, step);
            
            map = getSum(y, k1, 0.5, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step / 2);
            k2 = evaluate(f, map, step);
            
            map = getSum(y, k2, 0.5, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step / 2);
            k3 = evaluate(f, map, step);
            
            map = getSum(y, k3, 1.0, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step);
            k4 = evaluate(f, map, step);
            
            y = evaluateFunction(y, k1, k2, k3, k4);
            
            result.add(y.get(y.size() - 1));
        }
        
        return new Solution(result, start, stop, step);
    }
    
    private List<String> getFunctionVector(Equation equation) throws IncorrectODEEquationException
    {
        List<String> f = new ArrayList<String>(equation.getOrder());
        
        for (int i = 1; i < equation.getOrder(); i++)
        {
            f.add(equation.getFunctionVariable() + Integer.valueOf(i).toString());
        }
        
        f.add(parseFunctionEquation(equation));
        
        return f;
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
    
    private List<Double> evaluate(List<String> function, Map<String, Double> map, double h)
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
    
    private List<Double> evaluateFunction(List<Double> y, final List<Double> k1, final List<Double> k2,
            final List<Double> k3, List<Double> k4)
    {
        for (int i = 0; i < y.size(); i++)
            y.set(i, y.get(i) + (k1.get(i) + 2 * k2.get(i) + 2 * k3.get(i) + k4.get(i)) / 6.0);
        
        return y;
    }
    
    
}
