package wwc.ruse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import wwc.ruse.helper.*;

public class Test
{

    static FileToString fileToStr;

    private static void usage()
    {
        System.err.println("Usage: TestApp <algorithm> <input file> [-o <output file>]");
    }

    public static void main(String[] args) throws Throwable
    {
        args = new String[4];
        args[0] = "english";
        args[1] = ".";
        args[2] = "-o";
        args[3] = "out3.txt";


        
        HashMap indexMap = new HashMap(1000);
        String currentStem;
        String fileName;

        File file = new File(args[1]);
        if (!(file.exists() && file.isDirectory()))
        {
            System.err.println("文件夹不存在");
            return;
        }
        String[] list = (file).list();
        for (int i = 0; i < list.length; i++)
        {
            fileName = list[i];
            String fileType = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
            fileType = fileType.toUpperCase();
            if (!(fileType.equals("TXT") || fileType.equals("DOC")))
            {
                continue;
            }
            Class filePro = Class.forName("wwc.ruse.helper." + fileType + "ToString");
            fileToStr = (FileToString) filePro.newInstance();
            ArrayList stemsInOneFile = MyStemmer.stringToStems(fileToStr.read(fileName), args[0]);
            for (int j = 0; j < stemsInOneFile.size(); j++)
            {
                currentStem = stemsInOneFile.get(j).toString();
                if (!indexMap.containsKey(currentStem))
                {
                    TreeSet singleStemTreeSet = new TreeSet();
                    singleStemTreeSet.add(fileName);
                    indexMap.put(currentStem, singleStemTreeSet);
                } else
                {
                    ((TreeSet) indexMap.get(currentStem)).add(fileName);
                }
            }
        }

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[3])));
        Iterator it = indexMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            out.print(key + "-> ");
            Object value = entry.getValue();
            Iterator it2 = ((TreeSet)value).iterator();
            while(it2.hasNext())
                out.print(it2.next().toString() + " ");
            out.print('\n');
        }
        out.close();
        
        FileToString fts = new wwc.ruse.helper.TXTToString();
        MyStemmer.write("out4.txt", MyStemmer.stringToStems(fts.read("in.txt"),args[0]));
    }
    
}
