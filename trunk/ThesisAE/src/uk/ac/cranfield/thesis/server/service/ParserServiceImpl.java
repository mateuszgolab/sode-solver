/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uk.ac.cranfield.thesis.server.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.cranfield.thesis.client.service.ParserService;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.System;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ParserServiceImpl extends RemoteServiceServlet implements ParserService
{
    
    public static final String[] mathFunctions = {"sin", "cos", "asin", "acos", "tan", "atan", "cosh", "sinh", "exp",
            "log", "sqrt", "tanh"};
    
    /**
     * Parses single equation
     * @param input equation
     * @return parsed equation
     * @throws IncorrectODEEquationException
     */
    @Override
    public Equation parseEquation(String input) throws IncorrectODEEquationException
    {
        input = input.replace(" ", "").toLowerCase();
        String[] parts = input.split(",");
        
        if (parts.length < 2)
            throw new IncorrectODEEquationException("lack of initial values");
        
        Equation equation = new Equation(parts[0]);
        equation.setOrder(longestRun(parts[0], '\'', 1));
        
        List<String> init = new ArrayList<String>(parts.length - 1);
        for (int i = 1; i < parts.length; i++)
            init.add(parts[i]);
        
        equation.setInitValues(parseInitialValues(init));
        equation.setFunctionVariable(parseFunctionVariable(parts[0]));
        equation.setIndependentVariable(parseIndependentVariable(parts[0], equation.getFunctionVariable()));
        
        if (equation.getFunctionVariable() != parts[1].charAt(0))
            throw new IncorrectODEEquationException("Incorrect initial variable");
        
        return equation;
    }
    
    /**
     * Parses system of equations
     * @param inputs system of equations
     * @return parsed system
     * @throws IncorrectODEEquationException
     */
    @Override
    public System parseEquationsSystem(List<String> inputs) throws IncorrectODEEquationException
    {
        System system = new System();
        List<Character> functionalVariables = new ArrayList<Character>(inputs.size());
        
        for (String input : inputs)
        {
            input = input.replace(" ", "").toLowerCase();
            String[] parts = input.split(",");
            
            Equation equation = new Equation(parts[0]);
            equation.setOrder(longestRun(parts[0], '\'', 1));
            if (equation.getOrder() < parts.length - 1)
                throw new IncorrectODEEquationException("lack of initial values");
            
            List<String> init = new ArrayList<String>(parts.length - 1);
            for (int i = 1; i < parts.length; i++)
                init.add(parts[i]);
            
            equation.setInitValues(parseInitialValues(init));
            equation.setFunctionVariable(parseFunctionVariable(parts[0]));
            functionalVariables.add(equation.getFunctionVariable());
            
            system.addEquation(equation);
        }
        
        
        List<Equation> equations = system.getEquations();
        char independentVariable = parseIndependentVariable(inputs, functionalVariables);
        
        for (Equation eq : equations)
        {
            eq.setIndependentVariable(independentVariable);
        }
        
        return system;
    }
    
    /**
     * Returns character that is a symbol of a function in a equation
     * @param input equation
     * @return functional variable
     * @throws IncorrectODEEquationException
     */
    private char parseFunctionVariable(String input) throws IncorrectODEEquationException
    {
        char result = 0;
        for (char ch = 'a'; ch <= 'z'; ch++)
        {
            Pattern p = Pattern.compile(ch + " *'+");
            Matcher m = p.matcher(input);
            if (m.find())
            {
                if (result == 0)
                    result = ch;
                else
                    throw new IncorrectODEEquationException(input);
            }
        }
        
        return result;
    }
    
    private int longestRun(String s, char ch, int n)
    {
        int nm = n - 1;
        if (RegExp.compile("(" + ch + "){" + n + ",}").test(s))
        {
            nm = longestRun(s, ch, n + 1);
        }
        return nm;
    }
    
    /**
     * Returnes independent variable of the equation
     * @param input equation
     * @param functionalVariable symbol of functional variable (to exclude from searching set)
     * @return independent variable
     * @throws IncorrectODEEquationException
     */
    private char parseIndependentVariable(String input, char functionalVariable) throws IncorrectODEEquationException
    {
        Set<Character> invalid = new HashSet<Character>();
        Set<Character> valid = new HashSet<Character>();
        for (String f : mathFunctions)
        {
            input = input.replace(f, "");
        }
        
        for (int i = 0; i < input.length(); i++)
        {
            char ch = input.charAt(i);
            if (Character.isLetter(ch))
            {
                if (input.contains(ch + "'"))
                {
                    invalid.add(ch);
                }
                else
                {
                    valid.add(ch);
                }
            }
        }
        
        if (valid.size() > 1)
        {
            throw new IncorrectODEEquationException("Too many independent variables in equation");
        }
        
        for (Character ch : valid)
        {
            return ch;
        }
        
        return 0;
        
    }
    
    private List<Character> parseIndependentVariable(String input, Set<Character> functionalVariables)
    {
        List<Character> result = new ArrayList<Character>();
        
        for (String f : mathFunctions)
        {
            input = input.replace(f, "");
        }
        
        for (int i = 0; i < input.length(); i++)
        {
            char ch = input.charAt(i);
            if (Character.isLetter(ch))
            {
                if (!input.contains(ch + "'") && !functionalVariables.contains(ch))
                {
                    result.add(ch);
                }
            }
        }
        
        return result;
        
    }
    
    private char parseIndependentVariable(List<String> inputs, List<Character> functionalVariables)
            throws IncorrectODEEquationException
    {
        Set<Character> result = new TreeSet<Character>();
        Set<Character> functionVariablesSet = new TreeSet<Character>(functionalVariables);
        
        for (String input : inputs)
        {
            result.addAll(parseIndependentVariable(input.toLowerCase(), functionVariablesSet));
        }
        
        
        if (result.size() > 1)
        {
            throw new IncorrectODEEquationException("Too many variables in equation");
        }
        
        for (Character ch : result)
        {
            if (ch != null)
            {
                return ch;
            }
        }
        
        return 0;
        
    }
    
    /**
     * Parses initial values
     * @param data initial values to parse
     * @return initial values
     * @throws IncorrectODEEquationException
     */
    private List<Double> parseInitialValues(List<String> data) throws IncorrectODEEquationException
    {
        List<Double> result = new ArrayList<Double>(data.size());
        for (int i = 0; i < data.size(); i++)
            result.add(0.0);
        
        for (String s : data)
        {
            String[] values = s.split("=");
            if (values.length < 2)
                throw new IncorrectODEEquationException("Wrong input of initial values");
            
            int i = longestRun(values[0], '\'', 1);
            result.set(i, Double.valueOf(values[1]));
        }
        
        return result;
    }
    
}
