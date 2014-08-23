/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wwc.ruse.helper;

/**
 *
 * @author Administrator
 */
/** A factory to create concrete content extractors*/
public class ContentExtractorFactory {
    /** The method to create concrete content extractors*/
    public static FileToString createExtractor(String fileType)
    {
        FileToString fileToStr = null;
        if(fileType.equals("TXT"))
            fileToStr = new TXTToString();
        else if(fileType.equals("DOC"))
            fileToStr = new DOCToString();
        return fileToStr;
    }
}
