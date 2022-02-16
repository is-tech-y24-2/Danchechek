package com.mycompany.banks.interfaces;

public interface IAccount {
    void addMoney(float money);

    float getBalance();

    void withdrawMoney(float money);

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
