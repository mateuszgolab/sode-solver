package uk.ac.cranfield.thesis.client.service;

import java.util.List;

import uk.ac.cranfield.thesis.shared.Equation;
import uk.ac.cranfield.thesis.shared.Solution;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("equation")
public interface EquationsService extends RemoteService
{
    
    Solution evaluate(Equation equation, List<Double> points) throws Exception;
    
}
