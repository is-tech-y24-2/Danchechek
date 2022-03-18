package ru.itmo.bank.models;

public class Transaction {
    private int _idFirstClient;
    private int _idSecondClient;
    private float _money;

    public Transaction(int idFirstClient, int idSecondClient, float money) {
        _idFirstClient = idFirstClient;
        _idSecondClient = idSecondClient;
        _money = money;
    }

    public float getMoneySum() {
        return _money;
    }

    public int getFirstClientId() {
        return _idFirstClient;
    }

    public int getSecondClientId() {
        return _idSecondClient;
    }
}
