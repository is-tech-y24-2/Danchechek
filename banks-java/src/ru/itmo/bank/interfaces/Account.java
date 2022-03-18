package ru.itmo.bank.interfaces;

import ru.itmo.bank.tools.BanksException;

public interface Account {
    void addMoney(float money);

    float getBalance();

    void withdrawMoney(float money) throws BanksException;

    void addDays(int days);

    Boolean canCloseAccount();

    int getId();

    String getName();

    float maxMoney();

    float minMoney();

    float getMinusSum();

    int getPeriod();

    float getPercentage();
}
