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
package uk.ac.cranfield.thesis.server.service.solver;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.cranfield.thesis.client.service.solver.RungeKuttaSolverService;
import uk.ac.cranfield.thesis.server.Solver;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

@SuppressWarnings("serial")
public class RungeKuttaSolverServiceImpl extends Solver implements RungeKuttaSolverService
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
        
        for (double i = start; i + step / 2.0 < stop; i += step)
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
    public List<Solution> solveSystem(System system, double step, double start, double stop)
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
        
        for (double i = start; i + step / 2.0 < stop; i += step)
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
    
    
}
