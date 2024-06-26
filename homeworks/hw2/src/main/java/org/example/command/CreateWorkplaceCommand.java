package org.example.command;

import org.example.app.EfficientWork;

public class CreateWorkplaceCommand implements Command {

    private final EfficientWork app;

    public CreateWorkplaceCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
       app.createWorkplace();
    }
}
