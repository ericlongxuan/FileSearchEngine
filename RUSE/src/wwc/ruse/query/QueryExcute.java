/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wwc.ruse.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeSet;
import wwc.ruse.indexer.IndexMaker;
import wwc.ruse.indexer.IndexObject;

/**
 *
 * @author Administrator
 */
/**查询语句执行器，用于执行中缀查询语句 
 * 该类中包含了QueryAnalyser的一个对象，用于将中缀查询语句转成后缀形式
 */
public class QueryExcute
{

    private QueryAnalyser queryAnalyser = new QueryAnalyser();
    private TreeSet queryResult = new TreeSet();
    private Stack oprandStack = new Stack();

    /**执行查询语句
     *@param query 查询语句
     *@param indexFile 索引文件的路径，查询将对该索引文件进行
     *@param algorithm 语句中单词的语言类别，用于指定对单词Stem操作时的algorithm,如果是英语，则传入"english"
     *@return 符合该查询的文件列表，以 TreeSet 的类型返回
     */
    public TreeSet excuteQuery(String query, String indexFile, String algorithm)
    {
        ArrayList queryList;
        IndexObject index;
        try
        {
            index = IndexMaker.readIndex(indexFile);
        }
        catch (Exception ex)
        {
            System.out.println("访问索引文件时出错" + ex);
            return null;
        }
        try
        {
            queryList = queryAnalyser.analyse(query, algorithm);
        }
        catch (Exception ex)
        {
            System.out.println("查询语句有语法错误" + ex);
            return null;
        }
        try
        {
            excuteQuery(queryList, index);
        }
        catch (Exception ex)
        {
            System.out.println("查询过程中出错,可能有语法错误,请重试" + ex);
            return null;
        }
        return queryResult;
    }

    private void excuteQuery(ArrayList queryList, IndexObject index) throws Exception
    {
        oprandStack.clear();
        for (int i = 0; i < queryList.size(); i++)
        {
            System.out.println(queryList.get(i) + "");
        }
        TreeSet allFiles = index.getFiles();
        HashMap indexMap = index.getIndexMap();
        for (int i = 0; i < queryList.size(); i++)
        {
            String temp = queryList.get(i).toString();
            if (temp.toUpperCase().equals("NOT"))
            {
                TreeSet allFilesCopy = (TreeSet) allFiles.clone();
                allFilesCopy.removeAll((TreeSet) oprandStack.pop());
                oprandStack.push(allFilesCopy);
            }
            else if (temp.toUpperCase().equals("AND"))
            {
                TreeSet oprand = (TreeSet) oprandStack.pop();
                TreeSet oprandCopy = (TreeSet) oprand.clone();
                oprandCopy.retainAll((TreeSet) oprandStack.pop());
                oprandStack.push(oprandCopy);
            }
            else if (temp.toUpperCase().equals("OR"))
            {
                TreeSet oprand = (TreeSet) oprandStack.pop();
                TreeSet oprandCopy = (TreeSet) oprand.clone();
                oprandCopy.addAll((TreeSet) oprandStack.pop());
                oprandStack.push(oprandCopy);
            }
            else
            {
                Object o = indexMap.get(temp);
                TreeSet set;
                if (o != null)
                {
                    set = (TreeSet) o;
                }
                else
                {
                    set = new TreeSet();
                }
                oprandStack.push(set);
            }

        }

        queryResult = (TreeSet) oprandStack.pop();
        if (oprandStack.size() != 0)
        {
            throw new Exception();
        }
    }
}
