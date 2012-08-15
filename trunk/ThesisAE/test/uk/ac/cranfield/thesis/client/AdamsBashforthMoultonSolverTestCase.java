package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.service.solver.AdamsBashforthMoultonSolverService;


public class AdamsBashforthMoultonSolverTestCase extends SolverTestCase
{
    
    @Override
    public void gwtSetUp()
    {
        solverService = AdamsBashforthMoultonSolverService.Util.getInstance();
        accuracy = 0.01;
    }
    
}
