/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.index.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import ruse2.index.IndexHolder;
import ruse2.index.IndexObject;

/**
 *
 * @author Administrator
 */
public class IndexReader {
    public static void loadIndex(String indexFile) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(indexFile));
        IndexObject index = (IndexObject) in.readObject();
        in.close();
        IndexHolder.setIndex(index);
    }
}
