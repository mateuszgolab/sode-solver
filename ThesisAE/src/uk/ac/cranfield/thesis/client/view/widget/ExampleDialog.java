package uk.ac.cranfield.thesis.client.view.widget;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;


public class ExampleDialog extends Dialog
{
    
    
    public ExampleDialog()
    {
        
        ContentPanel mainPanel = new ContentPanel();
        mainPanel.setHeading("Input examples");
        
        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(10);
        
        TextField<String> equation = new TextField<String>();
        equation.setReadOnly(true);
        equation.setValue("y' = y + x + 1, y = 0");
        
        TextField<String> system1 = new TextField<String>();
        system1.setReadOnly(true);
        system1.setValue("y' = y + x + 1, y = 0");
        
        TextField<String> system2 = new TextField<String>();
        system2.setReadOnly(true);
        system2.setValue("u' = u + x + y + 4, u = 1.0");
        
        
        panel.add(new Label("Equation"));
        panel.add(equation);
        panel.add(new Label("System of equations"));
        panel.add(system1);
        panel.add(system2);
        
        
        mainPanel.add(panel);
        add(mainPanel);
        
        
    }
    
    @Override
    protected void createButtons()
    {
        
        Button close = new Button("Close");
        close.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                setVisible(false);
            }
        });
        
        setFocusWidget(close);
        
        addButton(close);
        
    }
}
