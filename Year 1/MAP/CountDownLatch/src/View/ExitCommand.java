package View;

import Controller.Controller;
import Model.Exceptions.ControllerException;
import Model.Exceptions.EmptyExecutionStackException;
import Model.Exceptions.RepositoryException;

public class ExitCommand extends Command {

    public ExitCommand(String key, String desc){
        super(key, desc);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}

