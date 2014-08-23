/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.index.extractor;

import java.io.*;
import java.util.*;
import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 *
 * @author wwc
 */
public class DOCToString implements FileToString
{

    private static DOCToString docToString = new DOCToString();
    private static String fileName;

    private DOCToString()
    {
    }

    public static DOCToString getInstance(String tempFileName)
    {
        fileName = tempFileName;
        return docToString;
    }

    public String read( ) throws IOException
    {
        WordExtractor extractor = new WordExtractor(new FileInputStream(fileName));
        String text = extractor.getText();
        return text;
    }
}
