/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.commands;

import ruse2.index.storage.IndexReader;

/**
 *
 * @author Administrator
 */
public class IndexLoadCommand implements Command{

    private String indexFile;

    public IndexLoadCommand(String indexFile)
    {
        this.indexFile = indexFile;
    }
    
    public void execute() throws Throwable
    {
       IndexReader.loadIndex(indexFile);
    }
}
