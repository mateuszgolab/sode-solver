package uk.ac.cranfield.thesis.client.view.system;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.client.view.InputPanel;
import uk.ac.cranfield.thesis.client.view.widget.SaveDialog;
import uk.ac.cranfield.thesis.shared.model.entity.SystemEntity;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class SaveSystemDialog extends SaveDialog
{
    
    private final SystemPersistenceServiceAsync persistentService = SystemPersistenceService.Util.getInstance();
    
    public SaveSystemDialog(InputPanel panel)
    {
        super(panel);
        setHeading("Save system of equations");
    }
    
    @Override
    protected void createButtons()
    {
        super.createButtons();
        
        final MessageBox mb = new MessageBox();
        mb.setTitle("System of equations saved successfully");
        
        save = new Button("Save");
        save.disable();
        save.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                if (getNameText().getValue() != null && !getNameText().getValue().isEmpty())
                {
                    SystemEntity entity = new SystemEntity(getNameText().getValue(), getPanel().getEquations(),
                            getPanel().getRangeStart(), getPanel().getRangeStop(), getPanel().getStep());
                    persistentService.persist(entity, new AsyncCallback<Void>()
                    {
                        
                        @Override
                        public void onSuccess(Void result)
                        {
                            setVisible(false);
                            mb.show();
                        }
                        
                        @Override
                        public void onFailure(Throwable caught)
                        {
                            add(new Html(caught.getMessage()));
                            setVisible(true);
                        }
                    });
                }
                else
                {
                    save.disable();
                }
            }
        });
        
        Button cancel = new Button("Cancel");
        cancel.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                setVisible(false);
            }
        });
        
        setFocusWidget(save);
        
        addButton(save);
        addButton(cancel);
        
    }
    
}
