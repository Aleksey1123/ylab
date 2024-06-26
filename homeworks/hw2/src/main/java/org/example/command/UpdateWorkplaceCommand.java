package org.example.command;

import org.example.app.EfficientWork;

public class UpdateWorkplaceCommand implements Command {

    private final EfficientWork app;

    public UpdateWorkplaceCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.updateWorkplace();
    }
}
