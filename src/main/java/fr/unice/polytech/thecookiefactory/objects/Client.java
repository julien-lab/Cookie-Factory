package fr.unice.polytech.thecookiefactory.objects;

import fr.unice.polytech.thecookiefactory.objects.account.ClientAccount;

public class Client {

    private static int clientCount = 1;
    private final int clientNumber;
    private ClientAccount clientAccount;
    private Order order;
    private double moneyOnHisCard;

    public Client() {
        this.clientNumber = clientCount;
        clientCount++;
        this.clientAccount = null;
        this.order = null;
        this.moneyOnHisCard = 1000;
    }

    public Client(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
        this.clientNumber = this.clientAccount.getAccountNumber();
        this.order = null;
        this.moneyOnHisCard = 1000;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public ClientAccount getAccount() {
        return clientAccount;
    }

    public void setAccount(ClientAccount account) {
        this.clientAccount = account;
    }

    public double getMoneyOnHisCard() {
        return moneyOnHisCard;
    }

    public void setMoneyOnHisCard(double moneyOnHisCard) {
        this.moneyOnHisCard = moneyOnHisCard;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (this.getAccount() != null) {
            this.getAccount().addOrder(order);
        }
    }

    public boolean isPartOfLoyaltyProgram(){
        if(clientAccount != null){
            return clientAccount.isPartOfLoyaltyProgram();
        }
        return false;
    }

    public boolean canHaveLoyaltyReduction(){
        boolean canHaveLoyaltyReduction =false;
        if(this.isPartOfLoyaltyProgram() && clientAccount.getNumberCookiesPurchased() >= 30){
            canHaveLoyaltyReduction = true;
        }
        return canHaveLoyaltyReduction;
    }

    public boolean useLoyaltyReduction(){
        if(this.canHaveLoyaltyReduction()){
            clientAccount.remove30PurchasedCookies();
            return true;
        }
        return false;
    }

}
