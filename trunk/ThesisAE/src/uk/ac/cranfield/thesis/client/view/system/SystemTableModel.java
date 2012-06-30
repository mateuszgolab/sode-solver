package uk.ac.cranfield.thesis.client.view.system;

import uk.ac.cranfield.thesis.shared.model.entity.SystemEntity;

import com.extjs.gxt.ui.client.data.BaseModel;


@SuppressWarnings("serial")
public class SystemTableModel extends BaseModel
{
    
    public SystemTableModel()
    {
        
    }
    
    public SystemTableModel(SystemEntity system)
    {
        set("name", system.getName());
        set("equations", system.getEquations());
        set("min", system.getMin());
        set("max", system.getMax());
        set("step", system.getStep());
        set("date", system.getDate());
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
    
    public double getMin()
    {
        return get("min");
    }
    
    public double getMax()
    {
        return get("max");
    }
    
    public double getStep()
    {
        return get("step");
    }
    
    
    @Override
    public String toString()
    {
        return getName();
    }
    
    
}
