package ru.itmo.bank.models;

import ru.itmo.bank.interfaces.Account;
import ru.itmo.bank.solutions.CreditAccountImpl;
import ru.itmo.bank.solutions.DebitAccountImpl;
import ru.itmo.bank.solutions.DepositAccountImpl;
import ru.itmo.bank.tools.BanksException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bank {
    private List<Client> _clients = new ArrayList();
    private List<Transaction> _transactions = new ArrayList<>();
    private final String _name;
    private float _limitMoneyForUnTrust = -1;
    private float _percentForDebit = 0;
    private final List<Account> _accounts = new ArrayList<>();

    public Bank(String name) {
        _name = name;
    }

    public void addDebitAccountToClient(float capital, int id) throws BanksException {
        if (_percentForDebit == 0) {
            throw new BanksException("This bank haven't debit accounts");
        }

        findClientInside(id).setAccount(
                new DebitAccountImpl(capital, id, _percentForDebit, this, findClientInside(id)));
    }

    public void addDepositAccountToClient(float capital, int id) {
        List<Account> depositAccounts = _accounts.stream().filter(account -> account.getName().equals("deposit")).collect(Collectors.toList());

        for (Account depositAccount : depositAccounts) {
            if (depositAccount.minMoney() <= capital && depositAccount.maxMoney() >= capital) {
                float minMoney = depositAccount.minMoney();
                float maxMoney = depositAccount.maxMoney();
                int period = depositAccount.getPeriod();
                float percentage = depositAccount.getPercentage();
                findClientInside(id).setAccount(new DepositAccountImpl(minMoney, maxMoney, period, capital, id, percentage));
            }
        }
    }

    public void addDepositAccountConditions(float minValue, float maxValue, int period, float percentage) {
        var depositAccount = new DepositAccountImpl(minValue, maxValue, period, 0, 0, percentage);
        _accounts.add(depositAccount);
    }

    public void addCreditAccountConditions(float minValue, float maxValue, float percentage) {
        var creditAccount = new CreditAccountImpl(0, minValue, maxValue, 0, percentage);
        _accounts.add(creditAccount);
    }

    public void addCreditAccountToClient(float capital, int id) {
        List<Account> creditAccounts = _accounts
                .stream()
                .filter(account -> account.getName().equals("Credit"))
                .collect(Collectors.toList());

        for (Account creditAccount : creditAccounts) {
            if (creditAccount.minMoney() <= capital && creditAccount.maxMoney() >= capital) {
                Client client = findClientInside(id);
                float min = creditAccount.minMoney();
                float max = creditAccount.maxMoney();
                float percentage = creditAccount.getMinusSum();
                findClientInside(id).setAccount(new CreditAccountImpl(capital, min, max, id, percentage));
            }
        }
    }

    public void setPercentForDebit(float percentForDebit) {
        _clients.forEach(client -> {
            if (client.getAccount() instanceof DebitAccountImpl) {
                float capital = client.getAccount().getBalance();
                int id = client.getAccount().getId();
                client.setAccount(new DebitAccountImpl(capital, id, percentForDebit, this, client));
            }
        });

        _percentForDebit = percentForDebit;
    }

    public String getName() {
        return _name;
    }

    public void addClient(Client client) {
        _clients.add(client);
    }

    public void addLimitMoney(float limit) {
        _limitMoneyForUnTrust = limit;
    }

    public float getLimit() {
        return _limitMoneyForUnTrust;
    }

    public void addDays(int days) {
        for (Client t : _clients) {
            t.getAccount().addDays(days);
        }
    }

    public Client grantAccessToTheClient(int id, String password) throws BanksException {
        if (findClientInside(id) == null) {
            throw new BanksException("We haven't this id client");
        }

        if (!Objects.equals(findClientInside(id).getPassword(), password)) {
            throw new BanksException("Invalid password");
        }

        return findClientInside(id);
    }

    public List<Client> getClients() {
        return new ArrayList(_clients);
    }

    public void setPassword(String password, int id) throws BanksException {
        if (findClientInside(id) == null) {
            throw new RuntimeException("We haven't this id client");
        }

        findClientInside(id).setPassword(password);
    }

    public float GetBalance(int id) {
        return findClientInside(id).getAccount().getBalance();
    }

    public void closeAccount(int id) {
        if (findClientInside(id).getAccount().canCloseAccount()) {
            findClientInside(id).setAccount(null);
        }
    }

    public void addMoney(float money, int id) {
        if (findClientInside(id) == null) {
            throw new RuntimeException("We haven't this user");
        }

        findClientInside(id).getAccount().addMoney(money);
    }

    public void cancelingTransactions(float money, int firstClientId, int secondClientId) throws BanksException {
        for (int i = 0; i < _transactions.size(); i++) {
            Client firstClient = findClientInside(_transactions.get(i).getFirstClientId());
            Client secondClient = findClientInside(_transactions.get(i).getSecondClientId());

            if (_transactions.get(i).getFirstClientId() == firstClientId &&
                    _transactions.get(i)
                            .getSecondClientId() == secondClientId && _transactions.get(i).getMoneySum() == money) {
                _transactions.remove(i);
                firstClient.getAccount().addMoney(money);
                secondClient.getAccount().withdrawMoney(money);
            }
        }
    }

    public void doTransactions(float money, int firstClientId, int secondClientId) throws BanksException {
        if (!findClientInside(firstClientId).trustStatus()
                && money > _limitMoneyForUnTrust
                && _limitMoneyForUnTrust != -1) {
            throw new RuntimeException(
                    "You are not trust client, and u can't do it, please set passport and address");
        }

        addMoney(money, secondClientId);
        withdrawMoney(money, firstClientId);
        addTransactions(new Transaction(firstClientId, secondClientId, money));
    }

    public List<Account> getCredits() {
        List<Account> depositAccounts = new ArrayList<>();
        for (Account account : _accounts) {
            if (Objects.equals(account.getName(), "Credit")) {
                depositAccounts.add(account);
            }
        }

        return depositAccounts;
    }

    public List<Account> getDeposits() {
        List<Account> depositAccounts = new ArrayList<>();
        for (Account account : _accounts) {
            if (Objects.equals(account.getName(), "deposit")) {
                depositAccounts.add(account);
            }
        }

        return depositAccounts;
    }

    public float getPercentForDebit() {
        return _percentForDebit;
    }

    public void withdrawMoney(float money, int id) throws BanksException {
        if (findClientInside(id) == null) {
            throw new BanksException("We haven't this user");
        }

        findClientInside(id).getAccount().withdrawMoney(money);
    }

    public void withdrawMoney(float money, int id, int i) throws BanksException {
        if (findClientInside(id) == null) {
            throw new BanksException("We haven't this user");
        }

        findClientInside(id).getAccount(i).withdrawMoney(money);
    }

    private void addTransactions(Transaction transaction) {
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
