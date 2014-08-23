/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wwc.ruse.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.tartarus.snowball.SnowballStemmer;

/**
 *
 * @author Administrator
 */
/**扩展了stemmer，用来从一大串string中提取单词并还原为词根*/
public class MyStemmer
{

    private static Scanner scanner;

    private MyStemmer()
    {
    }

    /**静态方法，将ArrayList 一项项toString()后写入文件*/
    public static void write(String fileName, ArrayList text) throws IOException
    {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        for (int i = 0; i < text.size(); i++)
        {
            out.print(text.get(i).toString() + "\n");
        }
        out.close();
    }

    /**静态方法，传入全文String,返回ArrayList,内容各项是已经提取的单词，并还原为词根形式
     *@param text 全文String
     *@return 已经提取的单词，并还原为词根形式
     */
    public static ArrayList stringToStems(String text, String algorithm) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ArrayList arrList = new ArrayList();
        Class stemClass = Class.forName("org.tartarus.snowball.ext." + algorithm + "Stemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
        scanner = new Scanner(text);
        scanner.useDelimiter("\\s");
        while (scanner.hasNext())
        {
            String temp = scanner.next();
            if (!temp.equals(""))
            {
                stemmer.setCurrent(temp);
                stemmer.stem();
                arrList.add(stemmer.getCurrent());
            }
        }
        return arrList;
    }
}
