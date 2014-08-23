/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query;

import java.util.Set;
import ruse2.query.executor.*;
import ruse2.query.interpret.*;

/**
 *
 * @author Administrator
 */
public class QueryExecuteFacade {
    
    public static Set<String> getQueryResult(String query)
    {
        QueryInterpreter iterpreter = new QueryInterpreter(query);
        Expression wholeExpression = iterpreter.interpret();
        Set<String> fileSet = wholeExpression.getResult();
        return fileSet;
    }
}
