package ua.etaxi.core.commands;

/**
 * Created by Viktor on 27/11/2014.
 */
public class VoidResult implements DomainCommandResult {

    public static final VoidResult INSTANCE = new VoidResult();

    private VoidResult() {

    }

}
