/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.commands;

import ruse2.index.*;

/**
 *
 * @author Administrator
 */
public class IndexMakeCommand implements Command
{

    private TemplateIndexMaker indexMaker;
    private String dataDir;

    public IndexMakeCommand(String dataDir,TemplateIndexMaker indexMaker)
    {
        this.indexMaker = indexMaker;
        this.dataDir = dataDir;
    }

    public void execute() throws Throwable
    {
       indexMaker.buildIndex(dataDir);
    }
}
