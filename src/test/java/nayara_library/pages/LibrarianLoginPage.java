package nayara_library.pages;

import nayara_library.utilities.ConfigurationReader;
import nayara_library.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LibrarianLoginPage extends BasePage {

    public LibrarianLoginPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "inputEmail")
    public WebElement emailBox;

    @FindBy(id = "inputPassword")
    public WebElement passwordBox;

    @FindBy(tagName = "button")
    public WebElement signInButton;

    public void login(String email, String password) throws InterruptedException {
        Thread.sleep(4000);
        emailBox.sendKeys(email);
        passwordBox.sendKeys(password);
        signInButton.click();
    }
    public void login(String user) throws InterruptedException {

        switch (user){
            case "user":
                emailBox.sendKeys(ConfigurationReader.getProperty("student_username"));
                passwordBox.sendKeys(ConfigurationReader.getProperty("student_password"));
                signInButton.click();
                break;
            case "librarian":
                login(ConfigurationReader.getProperty("librarian_username"), ConfigurationReader.getProperty("librarian_password"));
                break;
        }
    }

}
