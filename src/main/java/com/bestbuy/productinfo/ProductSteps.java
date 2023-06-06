package com.bestbuy.productinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step("Creating products : name : {0}, type : {1}, price : {2}, shipping : {3} , upc : {4},description :{5},manufacturer : {6},model :{7},url :{8},image :{9}")
    public ValidatableResponse createANewProduct(String name, String type, Double price, int shipping,
                                              String upc, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model, url, image);

        return SerenityRest
                .given()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post()
                .then();
    }

    @Step("Getting products with id : {0}")
    public HashMap<String, Object> getProductInfoById(int productId) {

        return SerenityRest
                .given()
                    .pathParam("productId",productId)
                .when()
                    .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then()
                    .statusCode(200)
                .extract()
                    .path("");
    }

    @Step("Update product details of id: {0}")
    public ValidatableResponse updateProduct(int productID, String name) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .body(productPojo)
                .pathParam("productId", productID)
                .when()
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }


    @Step("Deleting product information with productId: {0}")
    public ValidatableResponse deleteSingleProduct(int productId) {
        return SerenityRest
                .given().log().all()
                .pathParam("productId", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting product information with productId: {0}")
    public ValidatableResponse getSingleProduct(int productId) {
        return SerenityRest.given().log().all()
                .pathParam("productId", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting all products information")
    public ValidatableResponse getAllProductInfo() {
        return SerenityRest
                .given()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then();
    }


}


