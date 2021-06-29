package integration;

import integration.entities.User;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = {com.bootcamp.mypos.mypos.MyposApplication.class})
public class UserControllerIT {

    @LocalServerPort
    int port;
    User user1 = new User();

    @Before
    public void setUp() {
        RestAssured.port = port;
        // create dummy users

        user1.username = "user1";
        user1.address = "01, Katubedda, Moratuwa";
        user1.email = "user1@gmail.com";
        user1.firstName = "User1";
        user1.lastName = "Kenobi";
        user1.password = "asdfasdf";

        User user2 = new User();
        user2.username = "user2";
        user2.address = "02, Katubedda, Moratuwa";
        user2.email = "user2@gmail.com";
        user2.firstName = "User2";
        user2.lastName = "Kenobi";
        user2.password = "asdfasdf";

        User user3 = new User();
        user3.username = "user3";
        user3.address = "02, Katubedda, Moratuwa";
        user3.email = "user3@gmail.com";
        user3.firstName = "User3";
        user3.lastName = "Kenobi";
        user3.password = "asdfasdf";

        given().contentType("application/json").body(user1).when().post("/api/users");
        given().contentType("application/json").body(user2).when().post("/api/users");
        given().contentType("application/json").body(user3).when().post("/api/users");
    }

    @Test
    public void shouldGetUserDetailsSuccessfully() throws InterruptedException {
        given().get("/api/users/1").then().statusCode(200);
    }

    @Test
    public void shouldCreateUserSuccessfully() {
        User newUser = new User();
        newUser.username = "person";
        newUser.email = "createperson@gamil.com";
        newUser.firstName = "mark";
        newUser.password = "xxx";
        given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .post("/api/users")
                .then()
                .body("$", hasKey("id"))
                .statusCode(201);
    }

    @Test
    public void shouldUpdateUserSuccessfully() {
        User newUser = new User();
        newUser.userId = 1;
        newUser.username = "updatedPerson";
        newUser.email = "person@gamil.com";
        newUser.firstName = "mark";
        newUser.password = "xxx";
        given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .put("/api/users")
                .then()
                .body("username", is("updatedPerson"))
                .statusCode(200);
    }

    @Test
    public void shouldDeleteUserSuccessfully() throws InterruptedException {
        given().delete("/api/users/2").then().statusCode(200);
    }

    @Test
    public void shouldNotCreateUserIfUsernameExists() {
        User newUser = new User();
        newUser.userId = 100;
        newUser.username = "user2";
        newUser.email = "person@gamil.com";
        newUser.firstName = "mark";
        newUser.password = "xxx";
        given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .put("/api/users")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldNotUpdateUserIfUserDoesNotExist() {
        User newUser = new User();
        newUser.userId = 100;
        newUser.username = "updatedPerson";
        newUser.email = "person@gamil.com";
        newUser.firstName = "mark";
        newUser.password = "xxx";
        given()
                .contentType("application/json")
                .body(newUser)
                .when()
                .put("/api/users")
                .then()
                .statusCode(400);
    }

}
