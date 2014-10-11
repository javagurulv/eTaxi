package ua.etaxi.core.domain.builders;


import ua.etaxi.core.domain.Permission;

/**
 * Created by Viktor on 11/10/2014.
 */
public class PermissionBuilder {

    private Long permissionId;
    private String permissionName;


    public static PermissionBuilder createPermission() {
        return new PermissionBuilder();
    }

    private PermissionBuilder() { }

    public Permission build() {
        Permission permission = new Permission();
        permission.setPermissionId(permissionId);
        permission.setPermissionName(permissionName);
        return permission;
    }

    public PermissionBuilder withId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public PermissionBuilder withName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }

}
