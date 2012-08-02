package uk.ac.cranfield.thesis.client.service.persistence;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.entity.SystemEntity;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface SystemPersistenceServiceAsync
{
    
    void persist(SystemEntity system, AsyncCallback<Void> callback);
    
    void get(String name, AsyncCallback<SystemEntity> callback);
    
    void getAll(AsyncCallback<List<SystemEntity>> callback);
    
    void remove(String name, AsyncCallback<String> callback);
    
    void removeAll(AsyncCallback<Void> callback);
    
}