/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.adapters;

import java.io.File;
import ruse2.commands.*;
import ruse2.index.*;

/**
 *
 * @author Administrator
 */
public class IndexSaveCommandAdapter implements Command{

    private Command indexMakeCommand;
    
    public IndexSaveCommandAdapter(File indexDir,TemplateIndexMaker indexMaker)
    {
        String strIndexDir = indexDir.getPath();
        indexMakeCommand = new IndexMakeCommand(strIndexDir,indexMaker);
    }
    
    public void execute() throws Throwable
    {
        indexMakeCommand.execute();
    }
    
}
