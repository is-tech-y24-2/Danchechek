package com.mycompany.banks.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainBank {

    private final List<Integer> _usersId;
    private final List<Bank> _banks;
    private final List<Transaction> _transactions;

    public MainBank()
    {
        _transactions = new ArrayList<Transaction>();
        _usersId = new ArrayList<Integer>();
        _banks = new ArrayList<Bank>();
    }

    public void addDays(int days)
    {
        for (Bank t : _banks)
        {
            t.addDays(days);
        }
    }

    public void addDebitAccountToClient(float capital, int id)
    {
        getUserBank(id).addDebitAccountToClient(capital, id);
    }

    public void addDepositAccountToClient(float capital, int id)
    {
        getUserBank(id).addDepositAccountToClient(capital, id);
    }

    public void addDepositAccountConditions(float minValue, float maxValue, int period, float percentage, String bank)
    {
        List<String> names = new ArrayList();
        for (Bank value : _banks) {
            names.add(value.getName());
        }

        if(!names.contains(bank))
        {
            throw new RuntimeException("We haven't this bank");
        }

        for (Bank value : _banks) {
            if (value.getName().equals(bank)) {
                value.addDepositAccountConditions(minValue, maxValue, period, percentage);
            }
        }
    }

    public Bank addBank(String name)
    {
        for (Bank bank : _banks) {
            if (bank.getName().equals(name)) {
                throw new RuntimeException("This bank has been registered");
            }
        }

        _banks.add(new Bank(name));
        return _banks.get(_banks.size() - 1);
    }

    public void addCreditAccountToClient(float capital, int id, String bank)
    {
        List<String> names = new ArrayList();
        for (Bank value : _banks) {
            names.add(value.getName());
        }

        if(!names.contains(bank))
        {
            throw new RuntimeException("We haven't this bank");
        }

        for (Bank value : _banks) {
            if (value.getName().equals(bank)) {
                value.addCreditAccountToClient(capital, id);
            }
        }
    }

    public void addCreditAccountConditions(float minValue, float maxValue, float percentage, String bank)
    {
        List<String> names = new ArrayList();
        for (Bank value : _banks) {
            names.add(value.getName());
        }

        if(!names.contains(bank))
        {
            throw new RuntimeException("We haven't this bank");
        }

        for (Bank value : _banks) {
            if (value.getName().equals(bank)) {
                value.addCreditAccountConditions(minValue, maxValue, percentage);
            }
        }
    }

    public void setPercentForDebitToBank(float percentForDebit, String bank)
    {
        List<String> names = new ArrayList();
        for (Bank value : _banks) {
            names.add(value.getName());
        }

        if(!names.contains(bank))
        {
            throw new RuntimeException("We haven't this bank");
        }

        for (Bank value : _banks) {
            if (value.getName().equals(bank)) {
                value.setPercentForDebit(percentForDebit);
            }
        }
    }

    public void doTransactions(int idFirst, int idSecond, float sum)
    {
        if (getUserBank(idFirst).getName() == getUserBank(idSecond).getName())
        {
            getUserBank(idFirst).doTransactions(sum, idFirst, idSecond);
        }
        else
        {
            doTransactionsInterbank(idFirst, idSecond, sum);
        }
    }

    public void cancelingTransactions(float money, int firstClientId, int secondClientId)
    {
        if (Objects.equals(getUserBank(firstClientId).getName(), getUserBank(secondClientId).getName()))
        {
            getUserBank(firstClientId).cancelingTransactions(money, firstClientId, secondClientId);
        }
        else
        {
            cancelingTransactionsInterbank(firstClientId, secondClientId, money);
        }
    }


    public ArrayList<Bank> getBanks()
    {
        return new ArrayList(_banks);
    }

    public void addLimitMoneyToBank(float limit, String bank)
    {
        List<String> names = new ArrayList();
        for (Bank value : _banks) {
            names.add(value.getName());
        }

        if (!names.contains(bank))
        {
            throw new RuntimeException("We haven't this bank");
        }

        for (Bank value : _banks) {
            if (value.getName().equals(bank)) {
                value.addLimitMoney(limit);
            }
        }
    }

    public void addBankToClient(String bankName, Client client)
    {
        List<String> names = new ArrayList();
        for (Bank value : _banks) {
            names.add(value.getName());
        }

        if (!names.contains(bankName))
        {
            throw new RuntimeException("We haven't this bank");
        }

        for (Bank value : _banks) {
            if (value.getName().equals(bankName)) {
                client.setBank(value);
                value.addClient(client);
            }
        }
    }

    public Client registration(String name, String secondName, String address, int numberOfPassport)
    {
        int uniqueId = getUniqueId();
        _usersId.add(uniqueId);
        var client = new Client(name, secondName, address, numberOfPassport, uniqueId);
        return client;
    }

    public void setPassword(int id, String password)
    {
        getUserBank(id).setPassword(password, id);
    }

    public Client login(int id, String password)
    {
        if (!_usersId.contains(id))
        {
            throw new RuntimeException("Invalid Id");
        }

        return getUserBank(id).grantAccessToTheClient(id, password);
    }

    public Bank getBank(String name)
    {
        List<String> names = new ArrayList();
        for (Bank value : _banks) {
            names.add(value.getName());
        }

        if (!names.contains(name))
        {
            throw new RuntimeException("We haven't this bank");
        }

        for (Bank bank : _banks) {
            if (bank.getName().equals(name)) {
                return bank;
            }
        }

        return null;
    }

    public void closeAccount(int id)
    {
        getUserBank(id).closeAccount(id);
    }

    private void cancelingTransactionsInterbank(int idFirst, int idSecond, float sum)
    {
        for (int i = 0; i < _transactions.size(); i++)
        {
            if (_transactions.get(i).getFirstClientId() != idFirst ||
                    _transactions.get(i).getSecondClientId() != idSecond || _transactions.get(i).getMoneySum() != sum) continue;
            _transactions.remove(i);
            getUserBank(idFirst).addMoney(sum, idFirst);
            getUserBank(idSecond).withdrawMoney(sum, idSecond);
        }
    }

    private Bank getUserBank(int id)
    {
        for (Bank bank : _banks) {
            for (int j = 0; j < bank.getClients().size(); j++) {
                if (bank.getClients().get(j).getId() == id) {
                    return bank;
                }
            }
        }

        return null;
    }

    private void doTransactionsInterbank(int idFirst, int idSecond, float sum)
    {
        _transactions.add(new Transaction(idFirst, idSecond, sum));
        getUserBank(idFirst).withdrawMoney(sum, idFirst);
        getUserBank(idSecond).addMoney(sum, idSecond);
    }

    private int getUniqueId()
    {
        var rand = new Random();
        int unique = rand.nextInt(100000000 - 10000000 + 1) + 10000000;
        if (!_usersId.contains(unique)) return unique;
        return getUniqueId();
    }
}
