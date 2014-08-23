package cn.edu.nju.software.ruse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import ruse2.commands.*;
import ruse2.adapters.*;
import ruse2.index.*;
import ruse2.query.QueryExecuteFacade;
import ruse2.query.result.OrderByModTime;
import ruse2.query.result.OrderByNothing;
import ruse2.query.result.OrderBySize;
import ruse2.query.result.ResultSortBehavior;

/**
 * A facade for testing your RUSE. Change the implementation of the
 * two methods to adapt your version of RUSE.
 * 
 */
public class Tester
{

    /**
     * Entrance to the indexing functionality.
     * 
     * @param dataDir
     *            the root directory of the files
     * @param indexDir
     *            the directory to place your index data
     */
    public static void index(File dataDir, File indexDir)
    {
        // add your code here
        try
        {
            TemplateIndexMaker indexMaker = new IndexMaker();
            Command command = new IndexMakeCommandAdapter(dataDir, indexMaker);
            command.execute();
            command = new IndexSaveCommand(indexDir.toString() + "\\index.txt");
            command.execute();
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Entrance to the search functionality.
     * 
     * @param indexDir
     *            the index directory
     * @param query
     *            the query
     * @param order
     * 			0: no order; 1: ordered by file size; 2: ordered by last modified time
     * @return a list containing the results that match the query
     */
    public static List<Result> search(File indexDir, String query, int order)
    {
        // add your code here        
        Set<String> fileSet = null;
        List<Result> resultList = new ArrayList<Result>();
        try
        {
            Command command = new IndexLoadCommand(indexDir.toString() + "\\index.txt");
            command.execute();
            fileSet = QueryExecuteFacade.getQueryResult(query);
            
            TreeMap<String, FileInformation> filesMap = IndexHolder.getInstance().getFilesMap();
            Iterator<String> iterator = fileSet.iterator();
            while (iterator.hasNext())
            {
                String filePath = iterator.next();
                FileInformation fileInfo = filesMap.get(filePath);
                resultList.add(new Result(fileInfo.size,fileInfo.name,fileInfo.modTime));
            }
            if(order==1)
            {
                Result.setSortBehavior(new OrderBySize());
            }
            else if(order==2)
            {
                Result.setSortBehavior(new OrderByModTime());
            }
            else
            {
                Result.setSortBehavior(new OrderByNothing());
            }
            Result[] resultArray = resultList.toArray(new Result[0]);
            Arrays.sort(resultArray);
            resultList = Arrays.asList(resultArray);
            
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }

        return resultList;
    }

    public static class Result implements Comparable 
    {
        public int fileSize;
        public String fileName,  modTime;

        private static ResultSortBehavior sortBehavior;
        
        public Result(long fileSize, String fileName, String modTime)
        {
            this.fileSize = (int)fileSize;
            this.fileName = fileName;
            this.modTime = modTime;
        }
                
        public static void setSortBehavior (ResultSortBehavior paraSortBehavior)
        {
            sortBehavior = paraSortBehavior;
        }
        
        public int compareTo(Object o)
        {
            return sortBehavior.Compare(this, (Result)o);
        }             
    }
}

