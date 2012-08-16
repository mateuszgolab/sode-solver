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

import uk.ac.cranfield.thesis.client.service.solver.AdamsBashforthMoultonSolverService;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

@SuppressWarnings("serial")
public class AdamsBashforthMoultonSolverServiceImpl extends Solver implements AdamsBashforthMoultonSolverService
{
    
    private final RungeKuttaSolverServiceImpl rungeKuttaSolverServiceImpl = new RungeKuttaSolverServiceImpl();
    
    @Override
    public Solution solve(Equation equation, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception
    {
        List<String> f = getFunctionVector(equation);
        Solution solution = rungeKuttaSolverServiceImpl.solve(equation, step, start, start + 2 * step);
        
        if (solution.getResults().size() < 3)
            throw new IncorrectODEEquationException("Error : Not enough initial values to solve equation");
        
        List<Double> yn_2 = new ArrayList<Double>();
        yn_2.add(solution.getResult(0));
        
        List<Double> yn_1 = new ArrayList<Double>();
        yn_1.add(solution.getResult(1));
        
        List<Double> yn = new ArrayList<Double>();
        yn.add(solution.getResult(2));
        
        List<Double> yn1 = new ArrayList<Double>();
        
        
        for (double i = start + 3 * step; i < stop + step / 2.0; i += step)
        {
            yn1 = predictor(step, i - step, equation, f, yn, yn_1, yn_2);
            yn1 = corrector(step, i - step, equation, f, yn1, yn, yn_1);
            yn_2 = new ArrayList<Double>(yn_1);
            yn_1 = new ArrayList<Double>(yn);
            yn = new ArrayList<Double>(yn1);
            solution.addResult(yn.get(yn.size() - 1));
        }
        
        return solution;
    }
    
    @Override
    public List<Solution> solveSystem(System system, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception
    {
        List<List<String>> f = getFunctionVector(system);
        List<Solution> result = rungeKuttaSolverServiceImpl.solveSystem(system, step, start, start + 2 * step);
        
        for (Solution s : result)
        {
            if (s.getResults().size() < 3)
                throw new IncorrectODEEquationException(
                        "Error : Not enough initial values to solve system of equations");
        }
        
        List<List<Double>> yn_2 = new ArrayList<List<Double>>();
        for (Solution s : result)
        {
            List<Double> tmp = new ArrayList<Double>();
            tmp.add(s.getResult(0));
            yn_2.add(tmp);
        }
        
        List<List<Double>> yn_1 = new ArrayList<List<Double>>();
        for (Solution s : result)
        {
            List<Double> tmp = new ArrayList<Double>();
            tmp.add(s.getResult(1));
            yn_1.add(tmp);
        }
        
        List<List<Double>> yn = new ArrayList<List<Double>>();
        for (Solution s : result)
        {
            List<Double> tmp = new ArrayList<Double>();
            tmp.add(s.getResult(2));
            yn.add(tmp);
        }
        
        List<List<Double>> yn1 = new ArrayList<List<Double>>();
        
        
        for (double i = start + 3 * step; i < stop + step / 2.0; i += step)
        {
            yn1 = predictor(step, i - step, system, f, yn, yn_1, yn_2);
            yn1 = corrector(step, i - step, system, f, yn1, yn, yn_1);
            yn_2 = new ArrayList<List<Double>>(yn_1);
            yn_1 = new ArrayList<List<Double>>(yn);
            yn = new ArrayList<List<Double>>(yn1);
            
            for (int ii = 0; ii < result.size(); ii++)
            {
                result.get(ii).addResult(yn.get(ii).get(yn.get(ii).size() - 1));
            }
        }
        
        return result;
    }
    
    private List<Double> predictor(final double step, final double i, final Equation equation, final List<String> f,
            List<Double> yn, List<Double> yn_1, List<Double> yn_2) throws UnknownFunctionException,
            UnparsableExpressionException
    {
        List<Double> result = new ArrayList<Double>();
        
        // f(xn, yn)
        Map<String, Double> map = getMap(yn, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), i);
        List<Double> fn1 = evaluate(f, 23.0, map);
        
        // f(xn-1, yn-1)
        map = getMap(yn_1, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), i - step);
        List<Double> fn2 = evaluate(f, -16.0, map);
        
        // f(xn-2, yn-2)
        map = getMap(yn_2, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), i - 2 * step);
        List<Double> fn3 = evaluate(f, 5.0, map);
        
        for (int j = 0; j < yn.size(); j++)
        {
            result.add(yn.get(j) + (step / 12.0) * (fn1.get(j) + fn2.get(j) + fn3.get(j)));
        }
        
        return result;
        
    }
    
    private List<Double> corrector(final double step, final double n, final Equation equation, final List<String> f,
            List<Double> yn1, List<Double> yn, List<Double> yn_1) throws UnknownFunctionException,
            UnparsableExpressionException
    {
        List<Double> result = new ArrayList<Double>();
        
        // f(xn+1, yn+1)
        Map<String, Double> map = getMap(yn1, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n + step);
        List<Double> fn = evaluate(f, 5.0, map);
        
        // f(xn, yn)
        map = getMap(yn, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n);
        List<Double> fn1 = evaluate(f, 8.0, map);
        
        // f(xn-1, yn-1)
        map = getMap(yn_1, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n - step);
        List<Double> fn2 = evaluate(f, -1.0, map);
        
        for (int i = 0; i < yn.size(); i++)
        {
            result.add(yn.get(i) + (step / 12.0) * (fn.get(i) + fn1.get(i) + fn2.get(i)));
        }
        
        return result;
    }
    
    private List<List<Double>> predictor(final double step, final double i, final System system,
            final List<List<String>> f, List<List<Double>> yn, List<List<Double>> yn_1, List<List<Double>> yn_2)
            throws UnknownFunctionException, UnparsableExpressionException
    {
        List<List<Double>> results = new ArrayList<List<Double>>();
        
        // f(xn, yn)
        Map<String, Double> map = getMap(yn, system.getFunctionVariables());
        map.put(String.valueOf(system.getIndependentVariable()), i);
        List<List<Double>> fn1 = evaluateSystem(f, 23.0, map);
        
        // f(xn-1, yn-1)
        map = getMap(yn_1, system.getFunctionVariables());
        map.put(String.valueOf(system.getIndependentVariable()), i - step);
        List<List<Double>> fn2 = evaluateSystem(f, -16.0, map);
        
        // f(xn-2, yn-2)
        map = getMap(yn_2, system.getFunctionVariables());
        map.put(String.valueOf(system.getIndependentVariable()), i - 2 * step);
        List<List<Double>> fn3 = evaluateSystem(f, 5.0, map);
        
        for (int ii = 0; ii < yn.size(); ii++)
        {
            List<Double> res = new ArrayList<Double>();
            for (int j = 0; j < yn.get(ii).size(); j++)
            {
                res.add(yn.get(ii).get(j) + (step / 12.0)
                        * (fn1.get(ii).get(j) + fn2.get(ii).get(j) + fn3.get(ii).get(j)));
            }
            results.add(res);
        }
        
        return results;
    }
    
    private List<List<Double>> corrector(final double step, final double n, final System system,
            final List<List<String>> f, List<List<Double>> yn1, List<List<Double>> yn, List<List<Double>> yn_1)
            throws UnknownFunctionException, UnparsableExpressionException
    {
        List<List<Double>> results = new ArrayList<List<Double>>();
        
        // f(xn+1, yn+1)
        Map<String, Double> map = getMap(yn1, system.getFunctionVariables());
        map.put(String.valueOf(system.getIndependentVariable()), n + step);
        List<List<Double>> fn = evaluateSystem(f, 5.0, map);
        
        // f(xn, yn)
        map = getMap(yn, system.getFunctionVariables());
        map.put(String.valueOf(system.getIndependentVariable()), n);
        List<List<Double>> fn1 = evaluateSystem(f, 8.0, map);
        
        // f(xn-1, yn-1)
        map = getMap(yn_1, system.getFunctionVariables());
        map.put(String.valueOf(system.getIndependentVariable()), n - step);
        List<List<Double>> fn2 = evaluateSystem(f, -1.0, map);
        
        for (int ii = 0; ii < yn.size(); ii++)
        {
            List<Double> res = new ArrayList<Double>();
            for (int j = 0; j < yn.get(ii).size(); j++)
            {
                res.add(yn.get(ii).get(j) + (step / 12.0)
                        * (fn.get(ii).get(j) + fn1.get(ii).get(j) + fn2.get(ii).get(j)));
            }
            results.add(res);
        }
        
        return results;
    }
    
    
}
