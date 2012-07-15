package uk.ac.cranfield.thesis.shared.exception;

import com.google.gwt.user.client.rpc.IsSerializable;


@SuppressWarnings("serial")
public class IncorrectODEEquationException extends Exception implements IsSerializable
{
    
    
    public IncorrectODEEquationException()
    {
        super();
    }
    
    public IncorrectODEEquationException(String input)
    {
        super(input);
    }
}
