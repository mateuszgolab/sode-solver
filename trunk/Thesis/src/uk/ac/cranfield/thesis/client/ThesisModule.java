package uk.ac.cranfield.thesis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ThesisModule implements EntryPoint
{
    
    private VerticalPanel mainPanel = new VerticalPanel();
    private InputPanel inputPanel = new InputPanel();
    private EquationPanel equationPanel = new EquationPanel();
    // private GraphPanel graphPanel = new GraphPanel();
    private RootPanel rootPanel = RootPanel.get();
    
    @Override
    public void onModuleLoad()
    {
        
        mainPanel.setStyleName("center");
        mainPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
        mainPanel.add(inputPanel);
        mainPanel.add(equationPanel);
        // mainPanel.add(graphPanel);
        mainPanel.setSpacing(50);
        
        inputPanel.addButtonHandlers(new ComputeButtonHandler(), new ComputeButtonKeyPress());
        equationPanel.setVisible(false);
        // graphPanel.setVisible(false);
        
        rootPanel.setStyleName("center");
        rootPanel.add(mainPanel);
        
        // VisualizationUtils.loadVisualizationApi(graphPanel, LineChart.PACKAGE);
    }
    
    private void computeAction()
    {
        equationPanel.clearPanel();
        
        if (inputPanel.isEquationEntered())
        {
            equationPanel.setEquation(inputPanel.getEquations());
            // graphPanel.setVisible(true);
            equationPanel.setVisible(true);
        }
        else
        {
            // graphPanel.setVisible(false);
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
