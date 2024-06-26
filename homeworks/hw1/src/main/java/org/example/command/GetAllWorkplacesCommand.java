package org.example.command;

import org.example.app.EfficientWork;

public class GetAllWorkplacesCommand implements Command {

    private final EfficientWork app;

    public GetAllWorkplacesCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.getAllWorkPlaces();
    }
}
