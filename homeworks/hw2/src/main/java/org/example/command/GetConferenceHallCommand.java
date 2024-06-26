package org.example.command;

import org.example.app.EfficientWork;

public class GetConferenceHallCommand implements Command {

    private final EfficientWork app;

    public GetConferenceHallCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.getConferenceHall();
    }
}
