/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.index.fileiterator;

/**
 *
 * @author Administrator
 */
import java.io.File;
import java.util.Iterator;
import java.util.Stack;

public class FileIterator implements Iterator<File>
{

    private static class IteratorState
    {

        final IteratorState parent;
        final File[] fileList;
        int listIndex;

        public IteratorState(IteratorState parent, File dir)
        {
            this.parent = parent;
            fileList = dir.listFiles();
            listIndex = 0;
        }
    }
    private File currentFile;
    private IteratorState state;
    private Stack<File> stack = new Stack<File>();

    public FileIterator(File file)
    {
        if (file.isDirectory())
        {
            state = new IteratorState(null, file);
        }
        else
        {
            this.currentFile = file;
            state = null;
        }
    }

    public File next()
    {
        return currentFile;
    }

    public boolean hasNext()
    {
        moveToNext();
        return currentFile!=null;
    }

    //Move the iterator to the next node. The currentFile will be changed meanwhile, whose content is exactly the next node.
    private void moveToNext()
    {
        currentFile = null;
        if (this.state == null)
        {
            return;
        }
        for (;;)
        {
            if (state.listIndex >= state.fileList.length)
            {
                state = state.parent;
                if (state == null)
                {
                    return;
                }
                state.listIndex++;
                continue;
            }

            File file = state.fileList[state.listIndex];

            if (file.isDirectory())
            {
                state = new IteratorState(state, file);
                continue;
            }
            
            currentFile = file;
            state.listIndex++;
            break;
        }
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
