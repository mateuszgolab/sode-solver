package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.service.solver.ModifiedMidpointService;


public class ModifiedMidpointSolverTestCase extends SolverTestCase
{
    
    @Override
    public void gwtSetUp()
    {
        solverService = ModifiedMidpointService.Util.getInstance();
        accuracy = 0.05;
    }
    
}
