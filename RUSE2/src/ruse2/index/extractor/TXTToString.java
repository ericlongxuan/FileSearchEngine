/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.index.extractor;

import java.io.*;
import java.util.*;

/**
 *
 * @author wwc
 */
public class TXTToString implements FileToString
{
    private static TXTToString txtToString = new TXTToString();
    private static String fileName;

    private TXTToString()
    {
    }

    public static TXTToString getInstance(String tempFileName)
    {
        fileName = tempFileName;
        return txtToString;
    }

    public String read() throws IOException
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


    public static void write(String fileName, String text) throws IOException
    {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        out.print(text);
        out.close();
    }
}
