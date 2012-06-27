package uk.ac.cranfield.thesis.client.view.widget;

import java.util.List;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class LoadSystemWidget extends Dialog
{
    
    private final SystemPersistenceServiceAsync persistentService = SystemPersistenceService.Util.getInstance();
    private SystemGrid grid;
    
    public LoadSystemWidget()
    {
        setResizable(false);
        setClosable(true);
        setButtons("");
        
        grid = new SystemGrid();
        add(grid);
        
        hide();
    }
    
    @Override
    protected void createButtons()
    {
        super.createButtons();
        
        final MessageBox mb = new MessageBox();
        mb.setTitle("System of equations saved successfully");
        
        Button load = new Button("Load");
        load.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
            
            @Override
            public void componentSelected(ButtonEvent ce)
            {
                
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
        
        addButton(load);
        addButton(cancel);
        
    }
    
    public void showData()
    {
        persistentService.getAll(new AsyncCallback<List<SystemEntity>>()
        {
            
            @Override
            public void onSuccess(List<SystemEntity> result)
            {
                grid.setData(result);
                // add(new WidgetRenderingExample());
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
}
