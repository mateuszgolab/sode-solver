package uk.ac.cranfield.thesis.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class InputPanel extends CaptionPanel
{
    
    private VerticalPanel panel;
    private FlexTable flexTextPanel;
    private Button computeButton;
    private RadioButton method1;
    private RadioButton method2;
    private RadioButton method3;
    private RadioButton method4;
    
    public InputPanel()
    {
        // setCaptionText("Input");
        setStyleName("roundedBorder");
        panel = new VerticalPanel();
        panel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
        panel.setSpacing(10);
        // setWidth("640px");
        
        
        flexTextPanel = new FlexTable();
        panel.add(flexTextPanel);
        
        TextBox input = new TextBox();
        input.setWidth("600px");
        // input.setWidth(new Integer(Window.getClientWidth()).toString() + "px");
        
        TextBox input2 = new TextBox();
        input2.setWidth("600px");
        // input2.setWidth(new Integer(Window.getClientWidth()).toString() + "px");
        input2.setValue("...");
        input2.addKeyPressHandler(new InputListener());
        
        flexTextPanel.setWidget(flexTextPanel.getRowCount(), 0, input);
        flexTextPanel.setWidget(flexTextPanel.getRowCount(), 0, input2);
        
        MethodSelector methodSelector = new MethodSelector();
        
        method1 = new RadioButton("method1", "Runge-Kutta");
        method1.setValue(true);
        method1.addClickHandler(methodSelector);
        
        method2 = new RadioButton("method2", "method 2");
        method2.addClickHandler(methodSelector);
        
        method3 = new RadioButton("method3", "method 3");
        method3.addClickHandler(methodSelector);
        
        method4 = new RadioButton("method4", "method 4");
        method4.addClickHandler(methodSelector);
        
        HorizontalPanel hp2 = new HorizontalPanel();
        hp2.add(method1);
        hp2.add(method2);
        hp2.add(method3);
        hp2.add(method4);
        panel.add(hp2);
        
        computeButton = new Button("Compute");
        panel.add(computeButton);
        
        add(panel);
        
    }
    
    public void addButtonHandlers(ClickHandler handler, KeyPressHandler keyHandler)
    {
        computeButton.addClickHandler(handler);
        computeButton.addKeyPressHandler(keyHandler);
    }
    
    public void removeButtonHandler()
    {
    }
    
    public List<String> getEquations()
    {
        List<String> inputs = new ArrayList<String>();
        for (int i = 0; i < flexTextPanel.getRowCount() - 1; i++)
        {
            if (getInput(i).isEmpty())
            {
                flexTextPanel.removeRow(i);
                i--;
                continue;
            }
            inputs.add(getInput(i));
        }
        
        return inputs;
    }
    
    public boolean isEquationEntered()
    {
        return !(((TextBox) flexTextPanel.getWidget(0, 0)).getText().isEmpty());
    }
    
    private class InputListener implements KeyPressHandler
    {
        
        @Override
        public void onKeyPress(KeyPressEvent event)
        {
            if (event.getSource().equals(flexTextPanel.getWidget(flexTextPanel.getRowCount() - 1, 0)))
            {
                TextBox input = new TextBox();
                input.setWidth("600px");
                input.setValue("...");
                input.addKeyPressHandler(this);
                flexTextPanel.setWidget(flexTextPanel.getRowCount(), 0, input);
            }
        }
    }
    
    private class MethodSelector implements ClickHandler
    {
        
        @Override
        public void onClick(ClickEvent event)
        {
            method1.setValue(false);
            method2.setValue(false);
            method3.setValue(false);
            method4.setValue(false);
            
            ((RadioButton) event.getSource()).setValue(true);
        }
    }
    
    private String getInput(int i)
    {
        return ((TextBox) flexTextPanel.getWidget(i, 0)).getText();
    }
}
