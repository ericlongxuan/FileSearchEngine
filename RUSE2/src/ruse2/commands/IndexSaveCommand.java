/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.commands;

import ruse2.index.storage.IndexSaver;

/**
 *
 * @author Administrator
 */
public class IndexSaveCommand implements Command{

    private String indexFile;

    public IndexSaveCommand(String indexFile)
    {
        this.indexFile = indexFile;
    }

    public void execute() throws Throwable
    {
       IndexSaver.saveIndex(indexFile);
    }
}
