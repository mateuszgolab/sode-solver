package uk.ac.cranfield.thesis.client.view;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import uk.ac.cranfield.thesis.client.EquationParser;
import uk.ac.cranfield.thesis.client.service.EquationsService;
import uk.ac.cranfield.thesis.client.service.EquationsServiceAsync;
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
    private List<Equation> equations;
    private DataTable dataTable;
    private final EquationsServiceAsync parserService = (EquationsServiceAsync) GWT.create(EquationsService.class);
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
    
    public void setEquations(List<Equation> equations)
    {
        this.equations = equations;
    }
    
    private void createTable() throws ScriptException
    {
        dataTable = DataTable.create();
        equationsCounter = 0;
        
        
        for (Equation equation : equations)
        {
            EquationParser parser = new EquationParser(equation.getEquation());
            equation.setIndependentVariable(parser.getIndependentVariable());
            equation.setDependentVariable(parser.getDependentVariable());
            
            dataTable.addColumn(ColumnType.NUMBER, equation.getIndependentVariable());
            dataTable.addColumn(ColumnType.NUMBER,
                    equation.getDependentVariable() + "(" + equation.getIndependentVariable() + ")");
            dataTable.addRows(100);
            
            parserService.setEquation(equation.getEquation(), new AsyncCallback<Void>()
            {
                
                @Override
                public void onFailure(Throwable caught)
                {
                    System.out.println(caught.getMessage());
                    
                }
                
                @Override
                public void onSuccess(Void result)
                {
                    // TODO Auto-generated method stub
                    
                }
            });
            
            List<Double> points = new ArrayList<Double>();
            
            for (double d = 0; d < 100; d++)
            {
                points.add(d);
            }
            
            
            parserService.getValues(equation.getIndependentVariable(), points, equationsCounter,
                    new AsyncCallback<Solution>()
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
                    });
        }
    }
}
