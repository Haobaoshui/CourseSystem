package com.haobaoshui.course.model.courseteachingclass;


public class CourseTeachingClassHomeworkSubmitFile {


    private String id;
    private String courseTeachingClassHomeworkSubmitBaseinfoId;
    private String filename;
    private String filepath;
    private int fileNodeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getCourseTeachingClassHomeworkSubmitBaseinfoId() {
        return courseTeachingClassHomeworkSubmitBaseinfoId;
    }

    public void setCourseTeachingClassHomeworkSubmitBaseinfoId(String courseTeachingClassHomeworkSubmitBaseinfoId) {
        this.courseTeachingClassHomeworkSubmitBaseinfoId = courseTeachingClassHomeworkSubmitBaseinfoId;
    }

    public int getFileNodeId() {
        return fileNodeId;
    }

    public void setFileNodeId(int fileNodeId) {
        this.fileNodeId = fileNodeId;
    }
}
