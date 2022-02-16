package com.mycompany.banks.models;

public class Transaction {
    private final int _idFirstClient;
    private final int _idSecondClient;
    private final float _money;

    public Transaction(int idFirstClient, int idSecondClient, float money)
    {
        _idFirstClient = idFirstClient;
        _idSecondClient = idSecondClient;
        _money = money;
    }

    public float getMoneySum()
    {
        return _money;
    }

    public int getFirstClientId()
    {
        return _idFirstClient;
    }

    public int getSecondClientId()
    {
        return _idSecondClient;
    }
}
