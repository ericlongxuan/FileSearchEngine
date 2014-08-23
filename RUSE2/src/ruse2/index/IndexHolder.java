/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.index;

import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author Administrator
 */
public class IndexHolder
{

    private static IndexObject index = new IndexObject(new TreeMap<String,FileInformation>(), new HashMap(1000));

    public static IndexObject getInstance()
    {
        return index;
    }

    public static void setIndex(IndexObject paraIndex)
    {
        index = paraIndex;
    }
    
    public static void reset()
    {
        index = new IndexObject(new TreeMap<String,FileInformation>(), new HashMap(1000));
    }
}
