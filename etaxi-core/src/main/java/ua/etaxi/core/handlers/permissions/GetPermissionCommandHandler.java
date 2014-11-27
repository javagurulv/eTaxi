package ua.etaxi.core.handlers.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.etaxi.core.commands.permissions.GetPermissionCommand;
import ua.etaxi.core.commands.permissions.GetPermissionCommandResult;
import ua.etaxi.core.database.EntityRepository;
import ua.etaxi.core.domain.Permission;
import ua.etaxi.core.handlers.DomainCommandHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Viktor on 27/11/2014.
 */
@Component
public class GetPermissionCommandHandler
        implements DomainCommandHandler<GetPermissionCommand, GetPermissionCommandResult> {

    @Autowired
    private EntityRepository entityRepository;


    @Override
    public GetPermissionCommandResult execute(GetPermissionCommand command) {
        validateCommand(command);

        Permission permission = entityRepository.getById(Permission.class, command.getPermissionId());

        return new GetPermissionCommandResult(permission);
    }

    private void validateCommand(GetPermissionCommand command) {
        checkNotNull(command, "GetPermissionCommand must not be null");
        checkNotNull(command.getPermissionId(), "Permission Id must not be null");
    }

    @Override
    public Class getCommandType() {
        return GetPermissionCommand.class;
    }

}
