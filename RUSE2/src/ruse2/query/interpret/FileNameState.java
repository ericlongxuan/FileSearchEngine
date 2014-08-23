/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query.interpret;

import java.util.HashSet;
import java.util.Iterator;
import ruse2.index.IndexHolder;
import ruse2.query.executor.Expression;
import ruse2.query.executor.LiteralExpression;

/**
 *
 * @author Administrator
 */
public class FileNameState extends State{

    @Override
    public Expression interpret(String s)
    {
        HashSet<String> resultFileSet = new HashSet<String>();
        Iterator<String> fileIterator = IndexHolder.getInstance().getFilesMap().keySet().iterator();
        
        int indexOfCross = s.indexOf("-");
        String fromFile = s.substring(1, indexOfCross);
        String toFile = s.substring(indexOfCross+1, s.length()-1);
        
        if(s.charAt(0)=='[')
        {
            String currentFile;
            while(fileIterator.hasNext())
            {
                currentFile = fileIterator.next();
                if(currentFile.compareTo(fromFile)>=0&&currentFile.compareTo(toFile)<=0)
                    resultFileSet.add(currentFile);
            }
        }
        else
        {
            String currentFile;
            while(fileIterator.hasNext())
            {
                currentFile = fileIterator.next();
                if(currentFile.compareTo(fromFile)>0&&currentFile.compareTo(toFile)<0)
                    resultFileSet.add(currentFile);
            }
        }
        
        return new LiteralExpression(resultFileSet);
    }

}
