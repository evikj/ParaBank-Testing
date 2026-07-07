package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select; // ОБВЕЗНО ДОДАДЕНО
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BillPayPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By billPayLink = By.linkText("Bill Pay");
    private By payeeNameField = By.name("payee.name");
    private By addressField = By.name("payee.address.street");
    private By cityField = By.name("payee.address.city");
    private By stateField = By.name("payee.address.state");
    private By zipCodeField = By.name("payee.address.zipCode");
    private By phoneNumberField = By.name("payee.phoneNumber");
    private By accountField = By.name("payee.accountNumber");
    private By verifyAccountField = By.name("verifyAccount");
    private By amountField = By.name("amount");
    private By fromAccountDropdown = By.name("fromAccountId"); // Локатор за сметката
    private By sendPaymentButton = By.xpath("//input[@value='Send Payment']");
    private By successMessageHeader = By.xpath("//h1[@class='title' and text()='Bill Payment Complete']");

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method 1: Legacy support for 3 parameters (for LoginTest.java)
    public void payBill(String name, String accountNumber, String amountValue) {
        fillBillPaymentForm(name, accountNumber, amountValue);
    }

    // Method 2: Standard form fill with 3 parameters
    public void fillBillPaymentForm(String name, String accountNumber, String amountValue) {
        wait.until(ExpectedConditions.elementToBeClickable(billPayLink)).click();
        fillCommonFields(name, accountNumber, amountValue);
        driver.findElement(sendPaymentButton).click();
    }

    // Method 3: NEW version with 4 parameters (for the integration test)
    public void fillBillPaymentForm(String name, String accountNumber, String amountValue, String fromAccountId) {
        wait.until(ExpectedConditions.elementToBeClickable(billPayLink)).click();
        fillCommonFields(name, accountNumber, amountValue);

        // Select the specific account from the dropdown
        Select select = new Select(driver.findElement(fromAccountDropdown));
        select.selectByVisibleText(fromAccountId);

        driver.findElement(sendPaymentButton).click();
    }

    // Private helper to avoid repeating code
    private void fillCommonFields(String name, String accountNumber, String amountValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(payeeNameField)).clear();
        driver.findElement(payeeNameField).sendKeys(name);
        driver.findElement(addressField).clear();
        driver.findElement(addressField).sendKeys("Main Street 123");
        driver.findElement(cityField).clear();
        driver.findElement(cityField).sendKeys("Skopje");
        driver.findElement(stateField).clear();
        driver.findElement(stateField).sendKeys("Macedonia");
        driver.findElement(zipCodeField).clear();
        driver.findElement(zipCodeField).sendKeys("1000");
        driver.findElement(phoneNumberField).clear();
        driver.findElement(phoneNumberField).sendKeys("070111222");
        driver.findElement(accountField).clear();
        driver.findElement(accountField).sendKeys(accountNumber);
        driver.findElement(verifyAccountField).clear();
        driver.findElement(verifyAccountField).sendKeys(accountNumber);
        driver.findElement(amountField).clear();
        driver.findElement(amountField).sendKeys(amountValue);
    }

    public boolean isPaymentConfirmed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageHeader));
            return driver.findElement(successMessageHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}