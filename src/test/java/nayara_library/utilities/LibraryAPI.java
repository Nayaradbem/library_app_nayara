package nayara_library.utilities;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LibraryAPI {

    public static String getToken(String role){


        String email=ConfigurationReader.getProperty(role+"_username");
        String password=ConfigurationReader.getProperty(role+"_password");
        return getToken(email,password);
    }
    public static String getToken(String email,String password){

        return given()
                .contentType(ContentType.URLENC)
                .formParam("email" , email)
                .formParam("password" , password).
                when()
                .post(ConfigurationReader.getProperty("apiUrl")+"/login")
                .prettyPeek()
                .then().statusCode(200)
                .extract().jsonPath().getString("token");
    }

    public static Map<String,Object> getRandomBookMap(){

        Faker faker = new Faker() ;
        Map<String,Object> bookMap = new LinkedHashMap<>();

        String bookName = faker.book().title() + faker.number().numberBetween(0, 10);
        bookMap.put("name", bookName);
        bookMap.put("isbn", faker.code().isbn10()   );
        bookMap.put("year", faker.number().numberBetween(1000,2021)   );
        bookMap.put("author",faker.book().author()   );
        bookMap.put("book_category_id", faker.number().numberBetween(1,15)   );
        bookMap.put("description", faker.chuckNorris().fact() );

        return bookMap ;
    }

    public static Map<String,Object> getRandomUserMap(){

        Faker faker = new Faker() ;
        Map<String,Object> userMap = new LinkedHashMap<>();
        String fullName = faker.name().fullName();
        String email=fullName.substring(0,fullName.indexOf(" "))+"@library";
        System.out.println(email);
        userMap.put("full_name", fullName );
        userMap.put("email", email);
        userMap.put("password", "libraryUser");
        userMap.put("user_group_id",2);
        userMap.put("status", "ACTIVE");
        userMap.put("start_date", "2023-03-11");
        userMap.put("end_date", "2024-03-11");
        userMap.put("address", faker.address().cityName());

        return userMap ;
    }


}
