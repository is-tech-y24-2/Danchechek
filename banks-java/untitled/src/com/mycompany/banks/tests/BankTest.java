package com.mycompany.banks.tests;

import com.mycompany.banks.models.Bank;
import com.mycompany.banks.models.Client;
import com.mycompany.banks.models.MainBank;
import org.junit.jupiter.api.*;

public class BankTest {
    private MainBank _mainBank;
    private Bank _tinkoff;

    @BeforeEach
    private void reset()
    {

        _mainBank = new MainBank();
        _tinkoff = _mainBank.addBank("Tinkoff");
    }


    @Test
    public void addDebitAccount()
    {
        _mainBank.getBank("Tinkoff").setPercentForDebit(0.2f);
        Client dan = _mainBank.registration("Dan", "Shevchuk", "Fedora Abramova 4", 401544544);
        String password = "12345678";
        _mainBank.addBankToClient(_tinkoff.getName(), dan);
        _mainBank.setPassword(dan.getId(), password);
        _mainBank.getBank("Tinkoff").addDebitAccountToClient(1000, dan.getId());
        Assertions.assertEquals(1000, _mainBank.login(dan.getId(), "12345678").getAccount().getBalance());
        _mainBank.addDays(31);
        Assertions.assertEquals(1200, _mainBank.login(dan.getId(), "12345678").getAccount().getBalance());
    }

    @Test
    public void addCreditAccount()
    {
        _mainBank.addCreditAccountConditions(0, 1000, 100, _tinkoff.getName());
        Client dan = _mainBank.registration("Dan", "Shevchuk", "Fedora Abramova 4", 401544544);
        String password = "12345678";
        _mainBank.addBankToClient(_tinkoff.getName(), dan);
        _mainBank.setPassword(dan.getId(), password);
        _mainBank.addCreditAccountToClient(1000, dan.getId(), dan.getBank().getName());
        Assertions.assertEquals(dan.getAccount().getBalance(), 1000);
        _mainBank.addDays(31);
        Assertions.assertEquals(dan.getAccount().getBalance(), 900);
    }

    @Test
    public void addDepositAccount()
    {
        _mainBank.addDepositAccountConditions(0, 1000, 366, 0.06f, _tinkoff.getName());
        Client dan = _mainBank.registration("Dan", "Shevchuk", "Fedora Abramova 4", 401544544);
        String password = "12345678";
        _mainBank.addBankToClient(_tinkoff.getName(), dan);
        _mainBank.setPassword(dan.getId(), password);
        _mainBank.addDepositAccountToClient(900, dan.getId());
        Assertions.assertEquals(_mainBank.login(dan.getId(), "12345678").getAccount().getBalance(), 900f);
        _mainBank.addDays(367);
        Assertions.assertEquals(_mainBank.login(dan.getId(), "12345678").getAccount().getBalance(), 954);
    }

    @Test
    public void doTransfer()
    {
        _mainBank.setPercentForDebitToBank(0.4f, _tinkoff.getName());
        Client dan = _mainBank.registration("Dan", "Shevchuk", "Fedora Abramova 4", 401544544);
        Client nekit = _mainBank.registration("Nekit", "Yusupov", "Fedora Abramova 4", 401124553);
        String password = "12345678";
        _mainBank.addBankToClient(_tinkoff.getName(), dan);
        _mainBank.addBankToClient(_tinkoff.getName(), nekit);
        _mainBank.setPassword(dan.getId(), password);
        _mainBank.setPassword(nekit.getId(), password);
        _mainBank.addDebitAccountToClient(100, dan.getId());
        _mainBank.addDebitAccountToClient(100, nekit.getId());
        Assertions.assertEquals(dan.getAccount().getBalance(), 100);
        Assertions.assertEquals(nekit.getAccount().getBalance(), 100);
        _mainBank.doTransactions(dan.getId(), nekit.getId(), 100);
        Assertions.assertEquals(dan.getAccount().getBalance(), 0);
        Assertions.assertEquals(nekit.getAccount().getBalance(), 200);
    }

    @Test
    public void cancelingTransaction()
    {
        _mainBank.setPercentForDebitToBank(0.4f, _tinkoff.getName());
        Client dan = _mainBank.registration("Dan", "Shevchuk", "Fedora Abramova 4", 401544544);
        Client nekit = _mainBank.registration("Nekit", "Yusupov", "Fedora Abramova 4", 401124553);
        String password = "12345678";
        _mainBank.addBankToClient(_tinkoff.getName(), dan);
        _mainBank.addBankToClient(_tinkoff.getName(), nekit);
        _mainBank.setPassword(dan.getId(), password);
        _mainBank.setPassword(nekit.getId(), password);
        _mainBank.addDebitAccountToClient(100, dan.getId());
        _mainBank.addDebitAccountToClient(100, nekit.getId());
        Assertions.assertEquals(dan.getAccount().getBalance(), 100);
        Assertions.assertEquals(nekit.getAccount().getBalance(), 100);
        _mainBank.doTransactions(dan.getId(), nekit.getId(), 100);
        Assertions.assertEquals(dan.getAccount().getBalance(), 0);
        Assertions.assertEquals(nekit.getAccount().getBalance(), 200);
        _mainBank.cancelingTransactions(100, dan.getId(), nekit.getId());
        Assertions.assertEquals(dan.getAccount().getBalance(), 100);
        Assertions.assertEquals(nekit.getAccount().getBalance(), 100);
    }
}
