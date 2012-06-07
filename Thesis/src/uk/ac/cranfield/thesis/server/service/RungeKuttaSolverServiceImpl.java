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
import uk.ac.cranfield.thesis.shared.Equation;
import uk.ac.cranfield.thesis.shared.Solution;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

@SuppressWarnings("serial")
public class RungeKuttaSolverServiceImpl extends RemoteServiceServlet implements RungeKuttaSolverService
{
    
    @Override
    public Solution solve(Equation equation) throws IncorrectODEEquationException, UnknownFunctionException,
            UnparsableExpressionException
    {
        double h = 1.0;
        double start = 0.0;
        double stop = 100.0;
        
        List<Double> k1 = new ArrayList<Double>();
        List<Double> k2 = new ArrayList<Double>();
        List<Double> k3 = new ArrayList<Double>();
        List<Double> k4 = new ArrayList<Double>();
        List<Double> y = equation.getInitValues();
        List<String> f = getFunctionVector(equation);
        
        // map contains derivative and initial value
        // <y0, 0.0> , <y1, 0.0> , ...
        Map<String, Double> map = new HashMap<String, Double>(equation.getOrder());
        
        int k = 0;
        for (Double val : y)
        {
            map.put("y" + k, val);
            k++;
        }
        
        for (double i = start; i < stop; i += h)
        {
            map.put(String.valueOf(equation.getIndependentVariable()), i);
            k1 = evaluate(f, map, h);
            
            map.put(String.valueOf(equation.getIndependentVariable()), i + h / 2);
            k1 = evaluate(f, map, h);
            
            
        }
        
        return new Solution();
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
    
    private Map<String, Double> addVectors(Map<String, Double> y, List<Double> v)
    {
        // for()
        
        return y;
    }
}
