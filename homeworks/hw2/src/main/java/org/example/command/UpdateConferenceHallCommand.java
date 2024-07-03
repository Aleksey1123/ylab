package org.example.command;

import org.example.app.EfficientWork;

public class UpdateConferenceHallCommand implements Command {

    private final EfficientWork app;

    public UpdateConferenceHallCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.updateConferenceHall();
    }
}
