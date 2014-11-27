package ua.etaxi.core;

import ua.etaxi.core.commands.DomainCommand;
import ua.etaxi.core.commands.DomainCommandResult;

/**
 * Created by Viktor on 27/11/2014.
 */
public interface CommandExecutor {

    <T extends DomainCommandResult> T execute(DomainCommand<T> domainCommand);

}
