package uk.ac.cranfield.thesis.client.service.solver;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface BulirschStoerSolverServiceAsync
{
    
    void solveSystem(System system, double step, double start, double stop, AsyncCallback<List<Solution>> callback);
    
    void solve(Equation equation, double step, double start, double stop, AsyncCallback<Solution> callback);
    
}
