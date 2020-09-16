package com.haobaoshui.course.model;

import java.util.List;

public class ExcelData {

    private String title;//标题
    private String subtitle;//副标题
    private List<String> rowHeads;// 表格表头
    private List<List<Object>> rowDatas;// 表格数据
    private String sheetName; // 页签名称


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getRowHeads() {
        return rowHeads;
    }

    public void setRowHeads(List<String> rowHeads) {
        this.rowHeads = rowHeads;
    }

    public List<List<Object>> getRowDatas() {
        return rowDatas;
    }

    public void setRowDatas(List<List<Object>> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
