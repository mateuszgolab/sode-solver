package uk.ac.cranfield.thesis.client.service;

import uk.ac.cranfield.thesis.shared.Equation;
import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface PersistentServiceAsync
{
    
    void persistEquation(Equation equation, AsyncCallback<Void> callback);
    
    void getEquation(String name, AsyncCallback<Equation> callback);
    
    void persistEquationsSystem(SystemEntity system, AsyncCallback<Void> callback);
    
    void getEquationsSystem(String name, AsyncCallback<SystemEntity> callback);
    
}
