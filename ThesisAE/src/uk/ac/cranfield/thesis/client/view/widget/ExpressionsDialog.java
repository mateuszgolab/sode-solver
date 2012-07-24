package uk.ac.cranfield.thesis.client.view.widget;

import java.util.Arrays;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;


public class ExpressionsDialog extends Dialog
{
    
    public static final String[] mathFunctions = {"acos", "asin", "atan", "cos", "cosh", "log", "sin", "sinh", "sqrt",
            "tan", "tanh", "exp (e^x)", "x^y (x to the y power)"};
    
    public ExpressionsDialog()
    {
        
        
        ContentPanel panel = new ContentPanel();
        panel.setHeading("Mathematical expressions");
        
        TextCell textCell = new TextCell();
        
        CellList<String> list = new CellList<String>(textCell);
        list.setRowData(Arrays.asList(mathFunctions));
        panel.add(list);
        
        add(panel);
        
        
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
