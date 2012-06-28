package uk.ac.cranfield.thesis.client.view.system;

import java.util.List;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.client.view.InputPanel;
import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class SystemWidget extends Dialog
{
    
    private final SystemPersistenceServiceAsync persistentService = SystemPersistenceService.Util.getInstance();
    private SystemTable table;
    private InputPanel inputPanel;
    private Button load;
    private Button remove;
    
    public SystemWidget(InputPanel panel)
    {
        setResizable(false);
        setClosable(true);
        setButtons("");
        
        this.inputPanel = panel;
        table = new SystemTable(this);
        add(table);
        
        hide();
    }
    
    @Override
    protected void createButtons()
    {
        super.createButtons();
        
        final MessageBox mb = new MessageBox();
        mb.setTitle("System of equations saved successfully");
        
        // Load Button
        load = new Button("Load");
        load.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                inputPanel.loadEquations(table.getSelectedEquations());
                hide();
            }
        });
        
        // Cancel Button
        Button cancel = new Button("Cancel");
        cancel.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                hide();
            }
        });
        
        // Remove Button
        remove = new Button("Remove");
        remove.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                persistentService.remove(table.getSelectedName(), new AsyncCallback<String>()
                {
                    
                    @Override
                    public void onSuccess(String result)
                    {
                        showData();
                    }
                    
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        
                    }
                });
            }
        });
        
        // Remove all Button
        Button removeAll = new Button("Remove all");
        removeAll.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                persistentService.removeAll(new AsyncCallback<Void>()
                {
                    
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        showData();
                    }
                    
                    @Override
                    public void onSuccess(Void result)
                    {
                        showData();
                        
                    }
                });
            }
        });
        
        addButton(load);
        addButton(cancel);
        addButton(remove);
        addButton(removeAll);
    }
    
    public void showData()
    {
        persistentService.getAll(new AsyncCallback<List<SystemEntity>>()
        {
            
            @Override
            public void onSuccess(List<SystemEntity> result)
            {
                table.setData(result);
                setButtonsEnabled(false);
                show();
            }
            
            @Override
            public void onFailure(Throwable caught)
            {
                add(new Html(caught.getMessage()));
                show();
            }
        });
    }
    
    public void setButtonsEnabled(boolean enabled)
    {
        load.setEnabled(enabled);
        remove.setEnabled(enabled);
    }
}
