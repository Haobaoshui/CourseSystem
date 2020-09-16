package com.haobaoshui.course.utility.compress.zip;


import com.haobaoshui.course.model.ProjectFile;
import com.haobaoshui.course.utility.GUID;
import com.haobaoshui.course.utility.ProjectFileUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 解压和压缩zip文件
 */
public class ZipUtils {
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           压缩文件夹路径
     * @param out              压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         <p>
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */

    private static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            ZipUtils.compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */

    private static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {

        ZipOutputStream zos = null;

        try {
            zos = new ZipOutputStream(out);

            for (File srcFile : srcFiles) {
                byte[] buf = new byte[ZipUtils.BUFFER_SIZE];

                zos.putNextEntry(new ZipEntry(srcFile.getName()));

                int len;

                FileInputStream in = new FileInputStream(srcFile);

                while ((len = in.read(buf)) != -1) zos.write(buf, 0, len);

                zos.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);

        } finally {
            if (zos != null) try {
                zos.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         <p>
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)

     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception {

        if (sourceFile == null) return;


        byte[] buf = new byte[ZipUtils.BUFFER_SIZE];


        if (sourceFile.isFile()) {
            String originName = sourceFile.getName();

            if (originName != null) {

                // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字

                zos.putNextEntry(new ZipEntry(originName));

                // copy文件到zip输出流中

                int len;


                FileInputStream in = new FileInputStream(sourceFile);

                while ((len = in.read(buf)) != -1) zos.write(buf, 0, len);

                // Complete the entry
                zos.closeEntry();
                in.close();
            }

        } else {
            File[] listFiles = sourceFile.listFiles();

            // 判断是否需要保留原来的文件结构
            // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
            // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理

                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));

                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else for (File file : listFiles)
                if (KeepDirStructure)
                    ZipUtils.compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                else
                    ZipUtils.compress(file, zos, file.getName(), KeepDirStructure);
        }
    }

    /**
     * 得到zip文件的编码,如是中文返回“GB2312”,否则乱码
     *
     * @param srcfile
     * @param destDirFile
     * @return
     * @throws Exception
     */
    private static String getZipFileEncoding(File srcfile, File destDirFile) throws Exception {
        final String encoding = "UTF-8";


        //  String fileName = null;
        try {
            if (srcfile.isFile() && srcfile.getName().endsWith(".zip")) {
                //   fileName = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
                ZipFile zipFile = new ZipFile(srcfile, encoding);
                Enumeration<ZipArchiveEntry> en = zipFile.getEntries();
                ZipArchiveEntry ze;
                while (en.hasMoreElements()) {
                    ze = en.nextElement();

                    File f = new File(destDirFile, ze.getName());

                    InputStream is = zipFile.getInputStream(ze);
                    OutputStream os = new FileOutputStream(f);

                    is.close();
                    os.close();
                }
                zipFile.close();
            }
        } catch (Exception e) {
            return "GB2312";
        }

        return encoding;

    }

    /**
     * 解压zip
     *
     * @param srcfile
     * @param destDirFile
     * @return
     * @throws Exception
     */
    private static List<ProjectFile> unZip(File srcfile, File destDirFile) throws Exception {
        if (srcfile == null) throw new Exception(srcfile.getPath() + "所指文件不存在");
        if (!srcfile.isFile()) throw new Exception(srcfile.getPath() + "所指文件不是文件");

        if (destDirFile == null) throw new Exception(destDirFile.getPath() + "目标文件夹为空");
        if (!destDirFile.isDirectory()) throw new Exception(destDirFile.getPath() + "目标文件夹为空");


        //  String fileName = null;

        //  fileName = zipscrFilePath.substring(0, zipscrFilePath.lastIndexOf("."));
        ZipFile zipFile = new ZipFile(srcfile, ZipUtils.getZipFileEncoding(srcfile, destDirFile));
        Enumeration<ZipArchiveEntry> en = zipFile.getEntries();
        ZipArchiveEntry ze;

        List<ProjectFile> projectFileList = new ArrayList<>();
        while (en.hasMoreElements()) {
            ze = en.nextElement();

            ProjectFile projectFile = new ProjectFile();
            String fileext = ProjectFileUtils.getExtensionNameWithSeparator(ze.getName()) + "";
            String diskFileName = GUID.getGUID() + fileext;
            projectFile.setName(ze.getName());
            projectFile.setDiskFileName(diskFileName);

            File f = new File(destDirFile, diskFileName);
            // 创建完整路径
            if (ze.isDirectory()) {
                f.mkdirs();
                continue;
            } else f.getParentFile().mkdirs();

            InputStream is = zipFile.getInputStream(ze);
            OutputStream os = new FileOutputStream(f);
            IOUtils.copy(is, os, 4096);
            is.close();
            os.close();


            projectFileList.add(projectFile);
        }
        zipFile.close();
        return projectFileList;


    }

    /**
     * 使用方法1压缩文件
     *
     * @param srcDir
     * @param destFileName
     * @throws Exception
     */
    public static void toZip(String srcDir, String destFileName) throws Exception {
        if (srcDir == null || srcDir.length() == 0 || destFileName == null || destFileName.length() == 0) return;


        FileOutputStream fos = new FileOutputStream(new File(destFileName));
        ZipUtils.toZip(srcDir, fos, false);
    }

    /**
     * 使用方法2压缩多个文件
     *
     * @param srcFiles
     * @param destFileName
     * @throws Exception
     */
    public static void toZip(List<String> srcFiles, String destFileName) throws Exception {
        if (srcFiles == null || srcFiles.size() == 0 || destFileName == null || destFileName.length() == 0) return;


        FileOutputStream fos = new FileOutputStream(new File(destFileName));
        List<File> fileList = new ArrayList<>();
        for (String str : srcFiles) {
            File file = new File(str);
            fileList.add(file);
        }

        ZipUtils.toZip(fileList, fos);
    }

    /**
     * 解压缩
     *
     * @param srcFilePath 源文件
     * @param destDirPath 目标文件夹
     */
    private static List<ProjectFile> unZip(String srcFilePath, String destDirPath) throws Exception {
        if (srcFilePath == null) throw new Exception("文件名为空");
        if (!ProjectFileUtils.getExtensionName(srcFilePath).toLowerCase().equals("zip")) throw new Exception("文件扩展名错误");

        File srcFile = new File(srcFilePath);//获取当前压缩文件
        if (!srcFile.exists()) throw new Exception(srcFile.getPath() + "所指文件不存在");


        File parentFile = new File(destDirPath);
        parentFile.mkdirs();

        return ZipUtils.unZip(srcFile, parentFile);
    }

    /**
     * 解压缩，对文件解压到同一文件夹的同名目录下
     *
     * @param srcFilePath
     * @return
     * @throws Exception
     */
    public static List<ProjectFile> unZip(String srcFilePath) throws Exception {

        if (srcFilePath == null) throw new Exception("文件名为空");
        String destPath = ProjectFileUtils.getFileNameNoEx(srcFilePath);
        return ZipUtils.unZip(srcFilePath, destPath);
    }




}
