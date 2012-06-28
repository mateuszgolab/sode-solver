package uk.ac.cranfield.thesis.client.view.system;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
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


public class SystemTable extends LayoutContainer
{
    
    private ColumnModel columnModel;
    private ContentPanel contentPanel;
    private Grid<SystemTableModel> grid;
    private ListStore<SystemTableModel> store;
    private SystemTableModel selected;
    private SystemWidget parentWidget;
    
    public SystemTable(SystemWidget parent)
    {
        parentWidget = parent;
        store = new ListStore<SystemTableModel>();
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
            public Object render(final SystemTableModel model, final String property, ColumnData config,
                    final int rowIndex, final int colIndex, ListStore<SystemTableModel> store,
                    Grid<SystemTableModel> grid)
            {
                
                Button b = new Button("show equations", new SelectionListener<ButtonEvent>()
                {
                    
                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        List<String> equations = model.get("equations");
                        String output = "<ul><li>";
                        for (String eq : equations)
                        {
                            output += eq + "<br>";
                        }
                        output += "</li></ul>";
                        
                        Info.display((String) model.get("name"), output);
                        
                    }
                });
                b.setToolTip("Click for more information");
                return b;
                
            }
        };
        
        
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        ColumnConfig column = new ColumnConfig();
        
        column = new ColumnConfig();
        
        column.setId("name");
        column.setHeader("Name");
        column.setWidth(150);
        configs.add(column);
        
        column = new ColumnConfig();
        column.setId("equations");
        column.setHeader("Equations");
        column.setRenderer(buttonRenderer);
        column.setWidth(150);
        configs.add(column);
        
        columnModel = new ColumnModel(configs);
        
        grid = new Grid<SystemTableModel>(store, columnModel);
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        grid.getSelectionModel().addListener(Events.SelectionChange,
                new Listener<SelectionChangedEvent<SystemTableModel>>()
                {
                    
                    @Override
                    public void handleEvent(SelectionChangedEvent<SystemTableModel> be)
                    {
                        if (be.getSelection().size() > 0)
                        {
                            selected = be.getSelection().get(0);
                            parentWidget.setButtonsEnabled(true);
                            
                        }
                        else
                        {
                            selected = null;
                            parentWidget.setButtonsEnabled(false);
                        }
                    }
                });
        grid.getView().setAutoFill(true);
        contentPanel = new ContentPanel();
        contentPanel.add(grid);
        contentPanel.setHeading("System of equations");
        contentPanel.setFrame(true);
        contentPanel.setLayout(new FitLayout());
        contentPanel.setSize(300, 400);
        
        add(contentPanel);
        
        
    }
    
    public List<String> getSelectedEquations()
    {
        return selected.get("equations");
    }
    
    public String getSelectedName()
    {
        return selected.get("name");
    }
    
    public void setData(List<SystemEntity> data)
    {
        store.removeAll();
        for (SystemEntity system : data)
        {
            store.add(new SystemTableModel(system));
        }
        
        
    }
}
