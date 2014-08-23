/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wwc.ruse.helper;

import java.io.*;
import java.util.*;

/**
 *
 * @author wwc
 */
/**用来从txt文件中获取全文转成string以及将string整个写入txt*/
public class TXTToString implements FileToString
{

    /**提取文件内容，传入fileName,返回文件内容(String)
     *@param fileName 表示文件路径
     *@return 文件内容(String)
     */
    public String read(String fileName) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String s;
        while ((s = in.readLine()) != null)
        {
            sb.append(s);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }

    /**静态方法，将string写入文件*/
    public static void write(String fileName, String text) throws IOException
    {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        out.print(text);
        out.close();
    }
}
