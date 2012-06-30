package uk.ac.cranfield.thesis.server.service.persistence;

import uk.ac.cranfield.thesis.client.service.persistence.EquationPersistenceService;
import uk.ac.cranfield.thesis.shared.model.Equation;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;


@SuppressWarnings("serial")
public class EquationPersistenceServiceImpl extends RemoteServiceServlet implements EquationPersistenceService
{
    
    static
    {
        ObjectifyService.register(Equation.class);
    }
    
    @Override
    public void persistEquation(Equation equation)
    {
        Objectify ofy = ObjectifyService.begin();
        ofy.put(equation);
    }
    
    @Override
    public Equation getEquation(String name)
    {
        Objectify ofy = ObjectifyService.begin();
        Query<Equation> q = ofy.query(Equation.class).filter("name", name);
        
        return q.get();
    }
    
}
