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
public class LiteralExpression extends Expression{
    
    Set<String> fileSet;
    
    public LiteralExpression(Set<String> fileSet)
    {
        this.fileSet=fileSet;
    }

    @Override
    public Set<String> getResult()
    {
        return fileSet;
    }
}
