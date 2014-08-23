/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.query.interpret;

import java.util.ArrayList;
import java.util.Stack;
import ruse2.query.executor.*;

/**
 *
 * @author Administrator
 */
public class QueryInterpreter
{

    private State modTimeState = new ModTimeState();
    private State fileSizeState = new FileSizeState();
    private State fileContentState = new FileContentState();
    private State fileNameState = new FileNameState();
    private State proximityState = new ProximityState();
    private StringBuffer query;
    private Stack<String> operatorStack = new Stack<String>();
    private Stack<Expression> operandStack = new Stack<Expression>();
    //the postorder expressions interpreted from query
    private ArrayList<Expression> result = new ArrayList<Expression>();
    private State state = fileContentState;

    public QueryInterpreter(String query)
    {
        this.query = new StringBuffer(query);
    }

    public Expression interpret()
    {
        operatorStack.clear();
        result.clear();
        operatorStack.push("#");

        while (query.length() > 0)
        {
            char c = query.charAt(0);
            if (c == '(' || c == ')')
            {
                String temp = String.valueOf(c);
                query.delete(0, 1);
                //only ')' can do the following
                while (getPriority(temp, false) < getPriority(operatorStack.peek().toString(), true))
                {
                    Expression expression = buildOperatorExpression(operatorStack.pop());
                    operandStack.add(expression);
                }
                //Åä¶Ô³ÉÀ¨ºÅ
                if (getPriority(temp, false) == getPriority(operatorStack.peek().toString(), true))
                {
                    operatorStack.pop();
                    continue;
                }
                //it must be '('
                operatorStack.push(temp);
            }
            else if (new String(query).startsWith("\""))
            {
                int i = 1;
                boolean hasOccured = false;
                for (; i < query.length(); i++)
                {
                    if (query.charAt(i) == '\"')
                    {
                        hasOccured = true;
                    }
                    if (query.charAt(i) == ' ' && hasOccured)
                    {
                        break;
                    }
                }

                String temp = query.substring(0, i);
                query.delete(0, i);
                state = proximityState;
                temp = temp.toLowerCase();
                operandStack.push(interpret(temp));
            }
            else if (new String(query).startsWith("fileSize:"))
            {
                int i = 9;
                for (; i < query.length(); i++)
                {
                    if (query.charAt(i) == ']' || query.charAt(i) == '}')
                    {
                        break;
                    }
                }
                String temp = query.substring(9, i + 1);
                query.delete(0, i + 1);
                state = fileSizeState;
                temp = temp.toLowerCase();
                operandStack.push(interpret(temp));
            }
            else if (new String(query).startsWith("fileContents:"))
            {
                query.delete(0, 13);
                continue;
            }
            else if (new String(query).startsWith("modTime:"))
            {
                int i = 8;
                for (; i < query.length(); i++)
                {
                    if (query.charAt(i) == ']' || query.charAt(i) == '}')
                    {
                        break;
                    }
                }
                String temp = query.substring(8, i + 1);
                query.delete(0, i + 1);
                state = modTimeState;
                temp = temp.toLowerCase();
                operandStack.push(interpret(temp));
            }
            else if (new String(query).startsWith("fileName:"))
            {
                int i = 9;
                for (; i < query.length(); i++)
                {
                    if (query.charAt(i) == ']' || query.charAt(i) == '}')
                    {
                        break;
                    }
                }
                String temp = query.substring(9, i + 1);
                query.delete(0, i + 1);
                state = fileNameState;
                temp = temp.toLowerCase();
                operandStack.push(interpret(temp));
            }
            else if (query.charAt(0) == ' ')
            {
                query.delete(0, 1);
            }
            else
            {
                int i = 0;
                for (; i < query.length(); i++)
                {
                    if (!Character.isLetter(query.charAt(i)))
                    {
                        break;
                    }
                }
                String temp = query.substring(0, i);
                query.delete(0, i);
                //is operator
                if (isOperator(temp))
                {
                    while (getPriority(temp, false) < getPriority(operatorStack.peek().toString(), true))
                    {
                        Expression expression = buildOperatorExpression(operatorStack.pop());
                        operandStack.add(expression);
                    }
                    operatorStack.push(temp);
                }
                //fileContents
                else
                {
                    state = fileContentState;
                    temp = temp.toLowerCase();
                    operandStack.push(interpret(temp));
                }
            }
        }

        while (operatorStack.size() > 1)
        {
            Expression expression = buildOperatorExpression(operatorStack.pop());
            operandStack.add(expression);
        }
        
        return operandStack.pop();
    }

    private Expression buildOperatorExpression(String s)
    {
        Expression expression = null;
        s = s.toUpperCase();
        if (s.equals("AND"))
        {
            expression = new AndExpression(operandStack.pop(), operandStack.pop());
        }
        else if (s.equals("OR"))
        {
            expression = new OrExpression(operandStack.pop(), operandStack.pop());
        }
        else if (s.equals("NOT"))
        {
            expression = new NotExpression(operandStack.pop());
        }
        return expression;
    }

    private Expression interpret(String part)
    {
        return state.interpret(part);
    }

    private boolean isOperator(String s)
    {
        s = s.toUpperCase();
        if (s.equals("AND") || s.equals("OR"))
        {
            return true;
        }
        else if (s.equals("NOT"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private int getPriority(String s, boolean inStack)
    {
        s = s.toUpperCase();
        if (s.equals("OR"))
        {
            return 3 + (inStack ? 1 : 0);
        }
        else if (s.equals("AND"))
        {
            return 5 + (inStack ? 1 : 0);
        }
        else if (s.equals("NOT"))
        {
            return 7 + (inStack ? 1 : 0);
        }
        else if (s.equals("("))
        {
            return 9 + (inStack ? -8 : 0);
        }
        else if (s.equals(")"))
        {
            return 1;
        }
        else if (s.equals("#"))
        {
            return 0;
        }
        return 100;
    }
}
