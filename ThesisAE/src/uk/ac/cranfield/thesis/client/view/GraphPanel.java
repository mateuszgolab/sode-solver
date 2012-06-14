package uk.ac.cranfield.thesis.client.view;

import java.util.List;

import uk.ac.cranfield.thesis.client.service.ParserService;
import uk.ac.cranfield.thesis.client.service.ParserServiceAsync;
import uk.ac.cranfield.thesis.client.service.RungeKuttaSolverService;
import uk.ac.cranfield.thesis.client.service.RungeKuttaSolverServiceAsync;
import uk.ac.cranfield.thesis.shared.Equation;
import uk.ac.cranfield.thesis.shared.Solution;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class GraphPanel extends CaptionPanel implements Runnable
{
    
    private LineChart chart;
    private List<String> equations;
    private DataTable dataTable;
    private final ParserServiceAsync parserService = ParserService.Util.getInstance();
    private RungeKuttaSolverServiceAsync rungeKuttaSolverService = RungeKuttaSolverService.Util.getInstance();
    private int equationsCounter;
    private DialogBox errorDialog;
    private InputPanel inputPanel;
    
    public GraphPanel(InputPanel panel)
    {
        this.inputPanel = panel;
        setCaptionText("Solution");
        setStyleName("bigFontRoundedBorder");
        errorDialog = new DialogBox();
        errorDialog.setAnimationEnabled(true);
        Button closeButton = new Button("Close");
        closeButton.addClickHandler(new ClickHandler()
        {
            
            @Override
            public void onClick(ClickEvent event)
            {
                errorDialog.hide();
            }
        });
        errorDialog.add(closeButton);
        
        // chart = new LineChart(DataTable.create(), createOptions());
        // add(chart);
    }
    
    @Override
    public void run()
    {
        createTable();
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
    
    public void createTable()
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
                // dataTable.setValue(i, solution.getxAxis(), i);
                // y
                // dataTable.setValue(i, solution.getyAxis(), solution.getResult(i));
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
            errorDialog.setHTML(caught.getMessage());
            errorDialog.center();
            errorDialog.show();
        }
        
        @Override
        public void onSuccess(Equation result)
        {
            dataTable.addColumn(ColumnType.NUMBER, String.valueOf(result.getIndependentVariable()));
            dataTable.addColumn(ColumnType.NUMBER, result.getFunctionVariable() + "(" + result.getIndependentVariable()
                    + ")");
            
            rungeKuttaSolverService.solve(result, inputPanel.getStep(), inputPanel.getRangeStart(),
                    inputPanel.getRangeStop(), new RungeKuttaSolverCallback());
            
        }
    }
    
    private class RungeKuttaSolverCallback implements AsyncCallback<Solution>
    {
        
        @Override
        public void onFailure(Throwable caught)
        {
            errorDialog.setHTML(caught.getMessage());
            errorDialog.center();
            errorDialog.show();
            
        }
        
        @Override
        public void onSuccess(Solution result)
        {
            dataTable.addRows(result.size());
            
            // for (int i = 0; i < result.size(); i++)
            int k = 0;
            for (double i = result.getStart(); i < result.getStop() && k < result.size(); i += result.getStep())
            {
                // x
                dataTable.setValue(k, equationsCounter, i);
                // y
                dataTable.setValue(k, equationsCounter + 1, result.getResult(k));
                
                
                k++;
            }
            
            equationsCounter++;
            
            if (equationsCounter == equations.size())
            {
                chart = new LineChart(dataTable, createOptions());
                clear();
                add(chart);
            }
            
        }
    }
    
}
