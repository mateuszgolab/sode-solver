package uk.ac.cranfield.thesis.client.service;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface EquationsServiceAsync
{
    
    void evaluate(Equation equation, List<Double> points, AsyncCallback<Solution> callback);
    
}
