package uk.ac.cranfield.thesis.client.service;

import java.util.List;

import uk.ac.cranfield.thesis.shared.Solution;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface EquationsServiceAsync
{
    
    void setEquation(String equation, AsyncCallback<Void> callback);
    
    void getValues(String var, List<Double> points, int coordinate, AsyncCallback<Solution> callback);
    
    
}
