package org.example.command;

import org.example.app.EfficientWork;

public class CreateConferenceHallCommand implements Command {

    private final EfficientWork app;

    public CreateConferenceHallCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.createConferenceHall();
    }
}
