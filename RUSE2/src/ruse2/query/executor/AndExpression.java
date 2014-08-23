/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query.executor;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Administrator
 */
public class AndExpression extends Expression{

    private Expression expression1;
    private Expression expression2;
    
    public AndExpression()
    {
        
    }
    
    public AndExpression(Expression expression1, Expression expression2)
    {
        this.expression1= expression1;
        this.expression2= expression2;
    }
    
    @Override
    public Set<String> getResult()
    {
        Set<String> temp = expression1.getResult();
        Set<String> result = (Set<String>)new TreeSet(temp).clone();   
        result.retainAll(expression2.getResult());
        return result;
    }

}
