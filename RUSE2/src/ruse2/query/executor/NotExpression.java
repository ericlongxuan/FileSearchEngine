/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query.executor;

import java.util.Set;
import java.util.TreeSet;
import ruse2.index.IndexHolder;

/**
 *
 * @author Administrator
 */
public class NotExpression extends Expression{

    private Expression expression;
    private Expression expression2;
    
    public NotExpression()
    {
        
    }
    
    public NotExpression(Expression expression)
    {
        this.expression= expression;
    }
    
    @Override
    public Set<String> getResult()
    {
        Set<String> temp = IndexHolder.getInstance().getFilesMap().keySet();
        Set<String> result = (Set<String>)new TreeSet(temp).clone();        
        result.removeAll(expression2.getResult());
        return result;
    }

}
