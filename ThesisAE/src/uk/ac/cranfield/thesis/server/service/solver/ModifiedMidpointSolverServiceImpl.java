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

import uk.ac.cranfield.thesis.client.service.solver.ModifiedMidpointService;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;

@SuppressWarnings("serial")
public class ModifiedMidpointSolverServiceImpl extends Solver implements ModifiedMidpointService
{
    
    @Override
    public Solution solve(Equation equation, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception
    {
        List<Double> y = equation.getInitValues();
        List<String> f = getFunctionVector(equation);
        Solution solution = new Solution(start, stop, step);
        solution.addResult(y.get(y.size() - 1));
        
        List<Double> tmp;
        List<Double> z0 = new ArrayList<Double>(equation.getInitValues());
        // map contains derivative and initial value
        // <y0, 0.0> , <y1, 0.0> , ... , <z0, 0.5> , <z1, 0.5> , ...
        Map<String, Double> map = getMap(z0, equation.getFunctionVariable());
        // add <x, val3>
        map.put(String.valueOf(equation.getIndependentVariable()), start);
        // z2 = z1 + hf(x, z1)
        List<Double> z1 = getSum(z0, evaluate(f, step, map));
        
        for (double i = start; i < stop; i += step)
        {
            map = getMap(z1, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step);
            y = evaluateFunction(y, z1, z0, evaluate(f, step, map));
            solution.addResult(y.get(y.size() - 1));
            
            tmp = z1;
            map = getMap(z1, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), start);
            z1 = getSum(z0, evaluate(f, 2 * step, map));
            z0 = new ArrayList<Double>(tmp);
            
        }
        
        return solution;
    }
    
    @Override
    public List<Solution> solveSystem(System system, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception
    {
        List<List<String>> f = getFunctionVector(system);
        List<List<Double>> functions = system.getInitValues();
        List<Solution> result = new ArrayList<Solution>();
        
        // Solution for y,z, ... at start point
        for (List<Double> list : functions)
        {
            Solution solution = new Solution(start, stop, step);
            solution.addResult(list.get(list.size() - 1));
            result.add(solution);
        }
        
        List<List<Double>> tmp;
        List<List<Double>> z0 = new ArrayList<List<Double>>();
        for (List<Double> init : functions)
            z0.add(new ArrayList<Double>(init));
        // map contains derivative and initial value
        // <y0, 0.0> , <y1, 0.0> , ... , <z0, 0.5> , <z1, 0.5> , ...
        Map<String, Double> map = getMap(z0, system.getFunctionVariables());
        // add <x, val3>
        map.put(String.valueOf(system.getIndependentVariable()), start);
        // z2 = z1 + hf(x, z1)
        List<List<Double>> z1 = getSystemSum(z0, evaluateSystem(f, step, map));
        
        for (double i = start + step; i < stop; i += step)
        {
            map = getMap(z1, system.getFunctionVariables());
            map.put(String.valueOf(system.getIndependentVariable()), i);
            functions = evaluateFunctions(functions, z1, z0, evaluateSystem(f, step, map));
            
            // adding result after step
            int index = 0;
            for (List<Double> list : functions)
            {
                result.get(index).addResult(list.get(list.size() - 1));
                index++;
            }
            
            tmp = z1;
            map = getMap(z1, system.getFunctionVariables());
            map.put(String.valueOf(system.getIndependentVariable()), i);
            z1 = getSystemSum(z0, evaluateSystem(f, 2 * step, map));
            z0 = new ArrayList<List<Double>>(tmp);
            
        }
        
        return result;
    }
    
    private List<Double> evaluateFunction(List<Double> y, final List<Double> z0, final List<Double> z1,
            final List<Double> f)
    {
        for (int i = 0; i < y.size(); i++)
            y.set(i, 0.5 * (z1.get(i) + z0.get(i) + f.get(i)));
        
        return y;
    }
    
    private List<List<Double>> evaluateFunctions(List<List<Double>> y, final List<List<Double>> z0,
            final List<List<Double>> z1, final List<List<Double>> f)
    {
        for (int i = 0; i < y.size(); i++)
        {
            int size = y.get(i).size();
            for (int j = 0; j < size; j++)
            {
                y.get(i).set(j, 0.5 * (z1.get(i).get(j) + z0.get(i).get(j) + f.get(i).get(j)));
            }
        }
        
        return y;
    }
    
    /**
     * returns following vector wich is the result of the following equation y + h*v
     * @param y - vector to add
     * @param v - vector to add
     * @param h - step
     * @return y + h*v
     */
    private List<Double> getSum(final List<Double> y, final List<Double> v)
    {
        List<Double> list = new ArrayList<Double>(y.size());
        int k = 0;
        
        for (Double val : y)
        {
            list.add(val + v.get(k));
            k++;
        }
        
        return list;
    }
    
    private List<List<Double>> getSystemSum(List<List<Double>> y, List<List<Double>> v)
    {
        List<List<Double>> list = new ArrayList<List<Double>>(y.size());
        
        for (int i = 0; i < y.size(); i++)
        {
            int k = 0;
            List<Double> vi = v.get(i);
            List<Double> res = new ArrayList<Double>();
            
            for (Double val : y.get(i))
            {
                res.add(val + vi.get(k));
                k++;
            }
            list.add(res);
        }
        return list;
    }
    
}
