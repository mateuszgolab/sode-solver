package uk.ac.cranfield.thesis.client.view;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import uk.ac.cranfield.thesis.client.service.EquationsService;
import uk.ac.cranfield.thesis.client.service.EquationsServiceAsync;
import uk.ac.cranfield.thesis.client.service.ParserService;
import uk.ac.cranfield.thesis.client.service.ParserServiceAsync;
import uk.ac.cranfield.thesis.shared.Equation;
import uk.ac.cranfield.thesis.shared.Solution;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;


public class GraphPanel extends CaptionPanel implements Runnable
{
    
    private LineChart chart;
    private List<String> equations;
    private DataTable dataTable;
    private final EquationsServiceAsync equationService = (EquationsServiceAsync) GWT.create(EquationsService.class);
    private final ParserServiceAsync parserService = ParserService.Util.getInstance();
    private int equationsCounter;
    
    public GraphPanel()
    {
        setCaptionText("Solution");
        setStyleName("bigFontRoundedBorder");
        
        // chart = new LineChart(DataTable.create(), createOptions());
        // add(chart);
    }
    
    @Override
    public void run()
    {
        
        try
        {
            createTable();
            // chart = new LineChart(dataTable, createOptions());
            // add(chart);
            
        }
        catch (ScriptException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
    
    private Options createOptions()
    {
        Options options = Options.create();
        options.setWidth((Window.WINDOW_WIDTH / 2));
        options.setHeight((Window.WINDOW_HEIGHT / 2));
        // options.setTitle("Solution");
        return options;
    }
    
    public void setEquations(List<String> equations)
    {
        this.equations = equations;
    }
    
    private void createTable() throws ScriptException
    {
        dataTable = DataTable.create();
        equationsCounter = 0;
        
        
        for (String equation : equations)
            parserService.parseEquation(equation, new ParserCallback());
        
    }
    
    private class EquationEvaluatorCallback implements AsyncCallback<Solution>
    {
        
        @Override
        public void onFailure(Throwable caught)
        {
            System.out.println(caught.getMessage());
        }
        
        @Override
        public void onSuccess(Solution solution)
        {
            for (int i = 0; i < solution.size(); i++)
            {
                // x
                dataTable.setValue(i, solution.getxAxis(), i);
                // y
                dataTable.setValue(i, solution.getyAxis(), solution.getResult(i));
            }
            
            equationsCounter++;
            
            if (equationsCounter == equations.size())
            {
                chart = new LineChart(dataTable, createOptions());
                clear();
                add(chart);
            }
        }
    };
    
    
    private class ParserCallback implements AsyncCallback<Equation>
    {
        
        
        @Override
        public void onFailure(Throwable caught)
        {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void onSuccess(Equation result)
        {
            dataTable.addColumn(ColumnType.NUMBER, String.valueOf(result.getIndependentVariable()));
            dataTable.addColumn(ColumnType.NUMBER, result.getFunctionVariable() + "(" + result.getIndependentVariable()
                    + ")");
            dataTable.addRows(100);
            
            List<Double> points = new ArrayList<Double>();
            
            for (double d = 0; d < 100; d++)
            {
                points.add(d);
            }
            
            equationService.evaluate(result, points, equationsCounter, new EquationEvaluatorCallback());
            
        }
    }
}
