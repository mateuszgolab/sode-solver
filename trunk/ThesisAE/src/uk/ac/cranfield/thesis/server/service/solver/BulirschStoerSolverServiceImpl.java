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

import java.util.List;
import java.util.Map;

import uk.ac.cranfield.thesis.client.service.solver.BulirschStoerSolverService;
import uk.ac.cranfield.thesis.server.Solver;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;

@SuppressWarnings("serial")
public class BulirschStoerSolverServiceImpl extends Solver implements BulirschStoerSolverService
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
        List<Double> z2;
        List<Double> z0 = equation.getInitValues();
        // map contains derivative and initial value
        // <y0, 0.0> , <y1, 0.0> , ... , <z0, 0.5> , <z1, 0.5> , ...
        Map<String, Double> map = getMap(z0, equation.getFunctionVariable());
        // add <x, val3>
        map.put(String.valueOf(equation.getIndependentVariable()), start);
        // z1 = z0 + hf(x, z0)
        List<Double> z1 = getSum(z0, evaluate(f, step, map), step);
        
        for (double i = start + step; i < stop; i += step)
        {
            tmp = z1;
            map = getMap(z1, equation.getFunctionVariable());
            // add <x, val3>
            map.put(String.valueOf(equation.getIndependentVariable()), i);
            z2 = getSum(z0, evaluate(f, 2 * step, map), 1.0);
            
            
            solution.addResult(y.get(y.size() - 1));
        }
        
        return solution;
    }
    
    @Override
    public List<Solution> solveSystem(System system, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception
    {
        return null;
    }
    
    
}
