package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountsPage;
import pages.BillPayPage;
import pages.LoginPage;
import java.time.Duration;


public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        // UI-01: sucessful login
        loginPage.login("john", "demo");
        // check title after login
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isOverviewVisible = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Accounts Overview"));

        Assertions.assertTrue(isOverviewVisible, "Login failed or 'Accounts Overview' not displayed!");

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


    // UI 3 - OPEN NEW ACCOUNT
    @Test
    public void testCreateSavingsAccount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        AccountsPage accountsPage = new AccountsPage(driver);
        accountsPage.openSavingsAccount();

        Assertions.assertTrue(accountsPage.isAccountOpenedSuccessfully(), "Failed to open new Savings account!");
    }


    // UI 6 - BILL PAY PROCESS
    @Test
    public void testBillPayment() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.payBill("EVN Macedonia", "12345", "50.00");

        Thread.sleep(3000);
        Assertions.assertTrue(driver.getPageSource().contains("Bill Payment Complete"));
    }

    // SCENARIO: UI-10 - Logout & Session Invalidation
    // ROLE: Security Testing - Identified BUG-03 (Insecure Session Termination)
    @Test
    public void testLogoutAndSessionInvalidation() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        loginPage.logout();
        Assertions.assertTrue(loginPage.isUserLoggedOut(), "Logout failed!");

        driver.navigate().back();

        // access attempt after logout
        //driver.get("https://parabank.parasoft.com/parabank/overview.htm");

        boolean isBalanceVisible = driver.getPageSource().contains("Accounts Overview");

        //boolean redirectedToLogin = loginPage.isLoginFormVisible();

        //driver.navigate().back();

        Assertions.assertFalse(isBalanceVisible,
                "Security Breach: User can access Overview after Logout!");
    }


    // UI 11 - EMPTY FORM VALIDATION
    @Test
    public void testEmptyBillPayValidation() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        BillPayPage billPayPage = new BillPayPage(driver);

        driver.findElement(By.linkText("Bill Pay")).click();
        driver.findElement(By.xpath("//input[@value='Send Payment']")).click();

        // (Client-side validation)
        Assertions.assertTrue(driver.getPageSource().contains("Payee name is required."),
                "Validation failed: Empty form was submitted!");
    }
}