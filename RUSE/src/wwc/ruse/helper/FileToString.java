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
/**处理从文件中获取全文转成string的接口*/
public interface FileToString {
    /**待实现方法
     * 从文件中获取全文，转成string
     *@param fileName 表示要进行读取的文件路径
     */
    String read(String fileName) throws IOException;
}
