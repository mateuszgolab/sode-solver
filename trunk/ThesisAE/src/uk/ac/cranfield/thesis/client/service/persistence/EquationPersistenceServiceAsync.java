package uk.ac.cranfield.thesis.client.service.persistence;

import uk.ac.cranfield.thesis.shared.model.Equation;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface EquationPersistenceServiceAsync
{
    
    void getEquation(String name, AsyncCallback<Equation> callback);
    
    void persistEquation(Equation equation, AsyncCallback<Void> callback);
    
    
}
