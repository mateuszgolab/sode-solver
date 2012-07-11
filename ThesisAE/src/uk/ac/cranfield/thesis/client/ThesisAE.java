package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.view.EquationPanel;
import uk.ac.cranfield.thesis.client.view.GraphPanel;
import uk.ac.cranfield.thesis.client.view.InputPanel;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ThesisAE implements EntryPoint
{
    
    private VerticalPanel mainPanel;
    private InputPanel inputPanel;
    private EquationPanel equationPanel;
    private GraphPanel graphPanel;
    private RootPanel rootPanel;
    
    public ThesisAE()
    {
        mainPanel = new VerticalPanel();
        inputPanel = new InputPanel(this);
        equationPanel = new EquationPanel();
        graphPanel = new GraphPanel(inputPanel);
        rootPanel = RootPanel.get();
    }
    
    @Override
    public void onModuleLoad()
    {
        // DOM.setStyleAttribute(inputPanel.getContentWidget().getElement(), "margin", "50px 50px 50px 50px");
        mainPanel.setStyleName("center");
        mainPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
        mainPanel.add(inputPanel);
        mainPanel.add(equationPanel);
        mainPanel.add(graphPanel);
        mainPanel.setSpacing(50);
        
        // inputPanel.addButtonHandlers(new ComputeButtonHandler(), new ComputeButtonKeyPress());
        inputPanel.addButtonHandlers(new ComputeButtonHandler());
        equationPanel.setVisible(false);
        graphPanel.setVisible(false);
        
        rootPanel.setStyleName("center");
        rootPanel.add(mainPanel);
    }
    
    private void computeAction()
    {
        equationPanel.clearPanel();
        
        if (inputPanel.isEquationEntered())
        {
            equationPanel.setEquations(inputPanel.getEquations());
            graphPanel.setEquations(inputPanel.getEquations());
            graphPanel.setVisible(true);
            equationPanel.setVisible(true);
            
            // launch solver and graph viewer
            VisualizationUtils.loadVisualizationApi(graphPanel, LineChart.PACKAGE);
        }
        else
        {
            graphPanel.setVisible(false);
            equationPanel.setVisible(false);
        }
    }
    
    public void resetView()
    {
        equationPanel.setVisible(false);
        graphPanel.setVisible(false);
    }
    
    
    private class ComputeButtonHandler extends SelectionListener<ButtonEvent>
    {
        
        @Override
        public void componentSelected(ButtonEvent ce)
        {
            computeAction();
            
        }
        
    }
    
    // private class ComputeButtonKeyPress implements KeyPressHandler
    // {
    //
    // @Override
    // public void onKeyPress(KeyPressEvent event)
    // {
    // if (event.getCharCode() == KeyCodes.KEY_ENTER)
    // {
    // computeAction();
    // }
    // }
    // }
    
}
