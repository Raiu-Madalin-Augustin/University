package View;

import Controller.Controller;
import Model.Exceptions.ControllerException;
import Model.Exceptions.EmptyExecutionStackException;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.UndeclaredVariableException;
import View.Command;

public class RunExample extends Command {
    private Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStepsExecution(true);

        } catch ( ControllerException e) {
            System.out.println(e.getMessage());
        }
    }
}