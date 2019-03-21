package com.kim.common.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 压缩或解压zip：
 * 由于直接使用java.util.zip工具包下的类，会出现中文乱码问题，所以使用ant.jar中的org.apache.tools.zip下的工具类
 *
 * @author liubo
 */
public class ZipUtils {
    private final static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    private static final String charset = "utf-8";

    /**
     * 压缩文件或路径
     *
     * @param zip      压缩的目的地址
     * @param srcFilePaths 压缩的源文件
     * @param relativePath 在zip中的相对路径
     */
    public static void zipFile(String zip, List<String> srcFilePaths, String relativePath) {
        OutputStream os = null;
        ZipOutputStream zipOut = null;
        try {
            if(zip.endsWith(".zip") || zip.endsWith(".ZIP")) {
                os = new FileOutputStream(new File(zip));
                zipOut = new ZipOutputStream(os);
                zipOut.setEncoding(charset);
                for (String filePath : srcFilePaths) {
                    handlerFile(zip, zipOut, new File(filePath), relativePath);
                }
            } else {
                logger.info("target file[{}] is not .zip type file", zip);
                throw new RuntimeException("Target file is not .zip type file");
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(zipOut);
            close(os);
        }
    }

    public static void close(Closeable c){
        if(c != null){
            try {
                c.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * @param zip     压缩的目的地址
     * @param zipOut
     * @param srcFile 被压缩的文件信息
     * @param path    在zip中的相对路径
     * @throws IOException
     */
    private static void handlerFile(String zip, ZipOutputStream zipOut, File srcFile, String path) throws IOException {
        logger.debug("start compress file [{}]", srcFile.getName());

        if(!srcFile.exists()){
            logger.error("文件不存在，srcFile:{}", srcFile);
            return ;
        }
        if (StringUtil.isNotBlank(path) && !path.endsWith(File.separator)) {
            path += File.separator;
        }
        if (StringUtil.equals(srcFile.getPath(), zip)) {
            logger.debug("要压缩的文件和目标文件相同!");
            return ;
        }
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            if (files.length == 0) {

                try {
                    zipOut.putNextEntry(new ZipEntry(path + srcFile.getName() + File.separator));
                    zipOut.closeEntry();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw e;
                }
            } else {
                for (File file : files) {
                    handlerFile(zip, zipOut, file, path + srcFile.getName());
                }
            }
        } else {
            InputStream in = null;
            try{
                in = new FileInputStream(srcFile);
                zipOut.putNextEntry(new ZipEntry(path + srcFile.getName()));
                int len = 0;
                byte[] byteArr = new byte[1024 * 10];
                while ((len = in.read(byteArr)) > 0) {
                    zipOut.write(byteArr, 0, len);
                }
                zipOut.closeEntry();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw e;
            } finally {
                close(in);
            }
        }
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
     *
     * @param zipPath 待解压缩的ZIP文件名
     * @param descDir 目标目录
     */
    public static List<File> upzipFile(String zipPath, String descDir) {
        return upzipFile(new File(zipPath), descDir);
    }

    /**
     * 对.zip文件进行解压缩
     *
     * @param zipFile 解压缩文件
     * @param descDir 压缩的目标地址，如：D:\\测试 或 /mnt/d/测试
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<File> upzipFile(File zipFile, String descDir) {
        List<File> _list = new ArrayList<File>();
        try {
            ZipFile _zipFile = new ZipFile(zipFile, "GBK");
            for (Enumeration entries = _zipFile.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File _file = new File(descDir + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    _file.mkdirs();
                } else {
                    File _parent = _file.getParentFile();
                    if (!_parent.exists()) {
                        _parent.mkdirs();
                    }
                    InputStream _in = _zipFile.getInputStream(entry);
                    OutputStream _out = new FileOutputStream(_file);
                    int len = 0;
                    byte[] byteArr = new byte[1024 * 10];
                    while ((len = _in.read(byteArr)) > 0) {
                        _out.write(byteArr, 0, len);
                    }
                    _in.close();
                    _out.flush();
                    _out.close();
                    _list.add(_file);
                }
            }
        } catch (IOException e) {
        }
        return _list;
    }

    /**
     * 对临时生成的文件夹和文件夹下的文件进行删除
     */
    public static void deletefile(String delpath) {
        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + File.separator + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + File.separator + filelist[i]);
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("D:/voiceAudios\\/RecWavXH2/20171011/95009//56092_20171011102334.wav");
        list.add("D:/voiceAudios\\/RecWavSZ2/20171012/92046//56011_20171012160105.wav");
        list.add("D:/voiceAudios\\//RecWavWH5//20171009//97153///58383_20171009090939.wav");
        zipFile("D:/aaaaa.zip", list,"audios");

    }

}