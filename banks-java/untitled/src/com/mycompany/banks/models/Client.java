package com.mycompany.banks.models;

import com.mycompany.banks.interfaces.IAccount;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final int _id;
    private final String _name;
    private final String _secondName;
    private String _address = null;
    private int _numberOfPassport = 0;
    private String _password = null;
    private Bank _bank = null;
    private List<IAccount> _accounts;
    private IAccount _lastAccount;
    private final List<String> _messages = new ArrayList();
    private final String _lastMessage = null;

    public Client(String name, String secondName, int id)
    {
        _id = id;
        _name = name;
        _secondName = secondName;
    }

    public Client(String name, String secondName, String address, int numberOfPassport, int id)
    {
        _accounts = new ArrayList<IAccount>();
        _name = name;
        _secondName = secondName;
        if (address != null)
        {
            _address = address;
        }

        if (numberOfPassport != 0)
        {
            _numberOfPassport = numberOfPassport;
        }

        _id = id;
    }

    public String getAddress() {
        return _address;
    }

    public void addAddressName(String address)
    {
        _address = address;
    }

    public int getNumberOfPassport() {
        return _numberOfPassport;
    }

    public void addNumberOfPassport(int _numberOfPassport) {
        if(String.valueOf(_numberOfPassport).length() != 10)
        {
            throw new RuntimeException("Неверно задан пароль");
        }

        this._numberOfPassport = _numberOfPassport;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String _password) {
        if(_password.length() != 8)
        {
            throw new RuntimeException("Неверно задан пароль");
        }

        this._password = _password;
    }

    public int getId()
    {
        return _id;
    }

    public IAccount getAccount(int i)
    {
        return _accounts.get(i - 1);
    }

    public IAccount getAccount()
    {
        return _lastAccount;
    }

    public void setAccount(IAccount account)
    {
        _lastAccount = account;
        _accounts.add(account);
    }

    public void setBank(Bank bank)
    {
        _bank = bank;
    }

    public Bank getBank()
    {
        return _bank;
    }

    public boolean trustStatus()
    {
        return _address != null && _numberOfPassport != 0;
    }

    public void changeAccount(IAccount account)
    {
        _lastAccount = account;
    }

}
