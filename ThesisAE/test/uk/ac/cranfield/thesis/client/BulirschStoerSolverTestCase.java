package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.service.solver.BulirschStoerSolverService;


public class BulirschStoerSolverTestCase extends SolverTestCase
{
    
    @Override
    public void gwtSetUp()
    {
        solverService = BulirschStoerSolverService.Util.getInstance();
    }
    
    
}
