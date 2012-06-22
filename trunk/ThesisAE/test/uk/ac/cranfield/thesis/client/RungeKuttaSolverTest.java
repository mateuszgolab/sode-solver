package uk.ac.cranfield.thesis.client;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.RungeKuttaSolverService;
import uk.ac.cranfield.thesis.client.service.RungeKuttaSolverServiceAsync;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.EquationsSystem;
import uk.ac.cranfield.thesis.shared.model.Solution;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class RungeKuttaSolverTest extends GWTTestCase
{
    
    @Override
    public String getModuleName()
    {
        return "uk.ac.cranfield.thesis.ThesisAE";
    }
    
    public void testRungeKutta1OrderEquation()
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
    
    public void testRungeKutta1OrderSystem()
    {
        RungeKuttaSolverServiceAsync solverService = RungeKuttaSolverService.Util.getInstance();
        delayTestFinish(500);
        
        Equation equation = new Equation("y'= y + x");
        equation.setIndependentVariable('x');
        equation.setFunctionVariable('y');
        equation.setOrder(1);
        
        Equation equation2 = new Equation("z'= z + y + x");
        equation2.setIndependentVariable('x');
        equation2.setFunctionVariable('z');
        equation2.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(0.0);
        
        List<Double> list2 = new ArrayList<Double>();
        list2.add(0.0);
        
        equation.setInitValues(list);
        equation2.setInitValues(list2);
        List<Equation> equations = new ArrayList<Equation>(2);
        equations.add(equation);
        equations.add(equation2);
        EquationsSystem system = new EquationsSystem(equations);
        
        solverService.solveSystem(system, 0.1, 0.0, 1.0, new AsyncCallback<List<Solution>>()
        {
            
            @Override
            public void onSuccess(List<Solution> result)
            {
                assertEquals(2, result.size());
                assertEquals(-0.5 + Math.exp(0.5) - 1.0, result.get(0).getResult(5), 0.00005);
                assertEquals(Math.exp(0.5) * (0.5 - 1.0) + 1, result.get(1).getResult(5), 0.00005);
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
    
    public void testRungeKutta1OrderSystem2()
    {
        RungeKuttaSolverServiceAsync solverService = RungeKuttaSolverService.Util.getInstance();
        delayTestFinish(500);
        
        Equation equation = new Equation("y'= y + x");
        equation.setIndependentVariable('x');
        equation.setFunctionVariable('y');
        equation.setOrder(1);
        
        Equation equation2 = new Equation("z'= z+x+y");
        equation2.setIndependentVariable('x');
        equation2.setFunctionVariable('z');
        equation2.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(0.0);
        
        List<Double> list2 = new ArrayList<Double>();
        list2.add(0.0);
        
        equation.setInitValues(list);
        equation2.setInitValues(list2);
        List<Equation> equations = new ArrayList<Equation>(2);
        equations.add(equation);
        equations.add(equation2);
        EquationsSystem system = new EquationsSystem(equations);
        
        solverService.solveSystem(system, 0.1, 0.0, 1.0, new AsyncCallback<List<Solution>>()
        {
            
            @Override
            public void onSuccess(List<Solution> result)
            {
                assertEquals(2, result.size());
                assertEquals(-0.5 + Math.exp(0.5) - 1.0, result.get(0).getResult(5), 0.00005);
                assertEquals(Math.exp(0.5) * (0.5 - 1.0) + 1, result.get(1).getResult(5), 0.00005);
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
    
    public void testRungeKutta1OrderSystem3()
    {
        RungeKuttaSolverServiceAsync solverService = RungeKuttaSolverService.Util.getInstance();
        delayTestFinish(500);
        
        Equation equation = new Equation("y'= y + x");
        equation.setIndependentVariable('x');
        equation.setFunctionVariable('y');
        equation.setOrder(1);
        
        Equation equation2 = new Equation("z'= z + y + x");
        equation2.setIndependentVariable('x');
        equation2.setFunctionVariable('z');
        equation2.setOrder(1);
        
        Equation equation3 = new Equation("u'= u + x + 4");
        equation3.setIndependentVariable('x');
        equation3.setFunctionVariable('u');
        equation3.setOrder(1);
        
        
        List<Double> list = new ArrayList<Double>();
        list.add(0.0);
        
        List<Double> list2 = new ArrayList<Double>();
        list2.add(0.0);
        
        List<Double> list3 = new ArrayList<Double>();
        list3.add(0.0);
        
        equation.setInitValues(list);
        equation2.setInitValues(list2);
        equation3.setInitValues(list3);
        
        List<Equation> equations = new ArrayList<Equation>(3);
        
        equations.add(equation);
        equations.add(equation2);
        equations.add(equation3);
        
        EquationsSystem system = new EquationsSystem(equations);
        
        solverService.solveSystem(system, 0.1, 0.0, 1.0, new AsyncCallback<List<Solution>>()
        {
            
            @Override
            public void onSuccess(List<Solution> result)
            {
                assertEquals(3, result.size());
                assertEquals(-0.5 + Math.exp(0.5) - 1.0, result.get(0).getResult(5), 0.00005);
                assertEquals(Math.exp(0.5) * (0.5 - 1.0) + 1, result.get(1).getResult(5), 0.00005);
                assertEquals(-0.5 + 5 * Math.exp(0.5) - 5.0, result.get(2).getResult(5), 0.00005);
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
