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
package uk.ac.cranfield.thesis.client.service.persistence;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.Solution;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SolutionPersistenceService")
public interface SolutionPersistenceService extends RemoteService
{
    
    void persist(Solution solution);
    
    Solution get(String name);
    
    List<Solution> getAll();
    
    String remove(String name);
    
    void removeAll();
    
    /**
     * Utility class for simplifying access to the instance of async service.
     */
    public static class Util
    {
        
        private static SolutionPersistenceServiceAsync instance;
        
        public static SolutionPersistenceServiceAsync getInstance()
        {
            if (instance == null)
            {
                instance = GWT.create(SolutionPersistenceService.class);
            }
            return instance;
        }
    }
}
