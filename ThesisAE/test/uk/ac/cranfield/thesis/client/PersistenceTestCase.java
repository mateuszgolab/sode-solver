package uk.ac.cranfield.thesis.client;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.shared.model.entity.SystemEntity;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;


public class PersistenceTestCase extends GWTTestCase
{
    
    private SystemPersistenceServiceAsync service;
    
    @Override
    public String getModuleName()
    {
        return "uk.ac.cranfield.thesis.ThesisAE";
    }
    
    @Override
    public void gwtSetUp()
    {
        service = SystemPersistenceService.Util.getInstance();
        // ObjectifyService.register(SystemEntity.class);
    }
    
    public void testStoreSystem()
    {
        delayTestFinish(500);
        SystemEntity entity = new SystemEntity();
        entity.setName("testName");
        List<String> equations = new ArrayList<String>();
        equations.add("eq1");
        equations.add("eq2");
        entity.setEquations(equations);
        service.persist(entity, new AsyncCallback<Void>()
        {
            
            @Override
            public void onSuccess(Void result)
            {
                Objectify ofy = ObjectifyService.begin();
                Query<SystemEntity> q = ofy.query(SystemEntity.class).filter("name", "testName");
                
                SystemEntity res = q.get();
                assertNotNull(res);
                assertEquals("testName", res.getName());
                assertEquals("eq1", res.getEquations().get(0));
                assertEquals("eq2", res.getEquations().get(1));
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
            }
        });
    }
    
    public void testLoadSystem()
    {
        delayTestFinish(500);
        service.get("testName", new AsyncCallback<SystemEntity>()
        {
            
            @Override
            public void onSuccess(SystemEntity result)
            {
                assertNotNull(result);
                assertNotNull(result);
                assertEquals("testName", result.getName());
                assertEquals("eq1", result.getEquations().get(0));
                assertEquals("eq2", result.getEquations().get(1));
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
            }
        });
        
    }
    
    public void testRemoveSystem()
    {
        delayTestFinish(500);
        service.remove("testName", new AsyncCallback<String>()
        {
            
            @Override
            public void onSuccess(String result)
            {
                
                Objectify ofy = ObjectifyService.begin();
                Query<SystemEntity> q = ofy.query(SystemEntity.class).filter("name", "testName");
                
                assertNull(q.get());
                
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
            }
        });
    }
}
