package uk.ac.cranfield.thesis.client;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.ParserService;
import uk.ac.cranfield.thesis.client.service.ParserServiceAsync;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.System;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class ParserTestCase extends GWTTestCase
{
    
    @Override
    public String getModuleName()
    {
        return "uk.ac.cranfield.thesis.ThesisAE";
    }
    
    public void testCorrectEquation1()
    {
        ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        parserService.parseEquation("y' = y + x, y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onSuccess(Equation result)
            {
                assertEquals(result.getFunctionVariable(), 'y');
                assertEquals(result.getIndependentVariable(), 'x');
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
    
    public void testCorrectEquation2()
    {
        ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        parserService.parseEquation("y''' = y + x, y=0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onSuccess(Equation result)
            {
                assertEquals(result.getFunctionVariable(), 'y');
                assertEquals(result.getIndependentVariable(), 'x');
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
    
    public void testCorrectEquation3()
    {
        ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        parserService.parseEquation("y ' ' ' = y+x, y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onSuccess(Equation result)
            {
                assertEquals(result.getFunctionVariable(), 'y');
                assertEquals(result.getIndependentVariable(), 'x');
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
    
    public void testCorrectEquation4()
    {
        ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        parserService.parseEquation("y''' = y'' + y' + y + x = 5, y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onSuccess(Equation result)
            {
                assertEquals(result.getFunctionVariable(), 'y');
                assertEquals(result.getIndependentVariable(), 'x');
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
    
    public void testNoODE()
    {
        ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        parserService.parseEquation("y + x", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onSuccess(Equation result)
            {
                assertTrue(false);
                finishTest();
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(caught instanceof IncorrectODEEquationException);
                finishTest();
            }
        });
        
    }
    
    public void testToManyVariables()
    {
        ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        parserService.parseEquation("y' = y + x + z, y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onSuccess(Equation result)
            {
                assertTrue(false);
                finishTest();
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(caught instanceof IncorrectODEEquationException);
                finishTest();
            }
        });
        
    }
    
    public void testCaseInsensitivity()
    {
        ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        parserService.parseEquation("y'' = y + x + 2*X + 3/Y', y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onSuccess(Equation result)
            {
                assertEquals('y', result.getFunctionVariable());
                assertEquals('x', result.getIndependentVariable());
                assertEquals(2, result.getOrder());
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
    
    public void testCorrectEquationFunction()
    {
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        parserService.parseEquation("y''' = y '' + y*y' - y  + x + 4, y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
            }
            
            @Override
            public void onSuccess(Equation result)
            {
                assertEquals('y', result.getFunctionVariable());
                assertEquals('x', result.getIndependentVariable());
                assertEquals(3, result.getOrder());
                finishTest();
            }
            
        });
        
        
    }
    
    public void testNoIndependentVariable()
    {
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        parserService.parseEquation("y''' = y '' + y*y' - y  + 4, y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
            }
            
            @Override
            public void onSuccess(Equation result)
            {
                assertEquals('y', result.getFunctionVariable());
                assertEquals(0, result.getIndependentVariable());
                assertEquals(3, result.getOrder());
                finishTest();
            }
            
        });
        
        
    }
    
    public void testToManyIndependentVariables()
    {
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        parserService.parseEquation("y''' = y '' + y*y' - y  + 4 + x + u, y = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(caught instanceof IncorrectODEEquationException);
                finishTest();
            }
            
            @Override
            public void onSuccess(Equation result)
            {
                assertTrue(false);
                finishTest();
            }
            
        });
        
    }
    
    public void testMathExpressions()
    {
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        parserService.parseEquation("y'=sin(x)+cos(x)+exp(x)+log(x)+tan(x)+sqrt(x), y = 0",
                new AsyncCallback<Equation>()
                {
                    
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        assertTrue(false);
                        finishTest();
                    }
                    
                    @Override
                    public void onSuccess(Equation result)
                    {
                        assertEquals(result.getIndependentVariable(), 'x');
                        finishTest();
                    }
                    
                });
        
    }
    
    public void testWrongInitVariable()
    {
        
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        parserService.parseEquation("  y' = y +3, u =4", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(caught instanceof IncorrectODEEquationException);
                finishTest();
            }
            
            @Override
            public void onSuccess(Equation result)
            {
                assertTrue(false);
                finishTest();
            }
            
        });
        
    }
    
    public void testNoInitVariable()
    {
        
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        parserService.parseEquation("  y' = x + 4", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(caught instanceof IncorrectODEEquationException);
                finishTest();
            }
            
            @Override
            public void onSuccess(Equation result)
            {
                assertTrue(false);
                finishTest();
            }
            
        });
        
    }
    
    public void testNoODEWithInitVariable()
    {
        
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        parserService.parseEquation(" y = tre , y  = 0", new AsyncCallback<Equation>()
        {
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(caught instanceof IncorrectODEEquationException);
                finishTest();
            }
            
            @Override
            public void onSuccess(Equation result)
            {
                assertTrue(false);
                finishTest();
            }
            
        });
        
    }
    
    public void testSystemOfODE()
    {
        
        final ParserServiceAsync parserService = ParserService.Util.getInstance();
        delayTestFinish(500);
        
        List<String> systemInput = new ArrayList<String>(2);
        systemInput.add("y'= y + x, y = 0");
        systemInput.add("z'= z + y + x, z = 0");
        
        parserService.parseEquationsSystem(systemInput, new AsyncCallback<System>()
        {
            
            @Override
            public void onFailure(Throwable caught)
            {
                assertTrue(false);
                finishTest();
                
            }
            
            @Override
            public void onSuccess(System result)
            {
                assertEquals('x', result.getIndependentVariable());
                finishTest();
                
            }
        });
        
    }
}
