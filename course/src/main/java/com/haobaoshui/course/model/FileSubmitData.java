package com.haobaoshui.course.model;

import org.springframework.web.multipart.MultipartFile;

public class FileSubmitData {


    private int nodeID;
    private MultipartFile file;

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileName() {
        if (file != null)
            return file.getOriginalFilename();
        return null;
    }
}
