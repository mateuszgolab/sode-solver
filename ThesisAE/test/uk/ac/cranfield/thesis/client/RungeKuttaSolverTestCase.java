package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.service.solver.RungeKuttaSolverService;


public class RungeKuttaSolverTestCase extends SolverTestCase
{
    
    @Override
    public void gwtSetUp()
    {
        solverService = RungeKuttaSolverService.Util.getInstance();
        accuracy = 0.0005;
    }
    
}
