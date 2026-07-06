package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By openNewAccountLink = By.linkText("Open New Account");
    private By accountTypeDropdown = By.id("type");
    private By openAccountButton = By.xpath("//input[@value='Open New Account']");
    private By resultResultTitle = By.xpath("//h1[@class='title' and text()='Account Opened!']");
    private By newAccountIdLink = By.id("newAccountId");

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openSavingsAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountLink)).click();

        // Wait for the dropdown to be visible and select SAVINGS
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountTypeDropdown));
        Select dropdown = new Select(driver.findElement(accountTypeDropdown));
        dropdown.selectByVisibleText("SAVINGS");

        // In ParaBank, sometimes you need to wait for the 'From Account' dropdown to populate
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        driver.findElement(openAccountButton).click();
    }

    public boolean isAccountOpenedSuccessfully() {
        try {
            // Wait until the success message or the new account ID link is visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(newAccountIdLink));
            return driver.findElement(newAccountIdLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}