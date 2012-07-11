package uk.ac.cranfield.thesis.client.view;


public enum SolverMethod
{
    RUNGE_KUTTA("Runge-Kutta"), MODIFIED_MIDPOINT("Modified Midpoint"), BULIRSCH_STOER("Bulirsch-Stoer"), ROSENBROCK(
            "Rosenbrock");
    
    private final String text;
    
    private SolverMethod(final String method)
    {
        this.text = method;
    }
    
    @Override
    public String toString()
    {
        return text;
    }
}
