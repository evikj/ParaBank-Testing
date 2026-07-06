package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.AccountsPage;
import pages.BillPayPage;
import pages.LoginPage;


public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        // UI-01: sucessful login
        loginPage.login("john", "demo");
        // check title after login
        Assertions.assertTrue(driver.getPageSource().contains("Accounts Overview"));
        Thread.sleep(2000);
    }

    @Test
    public void testUnsuccessfulLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        // UI-02: unsucessful login
        loginPage.login("wrong_user", "wrong_pass");

        // check error message
        String error = loginPage.getErrorMessage();
        Assertions.assertEquals("The username and password could not be verified.", error);
        Thread.sleep(2000);
    }

    @Test
    public void testCreateSavingsAccount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.openSavingsAccount();

        Assertions.assertTrue(accountsPage.isAccountOpenedSuccessfully(), "Failed to open new Savings account!");
    }

    @Test
    public void testBillPayment() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.payBill("EVN Macedonia", "12345", "50.00");

        Thread.sleep(3000);
        Assertions.assertTrue(driver.getPageSource().contains("Bill Payment Complete"));
    }
}