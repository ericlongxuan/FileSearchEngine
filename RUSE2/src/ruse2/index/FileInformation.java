/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.index;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class FileInformation implements Serializable
{
    public long size;
    public String name;
    public String modTime;

    public FileInformation(long size, String name, String modTime)
    {
        this.size = size;
        this.name = name;
        this.modTime = modTime;
    }
}
