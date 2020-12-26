package model.bean;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void addNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Product nullProduct = null;
            Cart testCart = new Cart(testUser);
            testCart.addProduct(nullProduct,12);
        });
    }

    @Test
    void addNotValidQuantityProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Operator testOperator = new Operator();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
            testOrder.addProduct(toAddProduct, 0);
        });
    }

    @Test
    void addNotValidQuantityProduct2() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Operator testOperator = new Operator();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
            testOrder.addProduct(toAddProduct, null);
        });
    }


    @Test
    void addNewProduct() {
        User testUser = new User();
        Operator testOperator = new Operator();
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
        Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
        testOrder.addProduct(toAddProduct, 10);
        assertEquals(true, testOrder.contains(toAddProduct.getId()));
    }

    @Test
    void addProductAlreadyIn() {
        User testUser = new User();
        Operator testOperator = new Operator();
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
        Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
        testOrder.addProduct(toAddProduct, 10);
        testOrder.addProduct(toAddProduct,4);
        assertEquals(14, testOrder.getQuantitySingleProduct(toAddProduct.getId()));
    }

    @Test
    void removeNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Operator testOperator = new Operator();
            Product nullProduct = null;
            Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
            testOrder.removeProduct(nullProduct, 12);
        });
    }

    @Test
    void removeProductNotContent() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Operator testOperator = new Operator();
            Product notContentProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
            testOrder.removeProduct(notContentProduct, 12);
        });
    }

    @Test
    void removeQuantityNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Operator testOperator = new Operator();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
            testOrder.addProduct(toAddProduct,6);
            testOrder.removeProduct(toAddProduct, null);
        });
    }

    @Test
    void removeZeroQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Operator testOperator = new Operator();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
            testOrder.addProduct(toAddProduct,6);
            testOrder.removeProduct(toAddProduct, 0);
        });
    }

    @Test
    void removeLotQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Operator testOperator = new Operator();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
            testOrder.addProduct(toAddProduct,6);
            testOrder.removeProduct(toAddProduct, 7);
        });
    }

    @Test
    void removeSomeItemsQuantity() {
        User testUser = new User();
        Operator testOperator = new Operator();
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
        Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
        testOrder.addProduct(toAddProduct,9);
        testOrder.removeProduct(toAddProduct, 7);
        assertEquals(testOrder.getQuantitySingleProduct(toAddProduct.getId()),2);
    }

    @Test
    void removeItemsQuantity() {
        User testUser = new User();
        Operator testOperator = new Operator();
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
        Order testOrder = new Order(57, testUser, testOperator, "21-12-2020");
        testOrder.addProduct(toAddProduct,9);
        testOrder.removeProduct(toAddProduct, 9);
        //assert verificano che è stato tolto da entrambe le liste
        assertAll(
                () -> assertEquals(testOrder.contains(toAddProduct.getId()),false),
                () -> assertEquals(null,testOrder.getQuantitySingleProduct(toAddProduct.getId()))
        );
    }



}