/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.index;

import java.io.File;
import java.io.IOException;
import ruse2.index.fileiterator.*;
import java.util.Iterator;
import ruse2.index.extractor.*;

/**
 *
 * @author Administrator
 */
public abstract class TemplateIndexMaker
{
    File file;
    Iterator<File> iterator;
    String fileContent;
    IndexObject index;
    
    public final void buildIndex(String dataDir) throws IOException
    {
        IndexHolder.reset();
        index = IndexHolder.getInstance();
        iterator = new FileIterator(new File(dataDir));
        while (iterator.hasNext())
        {            
            fetchFile();
            recordFileInformation();
            extractContent();
            recordInvertedIndex();
            hook();
        }

    }

    final void fetchFile()
    {
        file = iterator.next();
    }

    abstract void recordFileInformation();

    void extractContent() throws IOException
    {
        FileToString fileToStr = ContentExtractorFactory.createExtractor(file.toString());
        fileContent = fileToStr.read();
    }

    abstract void recordInvertedIndex();
    
    void hook()
    {
        
    }
}
