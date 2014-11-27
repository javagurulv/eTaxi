package ua.etaxi.core.handlers.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.etaxi.core.commands.DomainCommand;
import ua.etaxi.core.commands.DomainCommandResult;
import ua.etaxi.core.commands.VoidResult;
import ua.etaxi.core.commands.permissions.CreatePermissionCommand;
import ua.etaxi.core.database.EntityRepository;
import ua.etaxi.core.domain.Permission;
import ua.etaxi.core.handlers.DomainCommandHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Viktor on 27/11/2014.
 */
@Component
public class CreatePermissionCommandHandler
        implements DomainCommandHandler<CreatePermissionCommand, VoidResult> {

    @Autowired
    private EntityRepository entityRepository;


    @Override
    public VoidResult execute(CreatePermissionCommand command) {
        validateCommand(command);

        Permission permission = new Permission();
        permission.setPermissionName(command.getPermissionName());

        entityRepository.create(permission);

        return VoidResult.INSTANCE;
    }

    private void validateCommand(CreatePermissionCommand command) {
        checkNotNull(command, "CreatePermissionCommand must not be null");
        checkNotNull(command.getPermissionName(), "Permission Name must not be null");
    }

    @Override
    public Class getCommandType() {
        return null;
    }

}
