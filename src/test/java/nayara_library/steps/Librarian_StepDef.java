package nayara_library.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import nayara_library.pages.BooksPage;
import nayara_library.pages.LibrarianLoginPage;
import nayara_library.utilities.DataBase;
import nayara_library.utilities.LibraryAPI;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class Librarian_StepDef {
    RequestSpecification givenPart = RestAssured.given().log().uri();
    Response response;
    ValidatableResponse thenPart;
    JsonPath jp;

    LibrarianLoginPage loginPage = new LibrarianLoginPage();

    @Given("I logged Library api as a {string}") //librarian
    public void i_logged_library_api_as_a(String role) {
        givenPart.header("x-library-token", LibraryAPI.getToken(role));

    }
    @Given("Accept header is {string}")
    public void accept_header_is(String headerContentType) {
        givenPart.accept(headerContentType);

    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_get_all_users_endpoint(String endPoint) {

        System.out.println(endPoint);
        response = givenPart.when().get(endPoint);
        thenPart = response.then();

    }
    @Then("status code should be {int}")
    public void status_code_should_be(Integer int1) {

        thenPart.statusCode(int1);
    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        thenPart.contentType(contentType);
    }


    @Then("{string} field should not be null")
    public void field_should_not_be_null(String field) {

        jp = thenPart.extract().jsonPath();
        System.out.println(jp.getString(field));
        Assert.assertNotNull(field);
    }
    String pathParam;
    @Given("Path param {string} is {string}")
    public void path_param_is(String id,String pathParam) {

        this.pathParam = pathParam;
        givenPart.pathParam(id,pathParam);
        System.out.println(pathParam);
    }

    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String fild) {

        jp = thenPart.extract().jsonPath();
        String actualFiled = jp.getString(fild);
        Assert.assertEquals(actualFiled,pathParam);
    }

    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> filds) {
        for (String fild : filds) {
            Assert.assertNotNull(fild);
        }
    }

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentTypeHeader) {
        givenPart.contentType(contentTypeHeader);
    }

    Map<String, Object> requestBody;
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String name) {
        switch (name) {
            case "book":
                requestBody = LibraryAPI.getRandomBookMap();
                givenPart.formParams(requestBody);
                break;
            case "user":
                requestBody = LibraryAPI.getRandomUserMap();
                givenPart.formParams(requestBody);
        }
        System.out.println(requestBody);

    }

    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endPoint) {
        response = givenPart.when().post(endPoint);
        thenPart = response.then();
        System.out.println(response.statusCode());
        response.prettyPrint();
    }

    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String message, String sentence) {
        thenPart.body(message, Matchers.is(sentence));
    }

    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String librarian) throws InterruptedException {
        loginPage.login(librarian);
    }

    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String string) {
        loginPage.navigateTo(string);
    }
    BooksPage booksPage = new BooksPage();
    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {
        //retrieve the isbn to check data base
        DataBase.runQuery("select name, isbn, year, author, book_category_id, description\n" +
                "from books\n" +
                "where id =" + pathParam);
        List<Map<String, Object>> listOfColumn = DataBase.getAllRowAsListOfMap();// all columns

        for (Map<String, Object> eachData : listOfColumn) { //(name, nayara)

            Assert.assertEquals(eachData, requestBody);
        }
        //check if book with specific isbn is in the webPage
        loginPage.navigateTo("books");
        for(String key : requestBody.keySet()) {
            for (WebElement row : booksPage.allRows) {
                if (row.equals(requestBody.get(key))) //"requestBody.get("isbn") -> sequen....
                    Assert.assertNotNull(row);
            }
        }
    }
    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {
        DataBase.runQuery("select name, isbn, year, author, book_category_id, description\n" +
                "from books\n" +
                "where id =" + pathParam);
        List<Map<String, Object>> listOfColumn = DataBase.getAllRowAsListOfMap();// all columns

        for (Map<String, Object> eachData : listOfColumn) { //(name, nayara)

            Assert.assertEquals(eachData, requestBody);
        }

    }
    @Then("created user should be able to login Library UI")
    public void created_user_should_be_able_to_login_library_ui() throws InterruptedException {
        String email = (String)requestBody.get("email");
        String password = (String)requestBody.get("password");
        loginPage.login(email, password);
    }
    @Then("created user name should appear in Dashboard Page")
    public void created_user_name_should_appear_in_dashboard_page() {
        loginPage.navigateTo("dashboard");
        loginPage.accountHolderName.equals(requestBody.get("full_name"));
    }
    String email;
    String password;
    @Given("I logged Library api with credentials {string} and {string}")
    public void i_logged_library_api_with_credentials_and(String string, String string2) {
        this.email = string;
        this.password = string2;
        givenPart.formParam("email", string);
        givenPart.formParam("password", string2);
    }
    @Given("I send token information as request body")
    public void i_send_token_information_as_request_body() {

        // not passing with student1@library
        givenPart.formParam("token", LibraryAPI.getToken(email, password));
    }
}
