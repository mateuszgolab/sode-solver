package uk.ac.cranfield.thesis.client.view.widget;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.client.view.InputPanel;
import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class SaveSystemWidget extends Dialog
{
    
    private final SystemPersistenceServiceAsync persistentService = SystemPersistenceService.Util.getInstance();
    private InputPanel panel;
    private TextField<String> nameText;
    private Button save;
    
    public SaveSystemWidget(InputPanel panel)
    {
        this.panel = panel;
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(50);
        setLayout(layout);
        
        setButtons("");
        setIcon(IconHelper.createStyle("user"));
        setHeading("Save system of equations");
        setModal(true);
        setBodyBorder(true);
        setBodyStyle("padding: 8px;background: none");
        setResizable(false);
        
        
        nameText = new TextField<String>();
        nameText.setFieldLabel("Name");
        add(nameText);
        
        nameText.addKeyListener(new KeyListener()
        {
            
            @SuppressWarnings("unchecked")
            @Override
            public void componentKeyPress(ComponentEvent event)
            {
                // TODO : fix backspace
                if (event.getKeyCode() == KeyCodes.KEY_BACKSPACE && nameText.getValue().isEmpty())
                {
                    save.disable();
                }
                else
                {
                    save.enable();
                }
            }
        });
        
        setFocusWidget(nameText);
        
    }
    
    @Override
    protected void createButtons()
    {
        super.createButtons();
        Status status = new Status();
        status.setBusy("please wait...");
        status.hide();
        status.setAutoWidth(true);
        getButtonBar().add(status);
        
        getButtonBar().add(new FillToolItem());
        
        save = new Button("Save");
        save.disable();
        save.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                SystemEntity entity = new SystemEntity(nameText.getValue(), panel.getEquations());
                persistentService.persist(entity, new AsyncCallback<Void>()
                {
                    
                    @Override
                    public void onSuccess(Void result)
                    {
                        hide();
                    }
                    
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        add(new Html(caught.getMessage()));
                        show();
                    }
                });
            }
        });
        
        Button cancel = new Button("Cancel");
        cancel.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                hide();
            }
        });
        
        addButton(save);
        addButton(cancel);
        
        
    }
    
    public void showDialog()
    {
        nameText.clear();
        save.disable();
        show();
        
    }
}
