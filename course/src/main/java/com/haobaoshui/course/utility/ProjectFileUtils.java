package com.haobaoshui.course.utility;



import com.haobaoshui.course.configure.CommonConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectFileUtils {

    /*
     * 读取指定路径下的文件名和目录名
     */
    public static List<String> getFileList(String strDirPath) {
        File file = new File(strDirPath);

        List<String> filenameList = new ArrayList<>();

        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                String fileName = fileList[i].getName();
                filenameList.add(fileName);
            }

            if (fileList[i].isDirectory()) {
                // String fileName = fileList[i].getName();
                // System.out.println("目录：" + fileName);
            }
        }

        return filenameList;
    }

    public static String getProjectPath() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")
                + CommonConstant.PROJECT_UPLOAD_FILES_PATH)).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        //  if (path.indexOf(":") != 1) path = File.separator + path;
        return path;
    }

    public static boolean isDir(String strFileNamePath) {

        File file = new File(strFileNamePath);

        if (file.isDirectory()) return true;
        return false;

    }

    /**
     * 获得文件后缀,不包括"."
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) return filename.substring(dot + 1);

        }
        return null;
    }

    /**
     * 获得文件后缀,包括"."
     *
     * @param filename
     * @return
     */
    public static String getExtensionNameWithSeparator(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) return filename.substring(dot);

        }
        return null;
    }

    /**
     * 获得文件名，不包含文件后缀
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) return filename.substring(0, dot);
        }
        return null;
    }

    /**
     * 创建父目录
     *
     * <pre>
     * 当父目录不存在时，创建目录！
     * </pre>
     *
     * @param dirFile
     */
    public static void mkDir(File dirFile) {
        File parentFile = dirFile.getParentFile();
        if (!parentFile.exists()) {
            // 递归寻找上级目录
            ProjectFileUtils.mkDir(parentFile);
            parentFile.mkdir();
        }
    }

    /**
     * 创建父目录
     *
     * <pre>
     * 当父目录不存在时，创建目录！
     * </pre>
     *
     * @param dirPath
     */
    public static void mkDir(String dirPath) {
        File parentFile = new File(dirPath);
        parentFile.mkdirs();
    }
}
