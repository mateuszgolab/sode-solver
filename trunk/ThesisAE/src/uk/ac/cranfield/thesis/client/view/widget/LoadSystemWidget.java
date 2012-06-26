package uk.ac.cranfield.thesis.client.view.widget;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceService;
import uk.ac.cranfield.thesis.client.service.persistence.SystemPersistenceServiceAsync;
import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class LoadSystemWidget extends Dialog
{
    
    private final SystemPersistenceServiceAsync persistentService = SystemPersistenceService.Util.getInstance();
    private ContentPanel contentPanel;
    private ColumnModel columnModel;
    
    public LoadSystemWidget()
    {
        setResizable(false);
        setClosable(true);
        
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>(2);
        ColumnConfig column = new ColumnConfig();
        
        column = new ColumnConfig();
        
        column.setId("name");
        column.setHeader("Name");
        column.setWidth(100);
        configs.add(column);
        
        column = new ColumnConfig();
        column.setId("equations");
        column.setHeader("Equations");
        column.setWidth(200);
        configs.add(column);
        
        columnModel = new ColumnModel(configs);
        
        contentPanel = new ContentPanel();
        contentPanel.setHeading("Load system of equations");
        contentPanel.setFrame(true);
        // contentPanel.setSize(300, 300);
        contentPanel.setLayout(new FitLayout());
        
        add(contentPanel);
        hide();
    }
    
    public void showData()
    {
        persistentService.getAll(new AsyncCallback<List<SystemEntity>>()
        {
            
            @Override
            public void onSuccess(List<SystemEntity> result)
            {
                ListStore<SystemTableModel> store = new ListStore<SystemTableModel>();
                for (SystemEntity system : result)
                {
                    store.add(new SystemTableModel(system));
                }
                
                Grid<SystemTableModel> grid = new Grid<SystemTableModel>(store, columnModel);
                
                contentPanel.add(grid);
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
