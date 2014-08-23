/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2;
import cn.edu.nju.software.ruse.Tester;
import cn.edu.nju.software.ruse.Tester.Result;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Administrator
 */

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Tester.index(new File("D:/major/三年级/软件设计/ruse-test-suite-2/test-files"), 
                //new File("D:/major/三年级/软件设计/ruse-test-suite-2/"));
        String query = "\"wikipedia the free encyclopedia\" AND fileSize:[009000-017000]";
        List<Result> resultList = Tester.search(new File("D:/major/三年级/软件设计/ruse-test-suite-2/"),query , 0);
        Iterator<Result> it = resultList.iterator();
        while(it.hasNext())
        {
            Result result = it.next();
            System.out.print(result.fileName+",");
            System.out.print(result.modTime+",");
            System.out.println(result.fileSize+"|");
       }
    }

}
