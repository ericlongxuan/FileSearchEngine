/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.index;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import ruse2.index.extractor.*;

/**
 *
 * @author Administrator
 */
public class IndexMaker extends TemplateIndexMaker
{
    private long wordPosition;

    @Override
    void recordFileInformation()
    {
        //get the information of the file
        long size = file.length();

        String fileName = file.getName();

        long modTime = file.lastModified();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String modTimeStr = dataFormat.format(new Date(modTime));

        //create an instance of FileInformation
        FileInformation fileInfo = new FileInformation(size, fileName, modTimeStr);

        //record <key, fileInfo> into filesMap
        index.getFilesMap().put(file.toString(), fileInfo);
    }

    @Override
    void recordInvertedIndex()
    {
        Scanner scanner = new Scanner(fileContent);
        scanner.useDelimiter("[^a-zA-Z0-9]");
        while (scanner.hasNext())
        {
            String currentWord = scanner.next();
            if (!currentWord.equals(""))
            {
                currentWord = currentWord.toLowerCase();
                //the word is contained in index
                if(index.getIndexMap().containsKey(currentWord))
                {
                    //the file is contained in the word's index
                    if(index.getIndexMap().get(currentWord).containsKey(file.toString()))
                    {
                        index.getIndexMap().get(currentWord).get(file.toString()).add(wordPosition);
                    }
                    //otherwise
                    else
                    {
                        ArrayList<Long> positionArray = new ArrayList<Long>();
                        positionArray.add(wordPosition);
                        index.getIndexMap().get(currentWord).put(file.toString(), positionArray);
                    }
                }
                //the word is not contained in index
                else
                {
                    HashMap<String,ArrayList<Long>> fileToPosition = new HashMap<String,ArrayList<Long>>(); 
                    ArrayList<Long> positionArray = new ArrayList<Long>();
                    positionArray.add(wordPosition);
                    fileToPosition.put(file.toString(), positionArray);
                    index.getIndexMap().put(currentWord, fileToPosition);         
                }              
                                
                wordPosition++;               
                
            }
        }

    }
    
    @Override
    void hook()
    {
        wordPosition=1;
    }
}
