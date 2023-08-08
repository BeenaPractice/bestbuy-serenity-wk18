package com.bestbuy.crudtest;

import com.bestbuy.bestbuyinfo.StoresSteps;
import com.bestbuy.constants.Path;
import com.bestbuy.testbase.TestBaseStore;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends TestBaseStore {
    @Before
    public void setUp() {
        RestAssured.basePath = Path.STORES;

    }
    static String name = "Robert" + TestUtils.getRandomValue();
    static String type = "COM" + TestUtils.getRandomValue();
    static String address = "Bury Avenue" + TestUtils.getRandomValue();
    static String address2 = "Watford" + TestUtils.getRandomValue();
    static String city = "London" + TestUtils.getRandomValue();
    static String state = "MS" + TestUtils.getRandomValue();
    static String zip = "NN3 4SS" + TestUtils.getRandomValue();
    static int lat = 567 + TestUtils.getRandomNumber();
    static int lng = 1456 + TestUtils.getRandomNumber();
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storesId = 7;
    @Steps
    StoresSteps storesSteps;
    // get request
    @Title("This will get all stores list")
    @Test
    public void getStoresList(){
        storesSteps.getAllStores()
                .log().all()
                .statusCode(200);
    }
    @Title("This will get single stores information")
    @Test
    public void getSingleStore(){
        storesSteps.getSingleStoreById(4)
                .log().all()
                .statusCode(200);
    }
    // create new store
    @Title("This will create a new store")
    @Test
    public void createNewStore(){
        storesSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours)
                .log().all()
                .statusCode(201);
    }
    @Title("Create and verify the stores was added to the application")
    @Test
    public void createAndVerifyStore(){
        HashMap<String,Object> storesValue =
        storesSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours)
                .log().all()
                .statusCode(201)
                .extract()
                .body().jsonPath().get();
        System.out.println(storesValue);
        assertThat(storesValue,hasValue(name));
        storesId = (int) storesValue.get("id");
    }
    @Title("Update stores information and verify updated information")
    @Test

    public void updateStoreAndVerify(){
        name = name + "_Update";
        HashMap<String,Object> storesValue =
                storesSteps.updateStores(storesId,name,type,address,address2,city,zip,state,lat,lng,hours)
                        .statusCode(200)
                        .extract()
                        .body().jsonPath().get();
        assertThat(storesValue,hasValue(name));
        System.out.println(storesValue);

    }
    // delete request
    @Title("This will delete store")
    @Test
    public void deleteStoreInformation(){
        storesSteps.deleteStore(4).statusCode(200);
        storesSteps.getSingleStoreById(4).statusCode(404);

    }


}


