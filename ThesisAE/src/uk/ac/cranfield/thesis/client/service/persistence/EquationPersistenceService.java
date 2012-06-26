package uk.ac.cranfield.thesis.client.service.persistence;

import uk.ac.cranfield.thesis.shared.Equation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("EquationPersistentService")
public interface EquationPersistenceService extends RemoteService
{
    
    void persistEquation(Equation equation);
    
    Equation getEquation(String name);
    
    /**
     * Utility class for simplifying access to the instance of async service.
     */
    public static class Util
    {
        
        private static EquationPersistenceServiceAsync instance;
        
        public static EquationPersistenceServiceAsync getInstance()
        {
            if (instance == null)
            {
                instance = GWT.create(EquationPersistenceService.class);
            }
            return instance;
        }
    }
}
