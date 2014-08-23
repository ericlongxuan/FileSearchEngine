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
/**用来从DOC文件中获取全文转成stringd以及将string整个写入word*/
public class DOCToString implements FileToString
{

    /**从word文件中获取全文，转成string
     *@param fileName 表示要进行读取的文件路径
     */
    public String read(String fileName) throws IOException
    {
        WordExtractor extractor = new WordExtractor(new FileInputStream(fileName));
        String text = extractor.getText();
        return text;
    }
}
