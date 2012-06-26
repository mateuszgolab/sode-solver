package uk.ac.cranfield.thesis.client.view.widget;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.data.BaseModelData;


@SuppressWarnings("serial")
public class SystemTableModel extends BaseModelData
{
    
    public SystemTableModel()
    {
        
    }
    
    public SystemTableModel(SystemEntity system)
    {
        set("name", system.getName());
        set("equations", system.getEquations());
    }
    
    
    public String getName()
    {
        return get("name");
    }
    
    public void setName(String name)
    {
        set("name", name);
    }
    
    public List<String> getEquations()
    {
        return get("equations");
    }
    
    public void setEquations(List<String> equations)
    {
        set("equations", equations);
    }
    
    @Override
    public String toString()
    {
        return getName();
    }
    
    
}
