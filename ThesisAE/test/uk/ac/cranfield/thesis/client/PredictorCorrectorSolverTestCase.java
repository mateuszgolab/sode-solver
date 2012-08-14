package uk.ac.cranfield.thesis.client;

import uk.ac.cranfield.thesis.client.service.solver.PredictorCorrectorSolverService;


public class PredictorCorrectorSolverTestCase extends SolverTestCase
{
    
    @Override
    public void gwtSetUp()
    {
        solverService = PredictorCorrectorSolverService.Util.getInstance();
        accuracy = 0.001;
    }
    
}
