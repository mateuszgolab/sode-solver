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
package uk.ac.cranfield.thesis.client.service;

import java.util.List;

import uk.ac.cranfield.thesis.shared.Solution;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.EquationsSystem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RungeKuttaSolverService")
public interface RungeKuttaSolverService extends RemoteService
{
    
    Solution solve(Equation equation, double step, double start, double stop) throws IncorrectODEEquationException,
            Exception;
    
    List<Solution> solveSystem(EquationsSystem system, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception;
    
    
    /**
     * Utility class for simplifying access to the instance of async service.
     */
    public static class Util
    {
        
        private static RungeKuttaSolverServiceAsync instance;
        
        public static RungeKuttaSolverServiceAsync getInstance()
        {
            if (instance == null)
            {
                instance = GWT.create(RungeKuttaSolverService.class);
            }
            return instance;
        }
    }
}
