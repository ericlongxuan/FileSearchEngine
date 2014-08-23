/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author Administrator
 */

public class IndexObject implements Serializable{
    private TreeMap<String,FileInformation> filesMap;
    private HashMap<String,HashMap<String,ArrayList<Long>>> indexMap;

    public IndexObject(TreeMap<String,FileInformation> files, HashMap<String,HashMap<String,ArrayList<Long>>> indexMap)
    {
        this.filesMap = files;
        this.indexMap = indexMap;
    }


    public TreeMap<String,FileInformation> getFilesMap()
    {
        return filesMap;
    }


    public HashMap<String,HashMap<String,ArrayList<Long>>> getIndexMap()
    {
        return indexMap;
    }

}
