package fr.unice.polytech.thecookiefactory.objects.account;

public abstract class Account {

    private static int accountCount = 1;

    private final int accountNumber;

    public Account(){
        this.accountNumber = accountCount;
        accountCount++;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}
