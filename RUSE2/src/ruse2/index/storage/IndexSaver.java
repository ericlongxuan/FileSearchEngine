/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.index.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import ruse2.index.IndexHolder;

/**
 *
 * @author Administrator
 */
public class IndexSaver {

    public static void saveIndex(String outputFile) throws FileNotFoundException, IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile));
        out.writeObject(IndexHolder.getInstance());
        out.flush();
        out.close();
    }
}
