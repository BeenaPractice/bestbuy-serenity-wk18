package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class StoresSteps {

    // Get all stores

    public ValidatableResponse getAllStores(){
        return SerenityRest.rest()
                .given()
                .when()
                .get()
                .then();
    }

    // get single store with id
    @Step("Get single Store with id : {0}")
    public ValidatableResponse getSingleStoreById(int storesId){
        return SerenityRest.rest()
                .given()
                .pathParam("storesId",storesId)
                .when()
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then();
    }
    //Post request
    @Step
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city, String state, String zip, int lat, int lng, String hours){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);


        return SerenityRest.rest()
                .given()
                .header("Content-Type","application/json")
                .body(storePojo)
                .when()
                .post()
                .then();
    }
    // put request
    @Step
    public ValidatableResponse updateStores(int storesId,String name, String type, String address, String address2, String city, String state, String zip, int lat, int lng, String hours){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);

        return SerenityRest.rest()
                .given()
                .pathParam("storesId",storesId)
                .header("Content-Type","application/json")
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }

    // Delete request

    @Step
    public ValidatableResponse deleteStore(int storesId){
        return SerenityRest.rest()
                .given()
                .pathParam("storesId",storesId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();

    }
}
