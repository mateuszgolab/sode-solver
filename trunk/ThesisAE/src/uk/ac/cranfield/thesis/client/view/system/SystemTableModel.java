package uk.ac.cranfield.thesis.client.view.system;

import java.util.List;

import uk.ac.cranfield.thesis.shared.model.SystemEntity;

import com.extjs.gxt.ui.client.data.BaseModel;


@SuppressWarnings("serial")
public class SystemTableModel extends BaseModel
{
    
    public SystemTableModel()
    {
        
    }
    
    public SystemTableModel(String name, List<String> equations)
    {
        set("name", name);
        set("equations", equations);
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
    
    public String getEquations()
    {
        return get("equations");
    }
    
    public void setEquations(String equations)
    {
        set("equations", equations);
    }
    
    @Override
    public String toString()
    {
        return getName();
    }
    
    
}
