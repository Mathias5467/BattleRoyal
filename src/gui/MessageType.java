package gui;

public enum MessageType {
    EXIT("Do you want to exit?", "Yes", "No"),
    WIN("You won!", "Ok", "Exit"),
    LOST("You lost!", "Ok", "Exit");

    private String message;
    private String ok;
    private String cancel;
    MessageType(String message, String ok, String cancel) {
        this.message = message;
        this.ok = ok;
        this.cancel = cancel;
    }

    public String getMessage() {
        return this.message;
    }

    public String getOk() {
        return this.ok;
    }

    public String getCancel() {
        return this.cancel;
    }
}
