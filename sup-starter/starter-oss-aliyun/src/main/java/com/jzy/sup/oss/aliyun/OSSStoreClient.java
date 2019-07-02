package com.jzy.sup.oss.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;

import java.io.IOException;
import java.io.InputStream;

/**
 * <b>功能：</b>文件服务器接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface OSSStoreClient {


    /**
    * @Author: 刘宏超
    * @param key 文件key
    * @param input 文件输入流
    * @Description:在默认的路径下上传文件
    * @Return String 文件存放的路径
    * @Date: Created in 15:34 2018/7/24 0024
    */
    public String putObject(String key, InputStream input) throws IOException;

    /**
    * @Author: 刘宏超
    * @param key 文件key
    * @param input 文件输入流
    * @param directory 文件的存放目录，只需要目录名称
    * @Description:在制定的路径下上传文件
    * @Date: Created in 18:51 2018/7/24 0024
    */
    public String putObjectAndDirectory(String key, InputStream input, String directory) throws IOException;


    /**
    * @Author: 刘宏超
    * @param key 文件key
    * @Description:在默认的路径下获取文件的流对象
    * @Date: Created in 18:30 2018/7/24 0024
    */
    public InputStream getObject(String key) throws OSSException, ClientException;

    /**
    * @Author: 刘宏超
    * @param key 文件key
    * @param fileName 要保持的文件路径和名称
    * @Description:在默认的路径下获取文件，并根据文件路径进行保存
    * @Date: Created in 18:30 2018/7/24 0024
    */
    public void getObject(String key, String fileName) throws OSSException, ClientException;


    /**
     * @Author: 刘宏超
     * @param key 文件key
     * @param directory 文件的存放目录，只需要目录名称
     * @Description:在指定的路径下获取文件的流对象
     * @Date: Created in 18:30 2018/7/24 0024
     */
    public InputStream getObjectAndDirectory(String key, String directory) throws OSSException, ClientException;

    /**
     * @Author: 刘宏超
     * @param key 文件key
     * @param fileName 要保持的文件路径和名称
     * @param directory 文件的存放目录，只需要目录名称
     * @Description:在指定的路径下获取文件，并根据文件路径进行保存
     * @Date: Created in 18:30 2018/7/24 0024
     */
    public void getObjectAndDirectory(String key, String fileName, String directory) throws OSSException, ClientException;





}
