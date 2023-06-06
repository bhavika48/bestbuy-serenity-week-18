package com.bestbuy.productinfo;

import com.bestbuy.constants.Path;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCURDTest extends TestBase {

    static String name = "Jason" + TestUtils.getRandomValue();
    static String type = "CarWash" + TestUtils.getRandomValue();
    static String address = "7 Bond Road";
    static String address2 = "London,UK";
    static String city = "London";
    static String state = "Check";
    static String zip = "nw9 5ot";
    static int lat = 34;
    static int lng = 23;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9;Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storeID;

    @Steps
    StoreSteps storesSteps;

    @Before
    public void setup(){
        RestAssured.basePath = Path.Store;
    }
    @Title("This test will create a New Store")
    @Test
    public void test001() {
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
        System.out.println(storeID);
    }

    @Title("This test will verify if the Store was added to the application")
    @Test
    public void test002() {
        ValidatableResponse response = storesSteps.getStoreByID(storeID);
        response.statusCode(200);
    }

    @Title("This test will Update the services information")
    @Test
    public void test003() {
        name = name + "_updated";
        ValidatableResponse validatableResponse = storesSteps.updateStore(storeID, name);
        validatableResponse.statusCode(200);
//        HashMap<String, ?> storeList = (HashMap<String, ?>) storesSteps.getAllStoreInfo();
//        Assert.assertThat(storeList, hasValue(name));
//        System.out.println(storeList);
    }

    @Title("This test will Delete the services by ID")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200);

    }


}