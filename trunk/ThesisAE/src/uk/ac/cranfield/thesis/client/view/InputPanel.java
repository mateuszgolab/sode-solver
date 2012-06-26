package uk.ac.cranfield.thesis.client.view;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.client.view.widget.LoadSystemWidget;
import uk.ac.cranfield.thesis.client.view.widget.SaveSystemWidget;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;


public class InputPanel extends FormPanel
{
    
    private final SystemPersistenceServiceAsync persistenceService = SystemPersistenceService.Util.getInstance();
    private VerticalPanel panel;
    private Button computeButton;
    private Button saveSolution;
    private RadioButton method1;
    private RadioButton method2;
    private RadioButton method3;
    private RadioButton method4;
    private Label stepLabel;
    private Label minLabel;
    private Label maxLabel;
    private TextField<String> step;
    private TextField<String> minBox;
    private TextField<String> maxBox;
    private FlexTable flexTextPanel;
    
    
    public InputPanel()
    {
        setHeading("Input");
        
        panel = new VerticalPanel();
        panel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
        panel.setSpacing(10);
        
        createEquationsInput();
        createMethodsSelector();
        createParametersInput();
        createPersistentPanel();
        createComputePanel();
        
        add(panel);
        
    }
    
    private void createEquationsInput()
    {
        flexTextPanel = new FlexTable();
        addEquationTextField("");
        addLastEquationTextField();
        panel.add(flexTextPanel);
    }
    
    private void addEquationTextField(String val)
    {
        TextField<String> input = new TextField<String>();
        input.setWidth("600px");
        input.setValue(val);
        flexTextPanel.setWidget(flexTextPanel.getRowCount(), 0, input);
    }
    
    private void addLastEquationTextField()
    {
        TextField<String> input2 = new TextField<String>();
        input2.setWidth("600px");
        input2.setValue("...");
        input2.addKeyListener(new InputListener());
        flexTextPanel.setWidget(flexTextPanel.getRowCount(), 0, input2);
    }
    
    private void createMethodsSelector()
    {
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
        hp2.setSpacing(10);
        hp2.add(method1);
        hp2.add(method2);
        hp2.add(method3);
        hp2.add(method4);
        panel.add(hp2);
    }
    
    private void createParametersInput()
    {
        minLabel = new Label("Min : ");
        minBox = new TextField<String>();
        minBox.setValue("0.0");
        maxLabel = new Label("Max : ");
        maxBox = new TextField<String>();
        maxBox.setValue("10.0");
        stepLabel = new Label("Step : ");
        step = new TextField<String>();
        step.setValue("0.1");
        
        HorizontalPanel hp3 = new HorizontalPanel();
        hp3.setSpacing(10);
        hp3.add(minLabel);
        hp3.add(minBox);
        hp3.add(maxLabel);
        hp3.add(maxBox);
        hp3.add(stepLabel);
        hp3.add(step);
        panel.add(hp3);
    }
    
    private void createPersistentPanel()
    {
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(10);
        
        final SaveSystemWidget saveSystemWidget = new SaveSystemWidget(this);
        Button saveSystem = new Button("Save system");
        saveSystem.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                saveSystemWidget.showDialog();
            }
        });
        
        Button saveParameters = new Button("Save parameters");
        
        saveSolution = new Button("Save solution");
        saveSolution.setEnabled(false);
        
        hp.add(saveSystem);
        hp.add(saveParameters);
        hp.add(saveSolution);
        
        final LoadSystemWidget loadSystemWidget = new LoadSystemWidget();
        Button loadSystem = new Button("Load system");
        loadSystem.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                loadSystemWidget.showData();
                
                // persistenceService.get("testName2", new AsyncCallback<SystemEntity>()
                // {
                //
                // @Override
                // public void onFailure(Throwable caught)
                // {
                // Window.alert(caught.getMessage());
                // }
                //
                // @SuppressWarnings("unchecked")
                // @Override
                // public void onSuccess(SystemEntity result)
                // {
                //
                // for (int i = 2; i < flexTextPanel.getRowCount(); i++)
                // {
                // flexTextPanel.removeRow(i);
                // }
                // ((TextField<String>) flexTextPanel.getWidget(0, 0)).setValue("");
                // ((TextField<String>) flexTextPanel.getWidget(1, 0)).setValue("...");
                //
                // if (result == null)
                // return;
                //
                // List<String> equations = result.getEquations();
                // int i = 0;
                // for (String eq : equations)
                // {
                // if (eq != null && !eq.isEmpty())
                // {
                // if (i < 2)
                // {
                // ((TextField<String>) flexTextPanel.getWidget(i, 0)).setValue(eq);
                // }
                // else
                // {
                // addEquationTextField(eq);
                // }
                // i++;
                // }
                // }
                //
                // if (i > 1)
                // {
                // addLastEquationTextField();
                // }
                // }
                // });
            }
        });
        
        Button loadParameters = new Button("Load parameters");
        Button loadSolution = new Button("Load solution");
        
        hp.add(loadSystem);
        hp.add(loadParameters);
        hp.add(loadSolution);
        panel.add(hp);
    }
    
    private void createComputePanel()
    {
        HorizontalPanel hp5 = new HorizontalPanel();
        computeButton = new Button("Compute");
        hp5.add(computeButton);
        panel.add(hp5);
    }
    
    public void addButtonHandlers(SelectionListener<ButtonEvent> handler)
    {
        computeButton.addSelectionListener(handler);
        // computeButton.add addKeyPressHandler(keyHandler);
    }
    
    public void removeButtonHandler()
    {
    }
    
    public List<String> getEquations()
    {
        List<String> inputs = new ArrayList<String>();
        for (int i = 0; i < flexTextPanel.getRowCount() - 1; i++)
        {
            if (getInput(i) == null || getInput(i).isEmpty())
            {
                flexTextPanel.removeRow(i);
                i--;
                continue;
            }
            inputs.add(getInput(i));
        }
        
        return inputs;
    }
    
    @SuppressWarnings("unchecked")
    public boolean isEquationEntered()
    {
        String val = ((TextField<String>) flexTextPanel.getWidget(0, 0)).getValue();
        return (val != null) && (!val.isEmpty());
    }
    
    private class InputListener extends KeyListener
    {
        
        @Override
        public void componentKeyPress(ComponentEvent event)
        {
            if (event.getSource().equals(flexTextPanel.getWidget(flexTextPanel.getRowCount() - 1, 0)))
            {
                TextField<String> input = new TextField<String>();
                input.setWidth("600px");
                input.setValue("...");
                input.addKeyListener(this);
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
    
    @SuppressWarnings("unchecked")
    private String getInput(int i)
    {
        return ((TextField<String>) flexTextPanel.getWidget(i, 0)).getValue();
    }
    
    public double getRangeStart()
    {
        return Double.valueOf(minBox.getValue());
    }
    
    public double getRangeStop()
    {
        return Double.valueOf(maxBox.getValue());
    }
    
    public double getStep()
    {
        return Double.valueOf(step.getValue());
    }
    
    public void enableSolutionSaving()
    {
        saveSolution.setEnabled(true);
    }
    
}
