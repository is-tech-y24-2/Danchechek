package com.mycompany.banks.solutions;

import com.mycompany.banks.interfaces.IAccount;
import com.mycompany.banks.models.Bank;
import com.mycompany.banks.models.Client;

public class DebitAccount implements IAccount {
    private final Client _client;
    private final Bank _bank;
    private int _days = 1;
    private float _monthlyPercentage;
    private float _capital;
    private final int _id;

    public DebitAccount(float capital, int id, float monthlyPercentage, Bank bank, Client client)
    {
        if (monthlyPercentage <= 0 || monthlyPercentage > 1)
        {
            throw new RuntimeException("Incorrect monthly percentage");
        }

        _capital = capital;
        _id = id;
        _monthlyPercentage = monthlyPercentage;
        _client = client;
        _bank = bank;
    }

    public void setMonthlyPercentage(float monthlyPercentage)
    {
        _monthlyPercentage = monthlyPercentage;
    }

    @Override
    public void addMoney(float money) {
        _capital += money;
    }

    @Override
    public void withdrawMoney(float money) {
        if (money > _bank.getLimit() && _bank.getLimit() != -1 && !_client.trustStatus())
        {
            throw new RuntimeException(
                    "You are not trust client, and u can't do it, please set passport and address");
        }

        _capital -= money;
    }

    @Override
    public void addDays(int days) {
        for (int i = 0; i < days; i++)
        {
            addDay();
        }
    }

    @Override
    public Boolean canCloseAccount() {
        return true;
    }

    @Override
    public float getBalance() {
        return _capital;
    }

    @Override
    public int getId() {
        return _id;
    }

    @Override
    public String getName() {
        return "debit";
    }

    @Override
    public float maxMoney() {
        return 0;
    }

    @Override
    public float minMoney() {
        return 0;
    }

    @Override
    public float getMinusSum() {
        return 0;
    }

    @Override
    public int getPeriod() {
        return 0;
    }

    @Override
    public float getPercentage() {
        return _monthlyPercentage;
    }

    private void addDay()
    {
        _days += 1;
        if (_days % 30 == 0)
        {
            addMoney(_capital * _monthlyPercentage);
        }
    }
}
