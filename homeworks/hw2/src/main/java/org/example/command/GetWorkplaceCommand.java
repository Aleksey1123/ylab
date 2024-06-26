package org.example.command;

import org.example.app.EfficientWork;

public class GetWorkplaceCommand implements Command {

    private final EfficientWork app;

    public GetWorkplaceCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.getWorkplace();
    }
}
