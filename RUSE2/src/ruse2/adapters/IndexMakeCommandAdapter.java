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
public class IndexMakeCommandAdapter implements Command{

    private Command indexMakeCommand;
    
    public IndexMakeCommandAdapter(File dataDir,TemplateIndexMaker indexMaker)
    {
        String strDataDir = dataDir.getPath();
        indexMakeCommand = new IndexMakeCommand(strDataDir, indexMaker);
    }
    
    public void execute() throws Throwable
    {
        indexMakeCommand.execute();
    }
    
}
