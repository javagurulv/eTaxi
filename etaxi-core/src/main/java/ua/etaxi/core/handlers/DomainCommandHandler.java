package ua.etaxi.core.handlers;

import ua.etaxi.core.commands.DomainCommand;
import ua.etaxi.core.commands.DomainCommandResult;

/**
 * Created by Viktor on 27/11/2014.
 */
public interface DomainCommandHandler<C extends DomainCommand, R extends DomainCommandResult> {

    R execute(C command);

    Class getCommandType();

}
