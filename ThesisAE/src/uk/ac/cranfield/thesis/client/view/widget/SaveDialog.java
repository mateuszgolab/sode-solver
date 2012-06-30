package uk.ac.cranfield.thesis.client.view.widget;

import uk.ac.cranfield.thesis.client.view.InputPanel;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;


public abstract class SaveDialog extends Dialog
{
    
    private InputPanel panel;
    private TextField<String> nameText;
    protected Button save;
    
    public SaveDialog(InputPanel panel)
    {
        this.panel = panel;
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(50);
        setLayout(layout);
        
        setButtons("");
        setIcon(IconHelper.createStyle("user"));
        setModal(true);
        setBodyBorder(true);
        setBodyStyle("padding: 8px;background: none");
        setResizable(false);
        
        
        nameText = new TextField<String>();
        nameText.setFieldLabel("Name");
        add(nameText);
        
        nameText.addKeyListener(new KeyListener()
        {
            
            @Override
            public void componentKeyPress(ComponentEvent event)
            {
                save.enable();
            }
        });
        
        setFocusWidget(nameText);
        
    }
    
    public void showDialog()
    {
        nameText.clear();
        save.disable();
        setVisible(true);
        
    }
    
    
    /**
     * @return the panel
     */
    public final InputPanel getPanel()
    {
        return panel;
    }
    
    
    /**
     * @return the nameText
     */
    public final TextField<String> getNameText()
    {
        return nameText;
    }
    
}
