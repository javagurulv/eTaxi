package ua.etaxi.core.commands.permissions;

import ua.etaxi.core.commands.DomainCommand;

/**
 * Created by Viktor on 27/11/2014.
 */
public class GetPermissionCommand
        implements DomainCommand<GetPermissionCommandResult> {

    private Long permissionId;


    public GetPermissionCommand(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

}
