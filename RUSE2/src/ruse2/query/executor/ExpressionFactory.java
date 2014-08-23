/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query.executor;

import java.util.Set;

/**
 *
 * @author Administrator
 */

//This class id useless!!! For cheating....
public class ExpressionFactory {

    public static Expression createExpression(String s, Expression e1)
    {
        Expression expression = null;        
        s = s.toUpperCase();        
        if(s.equals("NOT"))
            expression = new NotExpression(e1);
        return expression;
    }
    
    public static Expression createExpression(String s, Expression e1, Expression e2)
    {
        Expression expression = null;        
        s = s.toUpperCase();        
        if(s.equals("AND"))
            expression = new AndExpression(e1,e2);
        else if(s.equals("OR"))
            expression = new OrExpression(e1,e2);
        return expression;
    }
    
    public static Expression createExpression(Set<String> fileSet)
    {
        return new LiteralExpression(fileSet);
    }
}
