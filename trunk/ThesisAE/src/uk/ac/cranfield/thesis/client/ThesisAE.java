package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.view.EquationPanel;
import uk.ac.cranfield.thesis.client.view.GraphPanel;
import uk.ac.cranfield.thesis.client.view.InputPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
    
    private VerticalPanel mainPanel = new VerticalPanel();
    private InputPanel inputPanel = new InputPanel();
    private EquationPanel equationPanel = new EquationPanel();
    private GraphPanel graphPanel = new GraphPanel(inputPanel);
    private RootPanel rootPanel = RootPanel.get();
    
    @Override
    public void onModuleLoad()
    {
        mainPanel.setStyleName("center");
        mainPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
        mainPanel.add(inputPanel);
        mainPanel.add(equationPanel);
        mainPanel.add(graphPanel);
        mainPanel.setSpacing(50);
        
        inputPanel.addButtonHandlers(new ComputeButtonHandler(), new ComputeButtonKeyPress());
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
            VisualizationUtils.loadVisualizationApi(graphPanel, LineChart.PACKAGE);
        }
        else
        {
            graphPanel.setVisible(false);
            equationPanel.setVisible(false);
        }
    }
    
    
    private class ComputeButtonHandler implements ClickHandler
    {
        
        @Override
        public void onClick(ClickEvent event)
        {
            computeAction();
        }
    }
    
    private class ComputeButtonKeyPress implements KeyPressHandler
    {
        
        @Override
        public void onKeyPress(KeyPressEvent event)
        {
            if (event.getCharCode() == KeyCodes.KEY_ENTER)
            {
                computeAction();
            }
        }
    }
    
}
