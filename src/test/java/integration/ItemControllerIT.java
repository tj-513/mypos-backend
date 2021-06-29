package integration;

import integration.entities.Item;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = {com.bootcamp.mypos.mypos.MyposApplication.class})
public class ItemControllerIT {

    @LocalServerPort
    int port;
    Item item1 = new Item();

    @Before
    public void setUp() {
        RestAssured.port = port;
        // create dummy items

        item1.amountAvailable = 5;
        item1.itemName = "apple";
        item1.unitPrice = 50;


        Item item2 = new Item();
        item2.amountAvailable = 5;
        item2.itemName = "orange";
        item2.unitPrice = 50;

        Item item3 = new Item();
        item3.amountAvailable = 5;
        item3.itemName = "mango";
        item3.unitPrice = 50;

        given().contentType("application/json").body(item1).when().post("/api/items");
        given().contentType("application/json").body(item2).when().post("/api/items");
        given().contentType("application/json").body(item3).when().post("/api/items");
    }

    @Test
    public void shouldGetItemInfoSuccessfully() throws InterruptedException {
        given().get("/api/items/2").then().statusCode(200);
    }

    @Test
    public void shouldAddItemSuccessfully() {
        Item newItem = new Item();
        newItem.amountAvailable = 5;
        newItem.itemName = "cocoa";
        newItem.unitPrice = 50;
        given()
                .contentType("application/json")
                .body(newItem)
                .when()
                .post("/api/items")
                .then()
                .body("$", hasKey("id"))
                .statusCode(201);
    }

    @Test
    public void shouldUpdateItemDetailsSuccessfully() {
        Item newItem = new Item();
        newItem.id = 1;
        newItem.amountAvailable = 5;
        newItem.itemName = "coconut";
        newItem.unitPrice = 50;
        given()
                .contentType("application/json")
                .body(newItem)
                .when()
                .put("/api/items")
                .then()
                .body("itemName", is("coconut"))
                .statusCode(200);
    }

    @Test
    public void shouldDeleteItemSuccessfully() throws InterruptedException {
        given().delete("/api/items/3").then().statusCode(200);
    }

    @Test
    public void shouldNotAddExistingItems() {
        Item newItem = new Item();
        newItem.amountAvailable = 5;
        newItem.itemName = "orange";
        newItem.unitPrice = 50;
        given()
                .contentType("application/json")
                .body(newItem)
                .when()
                .put("/api/items")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldNotUpdateItemIfItemDoesNotExist() {
        Item newItem = new Item();
        newItem.id = 1000;
        newItem.amountAvailable = 5;
        newItem.itemName = "orange";
        newItem.unitPrice = 50;
        given()
                .contentType("application/json")
                .body(newItem)
                .when()
                .put("/api/items")
                .then()
                .statusCode(400);
    }

}
