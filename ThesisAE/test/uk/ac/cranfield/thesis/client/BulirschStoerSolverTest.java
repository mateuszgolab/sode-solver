package uk.ac.cranfield.thesis.client;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.solver.BulirschStoerSolverService;
import uk.ac.cranfield.thesis.client.service.solver.BulirschStoerSolverServiceAsync;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class BulirschStoerSolverTest extends GWTTestCase
{
    
    @Override
    public String getModuleName()
    {
        return "uk.ac.cranfield.thesis.ThesisAE";
    }
    
    public void test1OrderEquation()
    {
        BulirschStoerSolverServiceAsync solverService = BulirschStoerSolverService.Util.getInstance();
        delayTestFinish(500);
        
        Equation equation = new Equation("y'=y");
        equation.setFunctionVariable('y');
        equation.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(1.0);
        
        equation.setInitValues(list);
        
        solverService.solve(equation, 0.1, 0.0, 1.0, new AsyncCallback<Solution>()
        {
            
            @Override
            public void onSuccess(Solution result)
            {
                List<Double> results = result.getResults();
                assertEquals(11, results.size());
                int k = 0;
                for (double i = 0.0; i < 1.0; i += 0.1)
                {
                    System.out.println(results.get(k));
                    assertEquals(Math.exp(i), results.get(k), 1.005);
                    k++;
                }
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
