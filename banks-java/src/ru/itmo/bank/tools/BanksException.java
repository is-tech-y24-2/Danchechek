package ru.itmo.bank.tools;

public class BanksException extends RuntimeException{
    public BanksException()
    {
    }

    public BanksException(String message) {
        super(message);
    }

}
