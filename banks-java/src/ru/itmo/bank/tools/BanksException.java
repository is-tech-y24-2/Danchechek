package ru.itmo.bank.tools;

public class BanksException extends Exception {
    public BanksException()
    {
    }

    public BanksException(String message) {
        super(message);
    }

}
