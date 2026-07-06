package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillPayPage {
    private WebDriver driver;

    private By billPayLink = By.linkText("Bill Pay");
    private By payeeName = By.name("payee.name");
    private By address = By.name("payee.address.street");
    private By city = By.name("payee.address.city");
    private By state = By.name("payee.address.state");
    private By zipCode = By.name("payee.address.zipCode");
    private By phoneNumber = By.name("payee.phoneNumber");
    private By account = By.name("payee.accountNumber");
    private By verifyAccount = By.name("verifyAccount");
    private By amount = By.name("amount");
    private By sendPaymentButton = By.xpath("//input[@value='Send Payment']");

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
    }

    public void payBill(String name, String acc, String amt) {
        driver.findElement(billPayLink).click();
        driver.findElement(payeeName).sendKeys(name);
        driver.findElement(address).sendKeys("Test St 1");
        driver.findElement(city).sendKeys("Skopje");
        driver.findElement(state).sendKeys("MK");
        driver.findElement(zipCode).sendKeys("1000");
        driver.findElement(phoneNumber).sendKeys("070123456");
        driver.findElement(account).sendKeys(acc);
        driver.findElement(verifyAccount).sendKeys(acc);
        driver.findElement(amount).sendKeys(amt);
        driver.findElement(sendPaymentButton).click();
    }
}