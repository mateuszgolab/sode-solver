package uk.ac.cranfield.thesis.client;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.shared.model.entity.SystemEntity;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class PersistenceTestCase extends GWTTestCase
{
    
    private SystemPersistenceServiceAsync service;
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    
    @Override
    public String getModuleName()
    {
        return "uk.ac.cranfield.thesis.ThesisAE";
    }
    
    
    @Override
    public void gwtSetUp()
    {
        helper.setUp();
        service = SystemPersistenceService.Util.getInstance();
    }
    
    @Override
    public void gwtTearDown()
    {
        helper.tearDown();
        
    }
    
    public void testLoadEmpty()
    {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        
        
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
                
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
            }
        });
    }
    
    public void testLoadAfterRemovingSystem()
    {
        delayTestFinish(500);
        service.get("testName", new AsyncCallback<SystemEntity>()
        {
            
            @Override
            public void onSuccess(SystemEntity result)
            {
                assertNull(result);
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
