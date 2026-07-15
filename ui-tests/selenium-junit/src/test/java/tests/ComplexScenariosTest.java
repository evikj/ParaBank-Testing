package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.*;

public class ComplexScenariosTest extends BaseTest {


    // UI 8 - CHECK BALANCE CONSISTENCY
    @Test
    public void testAccountDetailsBalanceConsistency() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        OverviewPage overviewPage = new OverviewPage(driver);
        overviewPage.goToOverview();

        // UI-08: Check balance consistency
        String overviewBalance = overviewPage.getFirstAccountBalance();
        System.out.println("Balance on Overview: [" + overviewBalance + "]");

        overviewPage.clickFirstAccount();

        String detailsBalance = overviewPage.getDetailsBalance();
        System.out.println("Balance on Details: [" + detailsBalance + "]");

        // Assertion with trimmed values to ignore hidden spaces
        Assertions.assertEquals(overviewBalance, detailsBalance,
                "The balance on the Overview page doesn't match the Account Details page!");
    }


    // UI 7 - TEST CREATE AND FIND TRANSACTION
    @Test
    public void testCreateAndFindTransaction() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        //1: ID from Overview
        OverviewPage overviewPage = new OverviewPage(driver);
        overviewPage.goToOverview();
        String myAccountId = overviewPage.getFirstAccountId();
        System.out.println("Using Account ID: " + myAccountId);

        //2: payment
        String uniqueAmount = "12.34";
        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.fillBillPaymentForm("Test Payee", "99999", uniqueAmount, myAccountId);
        Assertions.assertTrue(billPayPage.isPaymentConfirmed(), "Bill payment failed!");

        //3:search
        FindTransactionsPage findPage = new FindTransactionsPage(driver);
        findPage.findByAmount(uniqueAmount, myAccountId);

        //4:verification
        boolean isFound = findPage.isTransactionInResultTable(uniqueAmount);
        Assertions.assertTrue(isFound, "Transaction of $" + uniqueAmount + " was not found in Account " + myAccountId);
    }


    // UI 9 UPDATE CONTACT INFO
    @Test
    public void testUpdateContactInfo() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        // UI-09
        UpdateProfilePage profilePage = new UpdateProfilePage(driver);
        profilePage.updateAllProfileFields("New Test Boulevard 99");

        Assertions.assertTrue(profilePage.isUpdateSuccessful(), "Profile update failed!");
    }
}