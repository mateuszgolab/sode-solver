package uk.ac.cranfield.thesis.client;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.RungeKuttaSolverService;
import uk.ac.cranfield.thesis.client.service.RungeKuttaSolverServiceAsync;
import uk.ac.cranfield.thesis.shared.Solution;
import uk.ac.cranfield.thesis.shared.model.Equation;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class RungeKuttaSolverTest extends GWTTestCase
{
    
    @Override
    public String getModuleName()
    {
        return "uk.ac.cranfield.thesis.ThesisAE";
    }
    
    public void testRungeKutta1Order()
    {
        RungeKuttaSolverServiceAsync solverService = RungeKuttaSolverService.Util.getInstance();
        delayTestFinish(500);
        
        Equation equation = new Equation("y'=-2*y+x+4");
        equation.setFunctionVariable('y');
        equation.setIndependentVariable('x');
        equation.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(1.0);
        
        equation.setInitValues(list);
        
        solverService.solve(equation, 0.2, 0.0, 0.2, new AsyncCallback<Solution>()
        {
            
            @Override
            public void onSuccess(Solution result)
            {
                assertEquals(1.3472, result.getResult(1), 0.00005);
                finishTest();
                
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
                
            }
        });
        
    }
    
    public void testRungeKuttaSystem1Order()
    {
        RungeKuttaSolverServiceAsync solverService = RungeKuttaSolverService.Util.getInstance();
        delayTestFinish(500);
        
        Equation equation = new Equation("y'=-2*y+x+4");
        equation.setFunctionVariable('y');
        equation.setIndependentVariable('x');
        equation.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(1.0);
        
        equation.setInitValues(list);
        
        solverService.solve(equation, 0.2, 0.0, 0.2, new AsyncCallback<Solution>()
        {
            
            @Override
            public void onSuccess(Solution result)
            {
                assertEquals(1.3472, result.getResult(1), 0.00005);
                finishTest();
                
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
                
            }
        });
        
    }
}
