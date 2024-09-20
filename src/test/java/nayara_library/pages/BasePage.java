package nayara_library.pages;

import nayara_library.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    public BasePage(){

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//a[@href='#books']")
    public WebElement booksModule;

    @FindBy(xpath = "//a[@href='#users']")
    public WebElement userModule;

    @FindBy(xpath = "//a[@href='#dashboard']")
    public WebElement dashboardModule;

    @FindBy(css = "#navbarDropdown>span")
    public WebElement accountHolderName;

    public void navigateTo(String module){

        switch (module){
            case "books" :
                    booksModule.click();
                    break;
            case "users" :
                    userModule.click();
                    break;
            case "dashborad" :
                dashboardModule.click();
                break;

        }
    }


}
