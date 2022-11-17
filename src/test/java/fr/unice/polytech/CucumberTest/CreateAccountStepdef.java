package fr.unice.polytech.cucumbertest;


import fr.unice.polytech.thecookiefactory.objects.account.ClientAccount;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import static org.junit.Assert.*;


public class CreateAccountStepdef implements En{

    private TheCookieFactory tfc;
    private ClientAccount clientAccount;

    public CreateAccountStepdef() {
        Given("^a client who wants to create an account in the cookie factory$", () -> {
            tfc = new TheCookieFactory();
        });

        When("^the client create his simple account to his name$", () -> {
            tfc.createClientAccount("Jacques");
            clientAccount = tfc.getClientAccount("Jacques");
        });
        Then("^the client now has an account to his name$", () -> {
            assertEquals("Jacques", clientAccount.getAccountName());
            assertFalse(clientAccount.isPartOfLoyaltyProgram());
        });


        When("^the client create his account to his name and join loyalty program$", () -> {
            tfc.createClientAccount("Georges");
            clientAccount = tfc.getClientAccount("Georges");
            clientAccount.joinLoyaltyProgram();
        });
        Then("^the client now has an account to his name with a loyalty program$", () -> {
            assertEquals("Georges", clientAccount.getAccountName());
            assertTrue(clientAccount.isPartOfLoyaltyProgram());
        });
    }
}
