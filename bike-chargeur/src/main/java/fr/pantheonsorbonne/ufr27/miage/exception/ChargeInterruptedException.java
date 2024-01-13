package fr.pantheonsorbonne.ufr27.miage.exception;

public class ChargeInterruptedException extends InterruptedException {
    private final String msg;

    public ChargeInterruptedException(String message) {
        super(message);
        this.msg = message;
    }

    public void printMessage() {
        System.out.println(msg);
    }
}

