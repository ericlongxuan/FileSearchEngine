/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wwc.ruse.helper;

import java.io.IOException;

/**
 *
 * @author Administrator
 */
/**处理提取文件内容的接口*/
public interface FileToString {
    String read(String fileName) throws IOException;
}
