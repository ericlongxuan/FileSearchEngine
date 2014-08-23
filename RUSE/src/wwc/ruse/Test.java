package wwc.ruse;

import java.util.List;

public class Test
{
    public static void main(String[] args) throws Throwable
    {
        args = new String[1];
        args[0] = "(small OR big) AND(NOT medium)";

        Tester.index("D:/major/三年级/软件设计/ruse-test-suite-1/test-files/", "C:/Downloads/");
        List list = Tester.search("C:/Downloads/",args[0]);
        for(int i=0;i<list.size();i++)
        {
            System.out.println(i+1+": "+list.get(i));
        }
    }
}
