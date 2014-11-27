package ua.etaxi.core.commands.permissions;

import ua.etaxi.core.commands.DomainCommandResult;
import ua.etaxi.core.domain.Permission;

/**
 * Created by Viktor on 27/11/2014.
 */
public class GetPermissionCommandResult implements DomainCommandResult {

    private Permission permission;


    public GetPermissionCommandResult(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }

}
