package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.RequestLoanPage;

public class LoanTest extends BaseTest {

    @Test
    public void testLoanApproval() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        // Request Loan (Approved - 20% down payment)
        RequestLoanPage loanPage = new RequestLoanPage(driver);
        loanPage.applyForLoan("500", "100");

        String status = loanPage.getLoanStatus();
        Assertions.assertEquals("Approved", status, "Loan should have been approved!");
    }

    @Test
    public void testLoanDenial() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        // Request Loan (Denied - very low down payment)
        RequestLoanPage loanPage = new RequestLoanPage(driver);
        loanPage.applyForLoan("100000", "1");

        String status = loanPage.getLoanStatus();
        Assertions.assertEquals("Denied", status, "Loan should have been denied due to low down payment!");
    }
}