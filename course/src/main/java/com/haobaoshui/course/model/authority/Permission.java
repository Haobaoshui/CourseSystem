package com.haobaoshui.course.model.authority;

public class Permission {


    private String id;
    private String name;
    private String note;
    private String permissionOperatorId;
    private String resourceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPermissionOperatorId() {
        return permissionOperatorId;
    }

    public void setPermissionOperatorId(String permissionOperatorId) {
        this.permissionOperatorId = permissionOperatorId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


}
