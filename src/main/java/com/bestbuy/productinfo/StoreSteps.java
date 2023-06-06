package com.bestbuy.productinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps{

    @Step("Creating store with name : {0}, type : {1}, address : {2}, address2 : {3} , city : {4},state :{5},state : {6},zip :{7},lat :{8},lng :{9},hours  :{10}")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city, String state, String zip, int lat, int lng, String hours
    ) {
        StorePojo storePojo = StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .body(storePojo)
                    .post()
                .then()
                    .statusCode(201);
    }

    @Step("Getting the store information with id : {0}")
    public HashMap<String, Object> getStoreInfoById(int storeID) {
        String s1 = "data.findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then().statusCode(200)
                .extract()
                .path(s1 + storeID + s2);
    }

    @Step("Update Products with name : {0}")
    public ValidatableResponse updateStore(int storeID, String name) {
        StorePojo storesPojo = new StorePojo();
        storesPojo.setName(name);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .pathParam("storeId", storeID)
                .when()
                .patch(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }


    @Step("Updating store information with storeId: {0}, name : {1}, type : {2}, price : {2}, shipping : {3} , upc : {4},description :{5},manufacturer : {6},model :{7},url :{8},image :{9}")
    public ValidatableResponse updateStore(int storeId, String name, String type, String address, String address2, String city, String state, String zip, int lat, int lng, String hours) {
        StorePojo storePojo = StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("storeId", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }


    @Step("Deleting store information with storeId: {0}")
    public ValidatableResponse deleteStore(int storeId) {
        return SerenityRest.given().log().all()
                .pathParam("storeId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }

    @Step("Getting single store with storeId: {0}")
    public ValidatableResponse getStoreByID(int storeId) {
        return SerenityRest.given().log().all()
                .pathParam("storeId", storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }

    @Step("Getting all store information")
    public ValidatableResponse getAllStoreInfo() {
        return SerenityRest.given()
                .when().get(EndPoints.GET_ALL_STORES)
                .then();
    }


}

