package org.example.command;

import org.example.app.EfficientWork;

public class BookResourceCommand implements Command {

    private final EfficientWork app;

    public BookResourceCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.bookResource();
    }
}
