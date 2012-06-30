package uk.ac.cranfield.thesis.client.service.persistence;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.Solution;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface SolutionPersistenceServiceAsync
{
    
    void persist(Solution solution, AsyncCallback<Void> callback);
    
    void get(String name, AsyncCallback<Solution> callback);
    
    void getAll(AsyncCallback<List<Solution>> callback);
    
    void remove(String name, AsyncCallback<String> callback);
    
    void removeAll(AsyncCallback<Void> callback);
    
}
