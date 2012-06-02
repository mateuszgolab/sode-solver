package uk.ac.cranfield.thesis.client;


public class EquationParser
{
    
    // private ExpressionBuilder calc;
    //
    // public EquationParser(String equation)
    // {
    // calc = new ExpressionBuilder(equation);
    // }
    //
    // public double getValue(Map<String, Double> variables) throws UnknownFunctionException,
    // UnparsableExpressionException
    // {
    // calc.withVariables(variables).build();
    //
    // return ((Calculable) calc).calculate();
    // }
    
    private String equation;
    
    public EquationParser(String equation)
    {
        this.equation = equation;
    }
    
    public String getIndependentVariable()
    {
        return "x";
    }
    
    public String getDependentVariable()
    {
        return "y";
    }
    
}
