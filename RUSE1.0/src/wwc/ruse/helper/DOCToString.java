/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wwc.ruse.helper;

import java.io.*;
import java.util.*;
import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 *
 * @author wwc
 */
/**用来从word文件中获取全文转成string以及将string整个写入word*/
public class DOCToString implements FileToString
{

    /**提取文件内容，传入fileName,返回文件内容(String)
     *@param fileName 表示文件路径
     *@return 文件内容(String)
     */
    public String read(String fileName) throws IOException
    {
        WordExtractor extractor = new WordExtractor(new FileInputStream(fileName));
        String text = extractor.getText();
        return text;
    }
}
