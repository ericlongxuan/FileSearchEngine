/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.query.interpret;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import ruse2.index.FileInformation;
import ruse2.index.IndexHolder;
import ruse2.query.executor.Expression;
import ruse2.query.executor.LiteralExpression;

/**
 *
 * @author Administrator
 */
public class FileSizeState extends State
{

    @Override
    public Expression interpret(String s)
    {
        HashSet<String> resultFileSet = new HashSet<String>();
        Iterator<Entry<String, FileInformation>> fileIterator = IndexHolder.getInstance().getFilesMap().entrySet().iterator();

        int indexOfCross = s.indexOf("-");
        long fromSize = Long.parseLong(s.substring(1, indexOfCross));
        long toSize = Long.parseLong(s.substring(indexOfCross + 1, s.length() - 1));


        if (s.charAt(0) == '[')
        {
            String currentFile;
            while (fileIterator.hasNext())
            {
                Entry<String, FileInformation> entry = fileIterator.next();
                currentFile = entry.getKey();
                long size = entry.getValue().size;
                if (size>=fromSize && size<= toSize)
                {
                    resultFileSet.add(currentFile);
                }
            }
        }
        else
        {
            String currentFile;
            while (fileIterator.hasNext())
            {
                Entry<String, FileInformation> entry = fileIterator.next();
                currentFile = entry.getKey();
                long size = entry.getValue().size;
                if (size>fromSize && size< toSize)
                {
                    resultFileSet.add(currentFile);
                }
            }
        }

        return new LiteralExpression(resultFileSet);
    }
}

    
