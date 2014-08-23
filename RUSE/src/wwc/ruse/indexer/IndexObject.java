/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wwc.ruse.indexer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Administrator
 */
/**索引文件类型，含有索引指向的文件夹中所有文本文件路径信息及单词指向文件的inverted index
 * 可序列化
 */
public class IndexObject implements Serializable{
    private TreeSet files;
    private HashMap indexMap;

    public IndexObject(TreeSet files, HashMap indexMap)
    {
        this.files = files;
        this.indexMap = indexMap;
    }

    /**返回索引指向的文件夹中所有文本文件路径信息
     *@return 各项内容为文件名string的TreeSet对象
     */
    public TreeSet getFiles()
    {
        return files;
    }

    /**返回单词指向文件的inverted index
     *@return HashMap对象,内容为:以单词为Key,文件名TreeSet为value的键-值映射
     */
    public HashMap getIndexMap()
    {
        return indexMap;
    }

}
