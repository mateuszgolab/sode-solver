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

import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.EquationsSystem;
import uk.ac.cranfield.thesis.shared.model.Solution;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RungeKuttaSolverServiceAsync
{
    
    void solve(Equation equation, double step, double start, double stop, AsyncCallback<Solution> callback);
    
    void solveSystem(EquationsSystem system, double step, double start, double stop,
            AsyncCallback<List<Solution>> callback);
}
