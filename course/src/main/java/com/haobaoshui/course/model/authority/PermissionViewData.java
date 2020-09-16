package com.haobaoshui.course.model.authority;

public class PermissionViewData {


    private Permission permission;
    private Resource resource;
    private PermissionOperator permissionoperator;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public PermissionOperator getPermissionoperator() {
        return permissionoperator;
    }

    public void setPermissionoperator(PermissionOperator permissionoperator) {
        this.permissionoperator = permissionoperator;
    }
}
