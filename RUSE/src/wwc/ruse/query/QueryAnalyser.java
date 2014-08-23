/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wwc.ruse.query;

import java.util.ArrayList;
import java.util.Stack;
import org.tartarus.snowball.SnowballStemmer;

/**
 *
 * @author Administrator
 */
/**查询语句分析器，用于将中缀查询语句转成后缀形式
 */
public class QueryAnalyser
{

    private StringBuffer query;
    private Stack operatorStack = new Stack();
    private ArrayList result = new ArrayList();
    private boolean lastIsNot = false;

    /**分析查询语句，将中缀查询语句转成后缀形式
     *@param s 查询语句
     *@param algorithm 语句中单词的语言类别，用于指定对单词Stem操作时的algorithm,如果是英语，则传入"english"
     *@return 后缀形式的查询，操作符和操作数都存入arrayList 
     */
    public ArrayList analyse(String s, String algorithm) throws Exception
    {
        query = new StringBuffer(s);
        analyse(algorithm);
        return result;
    }

    private void analyse(String algorithm) throws Exception
    {
        String temp;

        operatorStack.clear();
        result.clear();
        operatorStack.push("#");
        while (query.length() > 0)
        {
            char c = query.charAt(0);
            if (c == '(' || c == ')')
            {
                temp = String.valueOf(c);
                query.delete(0, 1);
                while (getPriority(temp, false) < getPriority(operatorStack.peek().toString(), true))
                {
                    result.add(operatorStack.pop());
                }
                //配对成括号
                if (getPriority(temp, false) == getPriority(operatorStack.peek().toString(), true))
                {
                    operatorStack.pop();
                    continue;
                }
                operatorStack.push(temp);
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
                temp = query.substring(0, i);
                query.delete(0, i);
                if (isOperator(temp))
                {
                    while (getPriority(temp, false) < getPriority(operatorStack.peek().toString(), true))
                    {
                        result.add(operatorStack.pop());
                    }
                    operatorStack.push(temp);
                }
                else
                {
                    temp = temp.toLowerCase();
                    if (lastIsNot)
                    {
                        result.add(temp);
                    }
                    else
                    {
                        Class stemClass = Class.forName("org.tartarus.snowball.ext." + algorithm + "Stemmer");
                        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
                        stemmer.setCurrent(temp);
                        stemmer.stem();
                        result.add(stemmer.getCurrent());
                    }
                }
            }
        }
        while (operatorStack.size() > 0)
        {
            result.add(operatorStack.pop());
        }
        result.remove(result.size() - 1);
    }

    private boolean isOperator(String s)
    {
        s = s.toUpperCase();
        if (s.equals("AND") || s.equals("OR"))
        {
            //lastIsNot = false;
            return true;
        }
        else if (s.equals("NOT"))
        {
            //lastIsNot = true;
            return true;
        }
        else
        {
            //lastIsNot = false;
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
