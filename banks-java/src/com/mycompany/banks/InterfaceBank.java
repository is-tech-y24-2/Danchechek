package com.mycompany.banks;

import com.mycompany.banks.interfaces.IAccount;
import com.mycompany.banks.models.Client;
import com.mycompany.banks.models.MainBank;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class InterfaceBank {
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args){
        var mainBank = new MainBank();
        begin(mainBank);
    }

    private static void begin(MainBank bank) {
        System.out.println(("Хай, это банковская система, с какой стороны ты хотел бы зайти?\n"));
        System.out.println("1 - как клиент");
        System.out.println("2 - как владелец банка");
        System.out.println("3 - добавить дни");
        String choose = s.nextLine();

        if (!Objects.equals(choose, "1") && !Objects.equals(choose, "2") && !Objects.equals(choose, "3"))
        {
            System.out.println("Нет такой цифры");
            begin(bank);
        }

        if (Objects.equals(choose, "1"))
        {
            System.out.println("Вы зарегестрированы или еще не имеете акаунт?\n");
            System.out.println("1 - не зарегестрирован");
            System.out.println("2 - зарегестрирован");

            String registration = s.nextLine();
            if (!Objects.equals(registration, "1") && !Objects.equals(registration, "2"))
            {
                System.out.println("Нет такой цифры");
                begin(bank);
            }

            if (Objects.equals(registration, "1"))
            {
                registrationClient(bank);
            }

            if (Objects.equals(registration, "2"))
            {
                loginClient(bank);
            }
        }

        if (Objects.equals(choose, "2"))
        {
            System.out.println("Вы хотите зарегестрировать новый банк или управлять существующим?");
            System.out.println("1 - зарегестрировать");
            System.out.println("2 - управлять существующим");
            String registration = s.nextLine();
            if (!Objects.equals(registration, "1") && !Objects.equals(registration, "2"))
            {
                System.out.println("Нет такой цифры");
                begin(bank);
            }

            if (Objects.equals(registration, "1"))
            {
                bankRegistration(bank);
            }

            if (Objects.equals(registration, "2"))
            {
                bankManagement(bank);
            }
        }

        if (Objects.equals(choose, "3"))
        {
            System.out.println("Введите дни");
            int days = s.nextInt();
            bank.addDays(days);
            begin(bank);
        }
    }

    private static <Bank> void bankManagement(MainBank bank)
    {
        System.out.println("Выберите банк, в которым хотите управлять или ноль, чтобы вернуться в начало");
        for (int i = 0; i < bank.getBanks().size(); i++)
        {
            System.out.println(i + 1 + " - " + bank.getBanks().get(i).getName());
        }

        int bankChoose = s.nextInt();
        if (bankChoose < 0 || bankChoose > bank.getBanks().size())
        {
            System.out.println("Некорректный выбор");
            begin(bank);
            return;
        }

        System.out.println("1 - Добавить условия дебетового счета");
        System.out.println("2 - Добавить депозитный счет");
        System.out.println("3 - Добавить кредитный счет");
        System.out.println("4 - Добавить лимит для недоверительных клиентов");
        int chooseOperation = s.nextInt();
        if (chooseOperation < 0 || chooseOperation > 4)
        {
            System.out.println("Некорректный выбор");
            begin(bank);
            return;
        }

        if (chooseOperation == 0)
        {
            begin(bank);
            return;
        }

        String name = bank.getBanks().get(bankChoose - 1).getName();
        var bankChose = bank.getBank(name);

        if (chooseOperation == 1)
        {
            System.out.println("Введите ежемесячный процент для дебетового счета(Через запятую после нуля)");
            Float proc = s.nextFloat();
            bankChose.setPercentForDebit(proc);
            bankManagement(bank);
        }
        else if (chooseOperation == 2)
        {
            System.out.println("Введите минимальную и максимальную сумму изначального вклада на данный депозитный счет.\n" +
                    "А также период и процент депозитного счета");
            float minSumFloat = s.nextFloat();
            float maxSumFloat = s.nextFloat();
            int period = s.nextInt();
            float percent = s.nextFloat();
            bank.addDepositAccountConditions(minSumFloat, maxSumFloat, period, percent, bankChose.getName());
        }
        else if (chooseOperation == 3)
        {
            System.out.println("Введите минимальную и максимальную сумму для кредитного счета." +
                    "А также сумму которая будет убавляться каждый месяц");
            float minSum = s.nextFloat();
            float maxSum = s.nextFloat();
            float minosSum = s.nextFloat();
            bank.addCreditAccountConditions(minSum, maxSum, minosSum, bankChose.getName());
        }
        else if (chooseOperation == 4)
        {
            System.out.println("Введите максимальную сумму, которую клиент, не указавший паспорт или адресс,\n" +
                    " сможет снять или перевести");
            float minSumFloat = 0f;
            float minSum = s.nextFloat();
            bank.addLimitMoneyToBank(minSumFloat, bankChose.getName());
        }

        begin(bank);

    }

    private static String bankRegistration(MainBank bank)
    {
        System.out.println("Введите название банка");
        bank.addBank(s.nextLine());
        begin(bank);
        return null;
    }

    private static String registrationClient(MainBank bank)
    {
        if (bank.getBanks().size() == 0)
        {
            System.out.println("На данный момент банков не существует");
            begin(bank);
        }

        System.out.println("Сейчас мы дадим вам уникальный ID для входа в банковскую систему, запомните его плиз\n");
        System.out.println("1 - продолжить");
        System.out.println("2 - вернуться в начало");
        String choose = s.nextLine();
        if (!Objects.equals(choose, "1") && !Objects.equals(choose, "2"))
        {
            System.out.println("нет такой цифры");
            begin(bank);
        }

        if (Objects.equals(choose, "2"))
        {
            return choose;
        }

        System.out.println("Введите через enter свое имя, фамилию, адрес и код паспорта(по желанию,\n" +
                " это влияет на сумму которую вы сможете снимать и переводить, если пропускаете адресс - просто жмите enter,\n"
                + "если паспорт - 0");
        String name = s.nextLine();
        String secondName = s.nextLine();
        String address = s.nextLine();
        int passport = s.nextInt();

        Client client = bank.registration(name, secondName, address, passport);

        System.out.println("Ваш банковский ID - " + client.getId());
        System.out.println("Выберите банк, в котором хотите зарегаться");
        for (int i = 0; i < bank.getBanks().size(); i++)
        {
            System.out.println(i + 1 + " - " + bank.getBanks().get(i).getName());
        }

        int bankChoose = s.nextInt();
        if (bankChoose < 0 ||
                bankChoose > bank.getBanks().size())
        {
            System.out.println("Некорректный выбор");
        }

        bank.addBankToClient(bank.getBanks().get(bankChoose - 1).getName(), client);

        System.out.println("Придумайте пароль, он должен состоять из 8 символов");
        s.nextLine();
        String firstPassword = s.nextLine();
        System.out.println("Повторите, пожалуйста");
        String secondPassword = s.nextLine();
        if (!Objects.equals(secondPassword, firstPassword))
        {
            System.out.println("Пароли не совпадают((");
            return null;
        }

        bank.setPassword(client.getId(), secondPassword);
        System.out.println("Теперь вы можете зайти в свой личный кабинет, управлять своим счетом");
        begin(bank);
        return "successful";
    }

    private static String loginClient(MainBank bank)
    {
        System.out.println("Введите свой пароль и Id, пожалуйста");
        String password = s.nextLine();
        int id = s.nextInt();

        System.out.println(id);
        System.out.println(password);
        bank.login(id, password);

        if(bank.login(id, password) == null)
        {
            System.out.println("Авторизация неуспешна");
            begin(bank);
        }

        System.out.println("Авторизация прошла успешно");
        if (bank.login(id, password).getAccount() != null)
        {
            Client client = bank.login(id, password);
            System.out.println("Ваш аккаунт: " + bank.login(id, password).getAccount().getName());
            System.out.println("Ваши деньги на акке: " + bank.login(id, password).getAccount().getBalance() + "\n");
            System.out.println("1 - хочешь перейти на какой-нибудь другой счет, если у тебя, конечно, есть такая возможность?");
            System.out.println("2 - сделать перевод");
            System.out.println("3 - go v nachalo");
            s.nextLine();
            s.nextLine();
            String choice = s.nextLine();
            if (!Objects.equals(choice, "1") && !Objects.equals(choice, "2") && !Objects.equals(choice, "3"))
            {
                System.out.println("Нет такой цифры");
                begin(bank);
            }

            if (Objects.equals(choice, "1"))
            {
                if(!client.getAccount().canCloseAccount())
                {
                    System.out.println("У тебя к сожалению нет такой возможности, погаси кредит или дождись конца периода депозита");
                    begin(bank);
                }else
                {
                    changeAccount(client, bank);
                }
            }

            if (Objects.equals(choice, "2"))
            {
                doTransfer(client, bank);
            }

            if (Objects.equals(choice, "3"))
            {
                begin(bank);
            }
        }
        else
        {
            System.out.println("Вы пока не завели счет");
            System.out.println("Хотите?)");
            System.out.println("1 - да");
            System.out.println("2 - гоу в начало");
            s.nextLine();
            String account = s.nextLine();
            if (!Objects.equals(account, "1") && !Objects.equals(account, "2"))
            {
                System.out.println("Нет такой цифры");
                begin(bank);
            }

            if (Objects.equals(account, "1"))
            {
                setAccount(bank.login(id, password), bank);
            }

            if (Objects.equals(account, "2"))
            {
                begin(bank);
            }
        }

        return null;
    }

    private static void setAccount(Client client, MainBank bank)
    {
        System.out.println("В твоем банке имеются следующие варианты");
        if(client.getBank().getPercentForDebit() == 0 && client.getBank().getDeposits().size() == 0 && client.getBank().getCredits().size() == 0)
        {
            System.out.println("В твоем банке еще не сделали счетов, подожди немного");
            begin(bank);
        }
        if (client.getBank().getPercentForDebit() != 0)
        {
            System.out.println("1 - Дебетовый аккаунт с ежемесячным начислением в сумме вашего счета умноженного на процент "
                    + client.getBank().getPercentForDebit());
        }

        if (client.getBank().getDeposits().size() != 0)
        {
            System.out.println("2 - есть варианты депозитного счета в количестве " + client.getBank().getDeposits().size());
        }

        if (client.getBank().getCredits().size() != 0)
        {
            System.out.println("3 - есть варианты кредитного счета в количестве " + client.getBank().getCredits().size());
        }

        String account = s.nextLine();
        if (!Objects.equals(account, "1") && !Objects.equals(account, "2") && !Objects.equals(account, "3"))
        {
            System.out.println("Нет такой цифры");
            begin(bank);
        }

        if (Objects.equals(account, "1"))
        {
            System.out.println("Положи мани");
            float moneyFloat = s.nextFloat();
            bank.addDebitAccountToClient(moneyFloat, client.getId());
            System.out.println("Ок, теперь у тебя дебетовый счет в " + client.getBank().getName());
            begin(bank);
        }

        if (Objects.equals(account, "2"))
        {
            int choice = choiceDeposit(client.getBank().getDeposits());
            if (choice + 1 > client.getBank().getDeposits().size() ||
                    choice + 1 < 1)
            {
                System.out.println("Не было такой цифры");
                begin(bank);
            }

            System.out.println("Закиньте бабосик(от этого зависит какой именно у вас будет аккаунт депозитный)");
            float moneyFloat = s.nextFloat();
            bank.addDebitAccountToClient(moneyFloat, client.getId());
            System.out.println("Поздравляю, у вас есть депозитный счет");
        }

        if (Objects.equals(account, "3"))
        {
            int choice = choiceCredit(client.getBank().getCredits());
            if (choice + 1 > client.getBank().getCredits().size() ||
                    choice + 1 < 1)
            {
                System.out.println("Не было такой цифры");
                begin(bank);
            }

            System.out.println("Закиньте бабосик(от этого зависит какой именно у вас будет аккаунт кредитный)");
            float moneyFloat = s.nextFloat();
            bank.addCreditAccountToClient(moneyFloat, client.getId(), client.getBank().getName());
            System.out.println("Поздравляю, у вас есть кредитный счет");
        }

        begin(bank);
    }

    private static void changeAccount(Client client, MainBank bank)
    {
        System.out.println("Помимо твоего типа аккаунта: " + client.getAccount().getName());
        System.out.println("В твоем банке имеются следующие варианты:");
        if (!Objects.equals(client.getAccount().getName(), "debit") && client.getBank().getPercentForDebit() != 0)
        {
            System.out.println("1 - Дебетовый аккаунт с ежемесячным начислением в сумме вашего счета умноженного на процент\n "
                    + client.getBank().getPercentForDebit());
        }

        if (!Objects.equals(client.getAccount().getName(), "deposit") && client.getBank().getDeposits().size() != 0)
        {
            System.out.println("2 - есть варианты депозитного счета в количестве " + client.getBank().getDeposits().size());
        }

        if (!Objects.equals(client.getAccount().getName(), "Credit") && client.getBank().getCredits().size() != 0)
        {
            System.out.println("3 - есть варианты кредитного счета в количестве " + client.getBank().getCredits().size());
        }

        String account = s.nextLine();
        if (!Objects.equals(account, "1") && !Objects.equals(account, "2") && !Objects.equals(account, "3"))
        {
            System.out.println("Нет такой цифры");
            begin(bank);
        }

        if (Objects.equals(account, "1"))
        {
            System.out.println("Положи мани");
            float moneyFloat = 0f;
            float money = s.nextFloat();
            bank.addDebitAccountToClient(moneyFloat, client.getId());
            System.out.println("Ок, теперь у тебя дебетовый счет в " + client.getBank().getName());
            begin(bank);
        }

        if (Objects.equals(account, "2"))
        {
            int choice = choiceDeposit(client.getBank().getDeposits());
            if (choice + 1 > client.getBank().getDeposits().size() ||
                    choice + 1 < 1)
            {
                System.out.println("Не было такой цифры");
                begin(bank);
            }

            System.out.println("Закиньте бабосик(от этого зависит какой именно у вас будет аккаунт депозитный)");
            float moneyFloat = s.nextFloat();
            bank.addDepositAccountToClient(moneyFloat, client.getId());
            System.out.println("Поздравляю, у вас есть депозитный счет");
        }

        if (Objects.equals(account, "3"))
        {
            int choice = choiceCredit(client.getBank().getCredits());
            if (choice + 1 > client.getBank().getCredits().size() ||
                    choice + 1 < 1)
            {
                System.out.println("Не было такой цифры");
                begin(bank);
            }

            System.out.println("Закиньте бабосик(от этого зависит какой именно у вас будет аккаунт кредитный)");
            float moneyFloat = s.nextFloat();
            bank.addCreditAccountToClient(moneyFloat, client.getId(), client.getBank().getName());
            System.out.println("Поздравляю, у вас есть кредитный счет");
        }

        begin(bank);
    }

    private static int choiceDeposit(List<IAccount> depositAccounts)
    {
        for (int i = 0; i < depositAccounts.size(); i++)
        {
            System.out.println(i + 1 + " - минимальный вклад - " + depositAccounts.get(i).minMoney());
            System.out.println("максимальный вклад - " + depositAccounts.get(i).maxMoney());
            System.out.println("период - " + depositAccounts.get(i).getPeriod());
            System.out.println("процент - " + depositAccounts.get(i).getPercentage());
        }

        int choice = s.nextInt();
        return choice - 1;
    }

    private static int choiceCredit(List<IAccount> creditAccounts)
    {
        for (int i = 0; i < creditAccounts.size(); i++)
        {
            System.out.println((i + 1 + " - минимальный вклад - " + creditAccounts.get(i).minMoney()));
            System.out.println("максимальный вклад - " + creditAccounts.get(i).maxMoney());
            System.out.println("сумма вычета каждый месяц - " + creditAccounts.get(i).getMinusSum());
        }

        int choice = s.nextInt();
        return choice - 1;
    }

    private static void doTransfer(Client client, MainBank bank)
    {
        System.out.println("Введите сумму перевода и ID клиента");
        float money = s.nextFloat();
        int idSecond = s.nextInt();
        if (money > client.getAccount().getBalance())
        {
            System.out.println("А не много ли?)");
            begin(bank);
        }

        bank.doTransactions(client.getId(), idSecond, money);
        System.out.println("Перевод сделан успешнно");
        begin(bank);
    }

}
