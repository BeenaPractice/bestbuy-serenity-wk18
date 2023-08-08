package com.bestbuy.crudtest;

import com.bestbuy.bestbuyinfo.ProductSteps;
import com.bestbuy.constants.Path;
import com.bestbuy.testbase.TestBaseProduct;
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
public class ProductCRUDTest extends TestBaseProduct {

    static String name="Choco Lava biscuits"+ TestUtils.getRandomValue();
    static String type="Cookies"+TestUtils.getRandomValue();
    static double price=5.99;
    static String upc="0413334"+TestUtils.getRandomValue();
    static int shipping=1+TestUtils.getRandomNumber();
    static String description="Chocolaty and Testy ; 5-pack"+TestUtils.getRandomValue();
    static String manufacturer="CadBury"+TestUtils.getRandomValue();
    static String model=" ";
    static String url="http://www.bestbuy.com/site/macvities biscuit-4-pack/43900.p?id=1059384074145&skuId=43900&cmp=RMXCC";
    static String image="http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";
    static int productsId;

    @Before
    public void setUp(){

        RestAssured.basePath= Path.PRODUCTS;
    }

    //Get Request
    @Steps
    ProductSteps productsSteps;
    @Title("This will get all products")
    @Test
    public void getAllProducts() {

        productsSteps.allProducts()
                .log().all()
                .statusCode(200);
    }

    @Title("This will get single product with id")
    @Test
    public void getSingleProduct() {

        productsSteps.getSingleProductsWithId(48530).log().all()
                .statusCode(200);

    }

    @Test
    @Title("This will get products with parameters")
    public void test008() {

        productsSteps.getProductsWithParameters(3,2).log().all()
                .statusCode(200);
    }

    //Create Request
    @Title("This will create new product")
    @Test
    public void test005(){

        productsSteps.createProduct(name,type,price,upc,shipping,description,manufacturer,model,url,image)
                .log().all()
                .statusCode(201);

    }

    @Title("Create and Verify if the products was added to the application")
    @Test
    public void test001() {
        
        HashMap<String,Object> pValue=productsSteps.createProduct(name,type,price,upc,shipping,description,manufacturer,model,url,image)
                .log().all()
                .statusCode(201).extract().response().body().jsonPath().get();
        System.out.println(pValue);
        assertThat(pValue,hasValue(name));
        productsId= (int) pValue.get("id");
        System.out.println(productsId);
    }

    //Put Request
    @Title("Update products information and verify the updated information")
    @Test
    public void test002(){

        name=name+"_Updated";
        HashMap<String,Object> pValue= productsSteps.updateProducts(productsId,name,type,price,upc,shipping,description,manufacturer,model,url,image)
                .statusCode(200).extract().response().body().jsonPath().get();

        assertThat(pValue,hasValue(name));
        System.out.println(pValue);

    }


    //Delete Request
    @Title("Delete products information with id and verify it")
    @Test
    public void test004(){
        productsSteps.deleteProducts(productsId)
                .log().all()
                .statusCode(200);

        productsSteps.getSingleProductsWithId(productsId)
                .statusCode(404);

    }

}
