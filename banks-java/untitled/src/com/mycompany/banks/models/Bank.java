package com.mycompany.banks.models;

import com.mycompany.banks.interfaces.IAccount;
import com.mycompany.banks.solutions.CreditAccount;
import com.mycompany.banks.solutions.DebitAccount;
import com.mycompany.banks.solutions.DepositAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bank {
    private final List<Client> _clients = new ArrayList();
    private final List<Transaction> _transactions  = new ArrayList<>();
    private final String _name;
    private float _limitMoneyForUnTrust = -1;
    private float _percentForDebit = 0;
    private final List<IAccount> _accounts = new ArrayList<>();

    public Bank(String name)
    {
        _name = name;
    }

    public void addDebitAccountToClient(float capital, int id)
    {
        if (_percentForDebit == 0)
        {
            throw new RuntimeException("This bank haven't debit accounts");
        }

        findClientInside(id).setAccount(
                new DebitAccount(capital, id, _percentForDebit, this, findClientInside(id)));
    }

    public void addDepositAccountToClient(float capital, int id)
    {
        List<IAccount> depositAccounts  = _accounts.stream().filter(account -> account.getName().equals("deposit")).collect(Collectors.toList());

        for (IAccount depositAccount : depositAccounts) {
            if (depositAccount.minMoney() <= capital && depositAccount.maxMoney() >= capital) {
                float minMoney = depositAccount.minMoney();
                float maxMoney = depositAccount.maxMoney();
                int period = depositAccount.getPeriod();
                float percentage = depositAccount.getPercentage();
                findClientInside(id).setAccount(new DepositAccount(minMoney, maxMoney, period, capital, id, percentage));
            }
        }
    }

    public void addDepositAccountConditions(float minValue, float maxValue, int period,  float percentage)
    {
        var depositAccount = new DepositAccount(minValue, maxValue, period, 0, 0, percentage);
        _accounts.add(depositAccount);
    }

    public void addCreditAccountConditions(float minValue, float maxValue, float percentage)
    {
        var creditAccount = new CreditAccount(0, minValue, maxValue, 0, percentage);
        _accounts.add(creditAccount);
    }

    public void addCreditAccountToClient(float capital, int id)
    {
        List<IAccount> creditAccounts = _accounts.stream().filter(account -> account.getName().equals("Credit")).collect(Collectors.toList());

        for (IAccount creditAccount : creditAccounts) {
            if (creditAccount.minMoney() <= capital && creditAccount.maxMoney() >= capital) {
                Client client = findClientInside(id);
                float min = creditAccount.minMoney();
                float max = creditAccount.maxMoney();
                float percentage = creditAccount.getMinusSum();
                findClientInside(id).setAccount(new CreditAccount(capital, min, max, id, percentage));
            }
        }
    }

    public void setPercentForDebit(float percentForDebit)
    {
        _clients.forEach(client -> {
            if (client.getAccount() instanceof DebitAccount) {
                float capital = client.getAccount().getBalance();
                int id = client.getAccount().getId();
                client.setAccount(new DebitAccount(capital, id, percentForDebit, this, client));
            }
        });

        _percentForDebit = percentForDebit;
    }

    public String getName()
    {
        return _name;
    }

    public void addClient(Client client)
    {
        _clients.add(client);
    }

    public void addLimitMoney(float limit)
    {
        _limitMoneyForUnTrust = limit;
    }

    public float getLimit()
    {
        return _limitMoneyForUnTrust;
    }

    public void addDays(int days)
    {
        for(Client t : _clients)
        {
            t.getAccount().addDays(days);
        }
    }

    public Client grantAccessToTheClient(int id, String password)
    {
        if (findClientInside(id) == null)
        {
            throw new RuntimeException("We haven't this id client");
        }

        if (!Objects.equals(findClientInside(id).getPassword(), password))
        {
            throw new RuntimeException("Invalid password");
        }

        return findClientInside(id);
    }

    public List<Client> getClients()
    {
        return new ArrayList(_clients);
    }

    public void setPassword(String password, int id)
    {
        if (findClientInside(id) == null)
        {
            throw new RuntimeException("We haven't this id client");
        }

        findClientInside(id).setPassword(password);
    }

    public float GetBalance(int id)
    {
        return findClientInside(id).getAccount().getBalance();
    }

    public void closeAccount(int id)
    {
        if (findClientInside(id).getAccount().canCloseAccount())
        {
            findClientInside(id).setAccount(null);
        }
    }

    public void addMoney(float money, int id)
    {
        if (findClientInside(id) == null)
        {
            throw new RuntimeException("We haven't this user");
        }

        findClientInside(id).getAccount().addMoney(money);
    }

    public void cancelingTransactions(float money, int firstClientId, int secondClientId)
    {
        for (int i = 0; i < _transactions.size(); i++)
        {
            Client firstClient = findClientInside(_transactions.get(i).getFirstClientId());
            Client secondClient = findClientInside(_transactions.get(i).getSecondClientId());

            if (_transactions.get(i).getFirstClientId() == firstClientId &&
                    _transactions.get(i).getSecondClientId() == secondClientId && _transactions.get(i).getMoneySum() == money)
            {
                _transactions.remove(i);
                firstClient.getAccount().addMoney(money);
                secondClient.getAccount().withdrawMoney(money);
            }
        }
    }

    public void doTransactions(float money, int firstClientId, int secondClientId) {
        if (!findClientInside(firstClientId).trustStatus()
                && money > _limitMoneyForUnTrust
                && _limitMoneyForUnTrust != -1)
        {
            throw new RuntimeException(
                    "You are not trust client, and u can't do it, please set passport and address");
        }

        addMoney(money, secondClientId);
        withdrawMoney(money, firstClientId);
        addTransactions(new Transaction(firstClientId, secondClientId, money));
    }

    public List<IAccount> getCredits()
    {
        List<IAccount> depositAccounts = new ArrayList<>();
        for (IAccount account : _accounts) {
            if (Objects.equals(account.getName(), "Credit")) {
                depositAccounts.add(account);
            }
        }

        return depositAccounts;
    }

    public List<IAccount> getDeposits()
    {
        List<IAccount> depositAccounts = new ArrayList<>();
        for (IAccount account : _accounts) {
            if (Objects.equals(account.getName(), "deposit")) {
                depositAccounts.add(account);
            }
        }

        return depositAccounts;
    }

    public float getPercentForDebit()
    {
        return _percentForDebit;
    }

    public void withdrawMoney(float money, int id)
    {
        if (findClientInside(id) == null)
        {
            throw new RuntimeException("We haven't this user");
        }

        findClientInside(id).getAccount().withdrawMoney(money);
    }

    public void withdrawMoney(float money, int id, int i)
    {
        if (findClientInside(id) == null)
        {
            throw new RuntimeException("We haven't this user");
        }

        findClientInside(id).getAccount(i).withdrawMoney(money);
    }

    private void addTransactions(Transaction transaction)
    {
        _transactions.add(transaction);
    }

    private Client findClientInside(int id) {
        for (Client client : _clients) {
            if (client.getId() == id) {
                return client;
            }
        }

        return null;
    }
}
