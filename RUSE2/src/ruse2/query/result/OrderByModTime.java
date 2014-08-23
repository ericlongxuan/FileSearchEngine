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
public class OrderByModTime extends ResultSortBehavior{

    @Override
    public int Compare(Result r1, Result r2)
    {
        if(r1.modTime.compareTo(r2.modTime)<0)
            return 1;
        else if(r1.modTime.compareTo(r2.modTime)==0)
            return 0;
        else 
            return -1;
    }
}
