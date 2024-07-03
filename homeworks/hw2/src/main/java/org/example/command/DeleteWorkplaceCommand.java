package org.example.command;

import org.example.app.EfficientWork;

public class DeleteWorkplaceCommand implements Command {

    private final EfficientWork app;

    public DeleteWorkplaceCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.deleteWorkplace();
    }
}
