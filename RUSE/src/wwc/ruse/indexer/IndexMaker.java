/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wwc.ruse.indexer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.TreeSet;
import wwc.ruse.helper.*;

/**
 *
 * @author Administrator
 */
/**索引生成器，用于对某个文件夹中所有txt,doc文件生成索引
 * 该类中包含了实现FileToString类的一个对象，用于从文件中取出整个的内容string
 */
public class IndexMaker
{

    private FileToString fileToStr;
    private HashMap indexMap = new HashMap(1000);
    private TreeSet filesName = new TreeSet();

    /**生成索引
     *@param folder 文件夹的路径，索引将对该文件夹中文件进行
     *@param outputFile 生成索引文件的路径，文件中的内容是对IndexObject对象的序列化
     *@param algorithm 语句中单词的语言类别，用于指定对单词Stem操作时的algorithm,如果是英语，则传入"english"
     */
    public void makeIndex(String folder, String outputFile, String algorithm) throws Throwable
    {
        String currentStem;
        String fileName;

        File file = new File(folder);
        if (!(file.exists() && file.isDirectory()))
        {
            System.err.println("该文件夹不存在");
            return;
        }
        String[] list = (file).list();
        for (int i = 0; i < list.length; i++)
        {
            fileName = list[i];
            if (fileName.equals(outputFile))
            {
                continue;
            }
            String fileType = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
            fileType = fileType.toUpperCase();
            if (!(fileType.equals("TXT") || fileType.equals("DOC")))
            {
                continue;
            }
            filesName.add(fileName);
            setExtractor(fileType);
            ArrayList stemsInOneFile = MyStemmer.stringToStemsWithOriginForm(fileToStr.read(folder + "\\" + fileName), algorithm);
            for (int j = 0; j < stemsInOneFile.size(); j++)
            {
                currentStem = stemsInOneFile.get(j).toString();
                if (!indexMap.containsKey(currentStem))
                {
                    TreeSet singleStemTreeSet = new TreeSet();
                    singleStemTreeSet.add(fileName);
                    indexMap.put(currentStem, singleStemTreeSet);
                }
                else
                {
                    ((TreeSet) indexMap.get(currentStem)).add(fileName);
                }
            }
        }
        recordIndex(outputFile);

    //可能还会用到，暂时不删
    /*
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("out2.txt")));
    Iterator it = indexMap.entrySet().iterator();
    while (it.hasNext())
    {
    Map.Entry entry = (Map.Entry) it.next();
    String key = entry.getKey().toString();
    out.print(key + "-> ");
    Object value = entry.getValue();
    Iterator it2 = ((TreeSet) value).iterator();
    while (it2.hasNext())
    {
    out.print(it2.next().toString() + " ");
    }
    out.println();
    }
    out.close();*/
    }

    private void setExtractor(String fileType)
    {
        fileToStr = ContentExtractorFactory.createExtractor(fileType);
    }
    
    
    private void recordIndex(String outputFile) throws FileNotFoundException, IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile));
        out.writeObject(new IndexObject(filesName, indexMap));
        out.flush();
        out.close();
    }

    /**返回没有序列化的IndexObject对象，如果已经生成过索引，可以直接调用该方法获得索引对象
     */
    public IndexObject getCurrentIndex()
    {
        return new IndexObject(filesName, indexMap);
    }

    /**静态方法，反序列化方法，传入索引文件的路径,返回IndexObject对象
     *@param indexFile 索引文件的路径
     *@return 索引文件中的IndexObject对象
     */
    public static IndexObject readIndex(String indexFile) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(indexFile));
        IndexObject index = (IndexObject) in.readObject();
        in.close();
        return index;
    }
}
