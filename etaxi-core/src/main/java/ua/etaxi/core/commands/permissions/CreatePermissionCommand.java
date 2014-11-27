package ua.etaxi.core.commands.permissions;

import ua.etaxi.core.commands.DomainCommand;
import ua.etaxi.core.commands.VoidResult;

/**
 * Created by Viktor on 27/11/2014.
 */
public class CreatePermissionCommand implements DomainCommand<VoidResult> {

    private String permissionName;

    public CreatePermissionCommand(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }

}
