/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query.interpret;

import java.util.HashSet;
import ruse2.index.IndexHolder;
import ruse2.query.executor.Expression;
import ruse2.query.executor.LiteralExpression;

/**
 *
 * @author Administrator
 */
public class FileContentState extends State{

    @Override
    public Expression interpret(String s)
    {
        if(IndexHolder.getInstance().getIndexMap().get(s)!=null)
            return new LiteralExpression(IndexHolder.getInstance().getIndexMap().get(s).keySet());
        else 
            return new LiteralExpression(new HashSet());
    }

}
