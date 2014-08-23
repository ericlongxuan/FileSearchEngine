/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.index.extractor;

/**
 *
 * @author Administrator
 */
/** A factory to create concrete content extractors*/
public class ContentExtractorFactory {
    /** The method to create concrete content extractors*/
    public static FileToString createExtractor(String fileName)
    {
        FileToString fileToStr = null;
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
        fileType = fileType.toUpperCase();
        if(fileType.equals("TXT"))
            fileToStr = TXTToString.getInstance(fileName);
        else if(fileType.equals("DOC"))
            fileToStr = DOCToString.getInstance(fileName);
        return fileToStr;
    }
}
