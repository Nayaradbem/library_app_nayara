package nayara_library.pages;

import nayara_library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BooksPage extends BasePage {

    public BooksPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//table/tbody/tr/td")
    public List<WebElement> allRows;
}
