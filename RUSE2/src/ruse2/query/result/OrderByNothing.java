/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query.result;

import cn.edu.nju.software.ruse.Tester.Result;

/**
 *
 * @author Administrator
 */
public class OrderByNothing extends ResultSortBehavior{

    @Override
    public int Compare(Result r1, Result r2)
    {
        return 1;
    }

}
