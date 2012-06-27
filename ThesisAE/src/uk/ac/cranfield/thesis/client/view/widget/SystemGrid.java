package uk.ac.cranfield.thesis.client.view.widget;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.Element;


public class SystemGrid extends LayoutContainer
{
    
    private ColumnModel columnModel;
    private ContentPanel contentPanel;
    private Grid<SystemTableModel> grid;
    ListStore<SystemTableModel> store;
    
    
    public SystemGrid()
    {
        store = new ListStore<SystemTableModel>();
        // List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        // ColumnConfig column = new ColumnConfig();
        //
        // column = new ColumnConfig();
        //
        // column.setId("name");
        // column.setHeader("Name");
        // // column.setWidth(100);
        // configs.add(column);
        //
        // column = new ColumnConfig();
        // column.setId("equations");
        // column.setHeader("Equations");
        // column.setWidth(200);
        // configs.add(column);
        //
        // columnModel = new ColumnModel(configs);
    }
    
    @Override
    protected void onRender(Element parent, int index)
    {
        super.onRender(parent, index);
        setLayout(new FlowLayout(2));
        getAriaSupport().setPresentation(true);
        
        GridCellRenderer<SystemTableModel> buttonRenderer = new GridCellRenderer<SystemTableModel>()
        {
            
            @Override
            public Object render(final SystemTableModel model, String property, ColumnData config, final int rowIndex,
                    final int colIndex, ListStore<SystemTableModel> store, Grid<SystemTableModel> grid)
            {
                
                Button b = new Button("show equations", new SelectionListener<ButtonEvent>()
                {
                    
                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Info.display("Equations", "<ul><li>y'= y + x + 1, y = 0 <br>z'= z + y + x, z = 0, </li></ul>");
                    }
                });
                // b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
                
                b.setToolTip("Click for more information");
                return b;
                
            }
        };
        
        
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        ColumnConfig column = new ColumnConfig();
        
        column = new ColumnConfig();
        
        column.setId("name");
        column.setHeader("Name");
        column.setWidth(100);
        configs.add(column);
        
        column = new ColumnConfig();
        column.setId("equations");
        column.setHeader("Equations");
        column.setRenderer(buttonRenderer);
        column.setWidth(100);
        configs.add(column);
        
        columnModel = new ColumnModel(configs);
        
        grid = new Grid<SystemTableModel>(store, columnModel);
        grid.getView().setAutoFill(true);
        contentPanel = new ContentPanel();
        contentPanel.add(grid);
        contentPanel.setHeading("Load system of equations");
        contentPanel.setFrame(true);
        contentPanel.setLayout(new FitLayout());
        contentPanel.setSize(300, 400);
        
        add(contentPanel);
        
        
    }
    
    public void setData(List<SystemEntity> data)
    {
        
        for (SystemEntity system : data)
        {
            store.add(new SystemTableModel(system));
        }
        
        
    }
}
