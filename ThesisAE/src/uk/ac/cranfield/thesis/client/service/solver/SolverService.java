package uk.ac.cranfield.thesis.client.service.solver;

import java.util.List;

import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.Solution;
import uk.ac.cranfield.thesis.shared.model.System;


public interface SolverService
{
    
    Solution solve(Equation equation, double step, double start, double stop) throws IncorrectODEEquationException,
            Exception;
    
    List<Solution> solveSystem(System system, double step, double start, double stop)
            throws IncorrectODEEquationException, Exception;
}
