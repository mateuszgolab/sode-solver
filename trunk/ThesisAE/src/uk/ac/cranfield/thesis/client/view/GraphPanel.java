package uk.ac.cranfield.thesis.client.view;

import java.util.List;

import uk.ac.cranfield.thesis.client.service.ParserService;
import uk.ac.cranfield.thesis.client.service.ParserServiceAsync;
import uk.ac.cranfield.thesis.client.service.solver.ModifiedMidpointService;
import uk.ac.cranfield.thesis.client.service.solver.ModifiedMidpointServiceAsync;
import uk.ac.cranfield.thesis.client.service.solver.RungeKuttaSolverService;
import uk.ac.cranfield.thesis.client.service.solver.RungeKuttaSolverServiceAsync;
import uk.ac.cranfield.thesis.client.view.widget.ProgressWidget;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class GraphPanel extends AbsolutePanel implements Runnable
{
    
    public final static String WHITE_COLOR = "#FFFFFF";
    public final static String CHART_URL = "/resources/chart/open-flash-chart.swf";
    private LineChart lineChart;
    private List<String> equations;
    private DataTable dataTable;
    private final ParserServiceAsync parserService = ParserService.Util.getInstance();
    private final RungeKuttaSolverServiceAsync rungeKuttaSolverService = RungeKuttaSolverService.Util.getInstance();
    private final ModifiedMidpointServiceAsync modifiedMidpointSolverService = ModifiedMidpointService.Util
            .getInstance();
    private int equationsCounter;
    private InputPanel inputPanel;
    private ProgressWidget progressWidget;
    private NumberFormat formatter = NumberFormat.getFormat("#.##");
    private AlertEvent alertEvent;
    
    public GraphPanel(InputPanel panel)
    {
        // setLayout(new FitLayout());
        this.inputPanel = panel;
        // setHeading("Solution");
        // setCaptionText("Solution");
        setStyleName("bigFontRoundedBorder");
        progressWidget = new ProgressWidget();
        alertEvent = new AlertEvent();
        
        
        // lineChart = new LineChart();
        // chart = new Chart(CHART_URL);
        // chart.setBorders(true);
        // charModel = new ChartModel();
        // charModel.setBackgroundColour(WHITE_COLOR);
        // // chart.setChartModel(getHorizontalBarChartModel());
        // add(chart);
        // chart.setVisible(false);
        
    }
    
    @Override
    public void run()
    {
        
        progressWidget.show();
        dataTable = DataTable.create();
        equationsCounter = 0;
        
        if (equations.size() == 1)
        {
            parserService.parseEquation(equations.get(0), new EquationParserCallback());
        }
        else
        {
            parserService.parseEquationsSystem(equations, new EquationsSystemParserCallback());
        }
    }
    
    private Options createOptions()
    {
        Options options = Options.create();
        options.setWidth((Window.getClientWidth() / 2));
        options.setHeight((Window.getClientHeight() / 2));
        // options.setTitle("Solution");
        return options;
    }
    
    public void setEquations(List<String> equations)
    {
        this.equations = equations;
    }
    
    private class EquationParserCallback implements AsyncCallback<Equation>
    {
        
        @Override
        public void onFailure(Throwable caught)
        {
            // errorDialog.setHTML(caught.getMessage());
            // errorDialog.center();
            // errorDialog.show();
            progressWidget.close();
            // Window.alert(caught.getMessage());
            MessageBox.alert("Error", caught.getMessage(), alertEvent);
        }
        
        @Override
        public void onSuccess(Equation result)
        {
            dataTable.addColumn(ColumnType.NUMBER, String.valueOf(result.getIndependentVariable()));
            dataTable.addColumn(ColumnType.NUMBER, result.getFunctionVariable() + "(" + result.getIndependentVariable()
                    + ")");
            
            if (SolverMethod.RUNGE_KUTTA.toString().compareTo(inputPanel.getSelectedMethod()) == 0)
            {
                rungeKuttaSolverService.solve(result, inputPanel.getStep(), inputPanel.getRangeStart(),
                        inputPanel.getRangeStop(), new EquationSolverCallback());
            }
            else if (SolverMethod.MODIFIED_MIDPOINT.toString().compareTo(inputPanel.getSelectedMethod()) == 0)
            {
                modifiedMidpointSolverService.solve(result, inputPanel.getStep(), inputPanel.getRangeStart(),
                        inputPanel.getRangeStop(), new EquationSolverCallback());
            }
        }
    }
    
    private class EquationsSystemParserCallback implements AsyncCallback<System>
    {
        
        @Override
        public void onFailure(Throwable caught)
        {
            // errorDialog.setHTML(caught.getMessage());
            // errorDialog.center();
            // errorDialog.show();
            progressWidget.close();
            // Window.alert(caught.getMessage());
            MessageBox.alert("Error", caught.getMessage(), alertEvent);
        }
        
        @Override
        public void onSuccess(System result)
        {
            List<Equation> list = result.getEquations();
            dataTable.addColumn(ColumnType.NUMBER, String.valueOf(result.getIndependentVariable()));
            
            for (Equation eq : list)
            {
                dataTable.addColumn(ColumnType.NUMBER, eq.getFunctionVariable() + "(" + eq.getIndependentVariable()
                        + ")");
            }
            
            if (SolverMethod.RUNGE_KUTTA.toString().compareTo(inputPanel.getSelectedMethod()) == 0)
            {
                rungeKuttaSolverService.solveSystem(result, inputPanel.getStep(), inputPanel.getRangeStart(),
                        inputPanel.getRangeStop(), new SystemSolverCallback());
            }
            else if (SolverMethod.MODIFIED_MIDPOINT.toString().compareTo(inputPanel.getSelectedMethod()) == 0)
            {
                modifiedMidpointSolverService.solveSystem(result, inputPanel.getStep(), inputPanel.getRangeStart(),
                        inputPanel.getRangeStop(), new SystemSolverCallback());
            }
            
            
        }
    }
    
    private class EquationSolverCallback implements AsyncCallback<Solution>
    {
        
        @Override
        public void onFailure(Throwable caught)
        {
            // errorDialog.setHTML(caught.getMessage());
            // errorDialog.center();
            // errorDialog.show();
            progressWidget.close();
            // Window.alert(caught.getMessage());
            MessageBox.alert("Error", caught.getMessage(), alertEvent);
        }
        
        @Override
        public void onSuccess(Solution result)
        {
            
            dataTable.addRows(result.size());
            
            // for (int i = 0; i < result.size(); i++)
            int k = 0;
            for (double i = result.getMin(); i < result.getMax() && k < result.size(); i += result.getStep())
            {
                // x
                dataTable.setValue(k, equationsCounter, Double.valueOf(formatter.format(i)));
                // y
                dataTable.setValue(k, equationsCounter + 1, result.getResult(k));
                
                k++;
            }
            
            equationsCounter++;
            
            if (equationsCounter == equations.size())
            {
                lineChart = new LineChart(dataTable, createOptions());
                clear();
                FormPanel chartPanel = new FormPanel();
                chartPanel.setHeading("Solution");
                chartPanel.add(lineChart);
                add(chartPanel);
            }
            
            progressWidget.close();
            
        }
    }
    
    private class SystemSolverCallback implements AsyncCallback<List<Solution>>
    {
        
        @Override
        public void onFailure(Throwable caught)
        {
            // errorDialog.setHTML(caught.getMessage());
            // errorDialog.center();
            // errorDialog.show();
            progressWidget.close();
            // Window.alert(caught.getMessage());
            MessageBox.alert("Error", caught.getMessage(), alertEvent);
        }
        
        @Override
        public void onSuccess(List<Solution> result)
        {
            // progressWidget.update(0.1);
            double start = result.get(0).getMin();
            double stop = result.get(0).getMax();
            double step = result.get(0).getStep();
            
            int size = result.get(0).size();
            dataTable.addRows(size);
            
            int k = 0;
            for (double i = start; i < stop && k < size; i += step, k++)
            {
                // x
                dataTable.setValue(k, equationsCounter, Double.valueOf(formatter.format(i)));
            }
            
            equationsCounter++;
            
            
            for (Solution sol : result)
            {
                // dataTable.addRows(sol.size());
                k = 0;
                for (double i = start; i < stop && k < sol.size(); i += step, k++)
                {
                    // y
                    dataTable.setValue(k, equationsCounter, sol.getResult(k));
                }
                
                equationsCounter++;
                
            }
            
            if (equationsCounter == equations.size() + 1)
            {
                lineChart = new LineChart(dataTable, createOptions());
                clear();
                FormPanel chartPanel = new FormPanel();
                chartPanel.setHeading("Solution");
                chartPanel.add(lineChart);
                add(chartPanel);
            }
            
            progressWidget.close();
        }
    }
    
    private class AlertEvent implements Listener<MessageBoxEvent>
    {
        
        @Override
        public void handleEvent(MessageBoxEvent be)
        {
            
        }
    }
    
}
