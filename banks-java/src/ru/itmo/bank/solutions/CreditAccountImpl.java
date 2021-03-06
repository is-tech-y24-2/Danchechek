package ru.itmo.bank.solutions;

import ru.itmo.bank.interfaces.Account;
import ru.itmo.bank.tools.BanksException;

public class CreditAccountImpl implements Account {

    private String _name = "Credit";
    private int _days = 1;
    private float _monthlyMinusSum;
    private float _capital;
    private final float _freeSum;
    private float _minMoney;
    private float _maxMoney;
    private int _id;

    public CreditAccountImpl(float capital, float minMoney, float maxMoney, int id, float monthlyMinusSum) {
        _capital = capital;
        _freeSum = _capital;
        _minMoney = minMoney;
        _maxMoney = maxMoney;
        _id = id;

        _monthlyMinusSum = monthlyMinusSum;
    }

    @Override
    public void addMoney(float money) {
        _capital += money;
    }

    @Override
    public float getBalance() {
        return _capital;
    }

    @Override
    public void withdrawMoney(float money) throws BanksException {
        if (_capital < _minMoney) {
            throw new BanksException("U can't do it couse min limit");
        }

        _capital -= money;
    }

    @Override
    public void addDays(int days) {
        for (int i = 0; i < days; i++) {
            addDay();
        }
    }

    @Override
    public Boolean canCloseAccount() {
        return !(_capital < _freeSum);
    }

    @Override
    public int getId() {
        return _id;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public float maxMoney() {
        return _maxMoney;
    }

    @Override
    public float minMoney() {
        return _minMoney;
    }

    @Override
    public float getMinusSum() {
        return _monthlyMinusSum;
    }

    @Override
    public int getPeriod() {
        return 0;
    }

    @Override
    public float getPercentage() {
        return 0;
    }

    private void addDay() {
        _days += 1;
        if (_days % 30 == 0) {
            _capital -= _monthlyMinusSum;
        }
    }
}
