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
package uk.ac.cranfield.thesis.server.service.persistence;

import java.util.List;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class SystemPersistenceServiceImpl extends RemoteServiceServlet implements SystemPersistenceService
{
    
    static
    {
        ObjectifyService.register(SystemEntity.class);
    }
    
    
    @Override
    public void persist(SystemEntity system)
    {
        Objectify ofy = ObjectifyService.begin();
        ofy.put(system);
        
    }
    
    @Override
    public SystemEntity get(String name)
    {
        Objectify ofy = ObjectifyService.begin();
        Query<SystemEntity> q = ofy.query(SystemEntity.class).filter("name", name);
        
        return q.get();
    }
    
    @Override
    public List<SystemEntity> getAll()
    {
        Objectify ofy = ObjectifyService.begin();
        Query<SystemEntity> q = ofy.query(SystemEntity.class);
        
        return q.list();
    }
    
    @Override
    public String remove(String name)
    {
        Objectify ofy = ObjectifyService.begin();
        ofy.delete(SystemEntity.class, name);
        
        return name;
    }
    
    @Override
    public void removeAll()
    {
        Objectify ofy = ObjectifyService.begin();
        ofy.delete(getAll());
        
    }
}
