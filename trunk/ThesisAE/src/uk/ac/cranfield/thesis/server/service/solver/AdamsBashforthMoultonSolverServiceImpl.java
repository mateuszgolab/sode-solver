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
    
    @Override
    public Solution solve(Equation equation, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception
    {
        List<String> f = getFunctionVector(equation);
        Solution solution = rungeKuttaEvaluation(start, step, stop, equation);
        
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
            yn1 = predictor(step, i, equation, f, yn_2, yn_1, yn);
            yn1 = corrector(step, i, equation, f, yn_1, yn, yn1);
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
        // TODO Auto-generated method stub
        return null;
    }
    
    private List<Double> predictor(final double step, final double n, final Equation equation, final List<String> f,
            List<Double> yn3, List<Double> yn2, List<Double> yn1) throws UnknownFunctionException,
            UnparsableExpressionException
    {
        List<Double> result = new ArrayList<Double>();
        
        // f(xn-1, yn-1)
        Map<String, Double> map = getMap(yn1, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n);
        List<Double> fn1 = evaluate(f, 23.0, map);
        
        // f(xn-2, yn-2)
        map = getMap(yn2, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n - step);
        List<Double> fn2 = evaluate(f, -16.0, map);
        
        // f(xn-3, yn-3)
        map = getMap(yn3, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n - 2 * step);
        List<Double> fn3 = evaluate(f, 5.0, map);
        
        for (int i = 0; i < yn3.size(); i++)
        {
            result.add(yn1.get(i) + step / 12.0 * (fn1.get(i) + fn2.get(i) + fn3.get(i)));
        }
        
        return result;
        
    }
    
    private List<Double> corrector(final double step, final double n, final Equation equation, final List<String> f,
            List<Double> yn2, List<Double> yn1, List<Double> yn) throws UnknownFunctionException,
            UnparsableExpressionException
    {
        List<Double> result = new ArrayList<Double>();
        
        // f(xn, yn)
        Map<String, Double> map = getMap(yn, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n);
        List<Double> fn = evaluate(f, 5.0, map);
        
        // f(xn-1, yn-1)
        map = getMap(yn1, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n - step);
        List<Double> fn1 = evaluate(f, 8.0, map);
        
        // f(xn-2, yn-2)
        map = getMap(yn2, equation.getFunctionVariable());
        map.put(String.valueOf(equation.getIndependentVariable()), n - 2 * step);
        List<Double> fn2 = evaluate(f, -1.0, map);
        
        for (int i = 0; i < yn.size(); i++)
        {
            result.add(yn.get(i) + step / 12.0 * (fn.get(i) + fn1.get(i) + fn2.get(i)));
        }
        
        return result;
    }
    
    private Solution rungeKuttaEvaluation(final double start, final double step, final double stop,
            final Equation equation) throws UnknownFunctionException, UnparsableExpressionException,
            IncorrectODEEquationException
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
        
        // y1,y2 evaluation
        for (double i = start; i < stop; i += step)
        {
            map = getMap(y, equation.getFunctionVariable());
            map.put(String.valueOf(equation.getIndependentVariable()), i + step);
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
}
