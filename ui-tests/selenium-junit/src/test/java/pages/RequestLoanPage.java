package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RequestLoanPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By requestLoanLink = By.linkText("Request Loan");
    private By loanAmountField = By.id("amount");
    private By downPaymentField = By.id("downPayment");
    private By applyNowButton = By.xpath("//input[@value='Apply Now']");
    private By loanStatusResult = By.id("loanStatus");
    private By loanResponseTitle = By.xpath("//h1[@class='title' and text()='Loan Request Processed']");

    public RequestLoanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void applyForLoan(String amount, String downPayment) {
        wait.until(ExpectedConditions.elementToBeClickable(requestLoanLink)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountField)).sendKeys(amount);
        driver.findElement(downPaymentField).sendKeys(downPayment);

        // Wait a bit for the account dropdown to be ready (internal ParaBank logic)
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        driver.findElement(applyNowButton).click();
    }

    public String getLoanStatus() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loanStatusResult));
            return driver.findElement(loanStatusResult).getText();
        } catch (Exception e) {
            return "Error: Status not found";
        }
    }
}