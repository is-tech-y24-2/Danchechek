package ru.itmo.bank.solutions;

import ru.itmo.bank.interfaces.Account;
import ru.itmo.bank.tools.BanksException;

public class DepositAccountImpl implements Account {

    private String _name = "deposit";
    private int _period;
    private int _id;
    private float _percentage;
    private float _minMoney;
    private float _maxMoney;
    private int _days = 0;
    private float _capital;

    public DepositAccountImpl(float minMoney, float maxMoney, int period, float capital, int id, float percentage) {
        _capital = capital;
        _id = id;
        _percentage = percentage;
        _minMoney = minMoney;
        _maxMoney = maxMoney;
        _period = period;
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
        if (_days < _period) {
            throw new BanksException("Period is not ended");
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
        return _days >= _period;
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
        return 0;
    }

    @Override
    public int getPeriod() {
        return _period;
    }

    @Override
    public float getPercentage() {
        return _percentage;
    }

    private void addDay() {
        _days += 1;
        if (_days % _period == 0) {
            _capital += _capital * _percentage;
        }
    }
}
