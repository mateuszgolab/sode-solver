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
    
    public SystemTableModel(String name, List<String> equations, double min, double max, double step)
    {
        set("name", name);
        set("equations", equations);
        set("min", min);
        set("max", max);
        set("step", step);
    }
    
    public SystemTableModel(SystemEntity system)
    {
        set("name", system.getName());
        set("equations", system.getEquations());
        set("min", system.getMin());
        set("max", system.getMax());
        set("step", system.getStep());
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
