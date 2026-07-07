package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class FindTransactionsPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By findTransactionsLink = By.linkText("Find Transactions");
    private By amountField = By.id("amount");
    private By findByAmountButton = By.id("findByAmount");
    private By transactionTable = By.id("transactionTable");


    private By transactionTableRows = By.xpath("//table[@id='transactionTable']//tbody/tr");

    public FindTransactionsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private By accountSelectDropdown = By.id("accountId");

    public void findByAmount(String amount, String accountId) {
        wait.until(ExpectedConditions.elementToBeClickable(findTransactionsLink)).click();

        // choose same account
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountSelectDropdown));
        Select select = new Select(driver.findElement(accountSelectDropdown));
        select.selectByVisibleText(accountId);

        wait.until(ExpectedConditions.visibilityOfElementLocated(amountField)).clear();
        driver.findElement(amountField).sendKeys(amount);
        driver.findElement(findByAmountButton).click();
    }

    public boolean isTransactionInResultTable(String expectedAmount) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(transactionTableRows));

            // ajax read
            Thread.sleep(2000);

            List<WebElement> rows = driver.findElements(transactionTableRows);

            for (WebElement row : rows) {
                // contains our sum
                if (row.getText().contains(expectedAmount)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}