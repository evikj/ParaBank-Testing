package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OverviewPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Using tbody to ensure we skip the header row
    private By accountsOverviewLink = By.linkText("Accounts Overview");
    private By firstAccountLink = By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[1]/a");
    private By firstAccountBalance = By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[2]");
    private By accountDetailsBalance = By.id("balance");

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void goToOverview() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsOverviewLink)).click();
        // Wait for the table to actually be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accountTable")));
    }

    public String getFirstAccountBalance() {
        // Wait until text is present and not empty
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstAccountBalance));
        wait.until(d -> !d.findElement(firstAccountBalance).getText().trim().isEmpty());
        return driver.findElement(firstAccountBalance).getText().trim();
    }

    public void clickFirstAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(firstAccountLink)).click();
    }

    public String getDetailsBalance() {
        // Wait until the details balance element is visible and contains data
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountDetailsBalance));
        wait.until(d -> !d.findElement(accountDetailsBalance).getText().trim().isEmpty());
        return driver.findElement(accountDetailsBalance).getText().trim();
    }

    public String getFirstAccountId() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstAccountLink));
        return driver.findElement(firstAccountLink).getText().trim();
    }
}