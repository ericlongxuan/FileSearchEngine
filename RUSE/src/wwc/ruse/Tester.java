package wwc.ruse;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import wwc.ruse.helper.*;
import wwc.ruse.indexer.IndexMaker;
import wwc.ruse.query.QueryExcute;

/**
 * A facade for testing your RUSE. Change the implementation of the
 * two methods to adapt your version of RUSE.
 * 
 */
public class Tester
{

    /**
     * Entrance to the indexing functionality.
     * 
     * @param dataDir
     *            the location of the files
     * @param indexDir
     *            the location to place your index data
     */
    public static void index(String dataDir, String indexDir)
    {
        try
        {
            IndexMaker indexMaker = new IndexMaker();
            indexMaker.makeIndex(dataDir, indexDir+"index.txt", "english");
        }
        catch (Throwable ex)
        {
            System.out.println(ex + "  A error occured when making index");
        }
    }

    /**
     * Entrance to the search functionality.
     * 
     * @param indexDir
     *            the location of the index
     * @param query
     *            the query
     * @return a list containing the files names (excluding the path) that match
     *         the query
     */
    public static List<String> search(String indexDir, String query)
    {
        // add your code here
        QueryExcute queryExcute = new QueryExcute();
        TreeSet result;
        result = (TreeSet) queryExcute.excuteQuery(query, indexDir+"index.txt", "english");
        List list = new ArrayList();
        list.addAll(result);
        return list;
    }
}
