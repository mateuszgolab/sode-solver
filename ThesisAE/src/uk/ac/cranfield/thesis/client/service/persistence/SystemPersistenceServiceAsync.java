package uk.ac.cranfield.thesis.client.service.persistence;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface SystemPersistenceServiceAsync
{
    
    void persist(SystemEntity system, AsyncCallback<Void> callback);
    
    void get(String name, AsyncCallback<SystemEntity> callback);
    
    void getAll(AsyncCallback<List<SystemEntity>> callback);
    
}
