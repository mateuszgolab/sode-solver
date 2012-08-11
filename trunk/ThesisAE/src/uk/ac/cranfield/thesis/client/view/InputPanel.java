package uk.ac.cranfield.thesis.client.view;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.ThesisAE;
import uk.ac.cranfield.thesis.client.view.system.SaveSystemDialog;
import uk.ac.cranfield.thesis.client.view.system.SystemWidget;
import uk.ac.cranfield.thesis.client.view.widget.ExampleDialog;
import uk.ac.cranfield.thesis.client.view.widget.ExpressionsDialog;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class InputPanel extends FormPanel
{
    
    private VerticalPanel panel;
    private Button computeButton;
    private Radio method1;
    private Radio method2;
    private Radio method3;
    private Label stepLabel;
    private Label minLabel;
    private Label maxLabel;
    private TextField<String> stepBox;
    private TextField<String> minBox;
    private TextField<String> maxBox;
    private FlexTable flexTextPanel;
    private ThesisAE parent;
    private String selectedMethod;
    
    public InputPanel(ThesisAE parent)
    {
        setHeading("Input");
        
        this.parent = parent;
        panel = new VerticalPanel();
        panel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
        panel.setSpacing(10);
        
        createEquationsInput();
        createParametersInput();
        createHelpPanel();
        createMethodsSelector();
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
        method1 = new Radio();
        method1.setBoxLabel(SolverMethod.RUNGE_KUTTA.toString());
        method1.setValue(true);
        
        method2 = new Radio();
        method2.setBoxLabel(SolverMethod.MODIFIED_MIDPOINT.toString());
        
        method3 = new Radio();
        method3.setBoxLabel(SolverMethod.PREDICTOR_CORRECTOR.toString());
        
        final RadioGroup group = new RadioGroup();
        group.add(method1);
        group.add(method2);
        group.add(method3);
        group.addListener(Events.Change, new Listener<FieldEvent>()
        {
            
            @Override
            public void handleEvent(FieldEvent fe)
            {
                selectedMethod = group.getValue().getBoxLabel();
            }
        });
        
        selectedMethod = SolverMethod.RUNGE_KUTTA.toString();
        
        HorizontalPanel hp2 = new HorizontalPanel();
        hp2.setSpacing(10);
        hp2.add(method1);
        hp2.add(method2);
        hp2.add(method3);
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
        stepBox = new TextField<String>();
        stepBox.setValue("0.1");
        
        HorizontalPanel hp3 = new HorizontalPanel();
        hp3.setSpacing(10);
        hp3.add(minLabel);
        hp3.add(minBox);
        hp3.add(maxLabel);
        hp3.add(maxBox);
        hp3.add(stepLabel);
        hp3.add(stepBox);
        panel.add(hp3);
    }
    
    private void createHelpPanel()
    {
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(10);
        
        final ExampleDialog exampleDialog = new ExampleDialog();
        Button examples = new Button("Show examples");
        examples.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                exampleDialog.setVisible(true);
            }
        });
        hp.add(examples);
        
        
        final ExpressionsDialog expressionDialog = new ExpressionsDialog();
        Button expressions = new Button("Available expressions");
        expressions.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                expressionDialog.setVisible(true);
            }
        });
        hp.add(expressions);
        
        panel.add(hp);
    }
    
    private void createPersistentPanel()
    {
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(10);
        
        final SaveSystemDialog saveSystemDialog = new SaveSystemDialog(this);
        Button saveSystem = new Button("Save equation system");
        saveSystem.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                saveSystemDialog.showDialog();
            }
        });
        hp.add(saveSystem);
        
        final SystemWidget systemWidget = new SystemWidget(InputPanel.this);
        Button systems = new Button("Show equation systems");
        systems.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                // SystemWidget systemWidget = new SystemWidget(InputPanel.this);
                systemWidget.showData();
            }
        });
        hp.add(systems);
        
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
    
    @SuppressWarnings("unchecked")
    private String getInput(int i)
    {
        return ((TextField<String>) flexTextPanel.getWidget(i, 0)).getValue();
    }
    
    public Double getRangeStart()
    {
        return Double.valueOf(minBox.getValue());
    }
    
    public Double getRangeStop()
    {
        return Double.valueOf(maxBox.getValue());
    }
    
    public Double getStep()
    {
        return Double.valueOf(stepBox.getValue());
    }
    
    public void loadEquations(List<String> equations, String min, String max, String step)
    {
        parent.resetView();
        flexTextPanel.removeAllRows();
        
        for (String eq : equations)
        {
            addEquationTextField(eq);
        }
        
        addLastEquationTextField();
        
        minBox.setValue(min);
        maxBox.setValue(max);
        stepBox.setValue(step);
    }
    
    public String getSelectedMethod()
    {
        return selectedMethod;
    }
}
