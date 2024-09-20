package nayara_library.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import nayara_library.utilities.ConfigurationReader;
import nayara_library.utilities.DataBase;
import nayara_library.utilities.Driver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.xml.crypto.Data;
import java.util.concurrent.TimeUnit;

public class Hooks {

    @Before()
    public void setBaseURI(){
        RestAssured.baseURI=ConfigurationReader.getProperty("apiUrl");
    }

    @Before ("@ui")
    public void setUp(){
        Driver.getDriver().get(ConfigurationReader.getProperty("appURL"));
    }

    @After("@ui")
    public void tearDown(Scenario scenario){

        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","screenshot");
        }

        Driver.closeDriver();

    }

    @Before("@db")
    public void setupDB(){
        System.out.println("Connecting to database...");
        DataBase.createConnection(ConfigurationReader.getProperty("library2.db.url"), ConfigurationReader.getProperty("library2.db.username"), ConfigurationReader.getProperty("library2.db.password"));
    }

    @After("@db")
    public void closeDB(){
        System.out.println("Closing DB connection...");
        DataBase.destroy();
    }
}
