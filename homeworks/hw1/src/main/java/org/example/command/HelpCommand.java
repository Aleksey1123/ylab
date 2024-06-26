package org.example.command;

import org.example.app.EfficientWork;

public class HelpCommand implements Command {

    private final EfficientWork app;

    public HelpCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.help();
    }
}
