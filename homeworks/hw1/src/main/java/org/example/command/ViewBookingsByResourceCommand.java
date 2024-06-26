package org.example.command;

import org.example.app.EfficientWork;

public class ViewBookingsByResourceCommand implements Command {

    private final EfficientWork app;

    public ViewBookingsByResourceCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.viewAllBookingsFilteredByResource();
    }
}
