package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class UpdateProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By updateContactLink = By.linkText("Update Contact Info");
    private By firstNameField = By.id("customer.firstName");
    private By lastNameField = By.id("customer.lastName");
    private By addressField = By.id("customer.address.street");
    private By cityField = By.id("customer.address.city");
    private By stateField = By.id("customer.address.state");
    private By zipCodeField = By.id("customer.address.zipCode");
    private By phoneField = By.id("customer.phoneNumber");
    private By updateProfileButton = By.xpath("//input[@value='Update Profile']");
    private By successMessage = By.xpath("//h1[@class='title' and text()='Profile Updated']");

    public UpdateProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void updateAllProfileFields(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(updateContactLink)).click();

        // Wait for one field to be visible then fill all required fields
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(addressField).clear();
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).clear();
        driver.findElement(cityField).sendKeys("Skopje");
        driver.findElement(stateField).clear();
        driver.findElement(stateField).sendKeys("Macedonia");
        driver.findElement(zipCodeField).clear();
        driver.findElement(zipCodeField).sendKeys("1000");
        driver.findElement(phoneField).clear();
        driver.findElement(phoneField).sendKeys("070111222");

        driver.findElement(updateProfileButton).click();
    }

    public boolean isUpdateSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}