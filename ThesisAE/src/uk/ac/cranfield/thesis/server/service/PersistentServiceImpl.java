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

import uk.ac.cranfield.thesis.client.service.PersistentService;
import uk.ac.cranfield.thesis.shared.model.Equation;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class PersistentServiceImpl extends RemoteServiceServlet implements PersistentService
{
    
    @Override
    public void persistEquation(Equation equation)
    {
        ObjectifyService.register(Equation.class);
        Objectify ofy = ObjectifyService.begin();
        
        ofy.put(equation);
    }
    
    @Override
    public Equation getEquation(String name)
    {
        ObjectifyService.register(Equation.class);
        Objectify ofy = ObjectifyService.begin();
        
        Query<Equation> q = ofy.query(Equation.class).filter("name", name);
        
        return q.get();
        
    }
}
