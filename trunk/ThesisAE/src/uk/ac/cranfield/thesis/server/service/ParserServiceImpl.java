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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.cranfield.thesis.client.service.ParserService;
import uk.ac.cranfield.thesis.shared.exception.IncorrectODEEquationException;
import uk.ac.cranfield.thesis.shared.model.Equation;
import uk.ac.cranfield.thesis.shared.model.EquationsSystem;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ParserServiceImpl extends RemoteServiceServlet implements ParserService
{
    
    @Override
    public Equation parseEquation(String input) throws IncorrectODEEquationException
    {
        input = input.replace(" ", "");
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
        equation.setIndependentVariable(parseIndependentVariable(parts[0], equation.getFunctionVariable()));
        
        return equation;
    }
    
    @Override
    public EquationsSystem parseEquationsSystem(List<String> inputs) throws IncorrectODEEquationException
    {
        EquationsSystem system = new EquationsSystem();
        List<Character> functionalVariables = new ArrayList<Character>(inputs.size());
        
        for (String input : inputs)
        {
            input = input.replace(" ", "");
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
    
    private char parseIndependentVariable(String input, char functionalVariable) throws IncorrectODEEquationException
    {
        char result = 0;
        for (char ch = 'a'; ch <= 'z'; ch++)
        {
            Pattern p = Pattern.compile("^[^" + ch + "]*" + ch + "[^" + ch + "]*$");
            Matcher m = p.matcher(input);
            if (m.find() && ch != functionalVariable)
            {
                // if (result == 0)
                // result = ch;
                // else
                // throw new IncorrectODEEquationException(input);
            }
        }
        
        return result;
    }
    
    private char parseIndependentVariable(List<String> inputs, List<Character> functionalVariables)
            throws IncorrectODEEquationException
    {
        char result = 0;
        int k = 0;
        Set<Character> set = new TreeSet<Character>();
        List<Character> results = new ArrayList<Character>();
        
        String input = inputs.get(0);
        
        for (char ch = 'a'; ch <= 'z'; ch++)
        {
            Pattern p = Pattern.compile("^[^" + ch + "]*" + ch + "[^" + ch + "]*$");
            Matcher m = p.matcher(input);
            if (m.find() && ch != functionalVariables.get(k))
            {
                results.add(ch);
            }
        }
        
        for (int i = 1; i < inputs.size(); i++)
        {
            for (char ch = 'a'; ch <= 'z'; ch++)
            {
                Pattern p = Pattern.compile("^[^" + ch + "]*" + ch + "[^" + ch + "]*$");
                Matcher m = p.matcher(inputs.get(i));
                if (m.find() && ch != functionalVariables.get(k))
                {
                    set.add(ch);
                }
            }
            
            for (Character res : results)
            {
                if (!set.contains(res))
                {
                    results.remove(res);
                }
            }
            
            if (results.isEmpty())
            {
                throw new IncorrectODEEquationException(inputs.get(i));
            }
            
            set.clear();
            k++;
        }
        
        if (results.size() != 1)
        {
            throw new IncorrectODEEquationException(inputs.toString());
        }
        
        for (Character ch : results)
        {
            if (ch != null)
            {
                return ch;
            }
        }
        
        return result;
    }
    
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
