package uk.ac.cranfield.thesis.client;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.solver.SolverServiceAsync;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


public abstract class SolverTestCase extends GWTTestCase
{
    
    protected SolverServiceAsync solverService;
    protected double accuracy;
    
    @Override
    public String getModuleName()
    {
        return "uk.ac.cranfield.thesis.ThesisAE";
    }
    
    @Override
    public abstract void gwtSetUp();
    
    public void test1OrderMathPowerEquation()
    {
        delayTestFinish(500);
        
        Equation equation = new Equation("y'=y+x^2");
        equation.setFunctionVariable('y');
        equation.setIndependentVariable('x');
        equation.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(0.0);
        
        equation.setInitValues(list);
        
        solverService.solve(equation, 0.1, 0.0, 1.0, new AsyncCallback<Solution>()
        {
            
            @Override
            public void onSuccess(Solution result)
            {
                assertEquals(11, result.size());
                assertEquals(-0.1 * 0.1 - 2 * 0.1 + 2 * Math.exp(0.1) - 2.0, result.getResult(1), accuracy);
                assertEquals(-0.5 * 0.5 - 2 * 0.5 + 2 * Math.exp(0.5) - 2.0, result.getResult(5), accuracy);
                assertEquals(-1.0 * 1.0 - 2 * 1.0 + 2 * Math.exp(1.0) - 2.0, result.getResult(10), accuracy);
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
    
    public void test1OrderMathSinCosEquation()
    {
        delayTestFinish(500);
        
        Equation equation = new Equation("y'=sin(x)+cos(x)");
        equation.setFunctionVariable('y');
        equation.setIndependentVariable('x');
        equation.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(0.0);
        
        equation.setInitValues(list);
        
        solverService.solve(equation, 0.1, 0.0, 1.0, new AsyncCallback<Solution>()
        {
            
            @Override
            public void onSuccess(Solution result)
            {
                assertEquals(11, result.size());
                assertEquals(Math.sin(0.1) - Math.cos(0.1) + 1.0, result.getResult(1), accuracy);
                assertEquals(Math.sin(0.5) - Math.cos(0.5) + 1.0, result.getResult(5), accuracy);
                assertEquals(Math.sin(1.0) - Math.cos(1.0) + 1.0, result.getResult(10), accuracy);
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
    
    public void test1OrderEquation()
    {
        delayTestFinish(500);
        
        Equation equation = new Equation("y'=-2*y+x+4");
        equation.setFunctionVariable('y');
        equation.setIndependentVariable('x');
        equation.setOrder(1);
        
        List<Double> list = new ArrayList<Double>();
        list.add(1.0);
        
        equation.setInitValues(list);
        
        solverService.solve(equation, 0.01, 0.0, 0.2, new AsyncCallback<Solution>()
        {
            
            @Override
            public void onSuccess(Solution result)
            {
                assertEquals(21, result.size());
                assertEquals(1.3472, result.getResult(20), accuracy);
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
    
    public void test1OrderSystem()
    {
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
        System system = new System(equations);
        
        solverService.solveSystem(system, 0.1, 0.0, 1.0, new AsyncCallback<List<Solution>>()
        {
            
            @Override
            public void onSuccess(List<Solution> result)
            {
                assertEquals(2, result.size());
                assertEquals(-0.5 + Math.exp(0.5) - 1.0, result.get(0).getResult(5), accuracy);
                assertEquals(Math.exp(0.5) * (0.5 - 1.0) + 1, result.get(1).getResult(5), accuracy);
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
    
    public void test1OrderSystem2()
    {
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
        System system = new System(equations);
        
        solverService.solveSystem(system, 0.1, 0.0, 1.0, new AsyncCallback<List<Solution>>()
        {
            
            @Override
            public void onSuccess(List<Solution> result)
            {
                assertEquals(2, result.size());
                assertEquals(-0.5 + Math.exp(0.5) - 1.0, result.get(0).getResult(5), accuracy);
                assertEquals(Math.exp(0.5) * (0.5 - 1.0) + 1, result.get(1).getResult(5), accuracy);
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
    
    public void test1OrderSystem3()
    {
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
        
        System system = new System(equations);
        
        solverService.solveSystem(system, 0.1, 0.0, 1.0, new AsyncCallback<List<Solution>>()
        {
            
            @Override
            public void onSuccess(List<Solution> result)
            {
                assertEquals(3, result.size());
                assertEquals(-0.5 + Math.exp(0.5) - 1.0, result.get(0).getResult(5), accuracy);
                assertEquals(Math.exp(0.5) * (0.5 - 1.0) + 1, result.get(1).getResult(5), accuracy);
                assertEquals(-0.5 + 5 * Math.exp(0.5) - 5.0, result.get(2).getResult(5), accuracy);
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
