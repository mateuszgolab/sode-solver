package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.service.ParserService;
import uk.ac.cranfield.thesis.client.service.ParserServiceAsync;
import uk.ac.cranfield.thesis.shared.Equation;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class ParserTest extends GWTTestCase
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
        
        parserService.parseEquation("y' + x", new AsyncCallback<Equation>()
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
        
        parserService.parseEquation("y''' + x", new AsyncCallback<Equation>()
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
        
        parserService.parseEquation("y ' ' ' + x", new AsyncCallback<Equation>()
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
        
        parserService.parseEquation("y''' + y'' + y' + y + x = 5", new AsyncCallback<Equation>()
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
        
        parserService.parseEquation("y + x + z", new AsyncCallback<Equation>()
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
    
    // public void testCorrectEquationFunction()
    // {
    // final ParserServiceAsync parserService = ParserService.Util.getInstance();
    // delayTestFinish(500);
    // parserService.parseEquation("y''' = y '' + y*y' - y  + x + 4", new AsyncCallback<Equation>()
    // {
    //
    // @Override
    // public void onFailure(Throwable caught)
    // {
    // assertTrue(false);
    // finishTest();
    // }
    //
    // @Override
    // public void onSuccess(Equation result)
    // {
    // parserService.getFunctionVector(result, new AsyncCallback<List<String>>()
    // {
    //
    // @Override
    // public void onFailure(Throwable caught)
    // {
    // assertTrue(false);
    // finishTest();
    // }
    //
    // @Override
    // public void onSuccess(List<String> result)
    // {
    // assertEquals(result.size(), 3);
    // assertEquals("y1", result.get(0));
    // assertEquals("y2", result.get(1));
    // assertEquals("y2+y0*y1-y0+x+4", result.get(2));
    // finishTest();
    //
    // }
    // });
    //
    // }
    //
    // });
    //
    //
    // }
}
