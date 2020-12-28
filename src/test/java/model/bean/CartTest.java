package model.bean;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

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
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Cart testCart = new Cart(testUser);
            testCart.addProduct(toAddProduct, 0);
        });
    }

    @Test
    void addNotValidQuantityProduct2() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Cart testCart = new Cart(testUser);
            testCart.addProduct(toAddProduct, null);
        });
    }

    @Test
    void getProductQuantityPresent(){
        Cart c = new Cart(
                new User(
                        "prova", "provaN", "provaS", "provaA", "provaC",
                        "provaC2", "12-12-1999", "mail@s.com", 'M',
                        "33342121212"
                        )
        );
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56,
                "Descrizione", "path immagine ", new ArrayList<>(), new ArrayList<>(),
                15, "grandezza", 5);
        c.addProduct(toAddProduct, 2);
        assertEquals(c.getQuantitySingleProduct(toAddProduct.getId()), 2);
    }

    @Test
    void getProductQuantityNotPresent(){
        Cart c = new Cart(
                new User(
                        "prova", "provaN", "provaS", "provaA", "provaC",
                        "provaC2", "12-12-1999", "mail@s.com", 'M',
                        "33342121212"
                )
        );
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56,
                "Descrizione", "path immagine ", new ArrayList<>(), new ArrayList<>(),
                15, "grandezza", 5);
        c.addProduct(toAddProduct, 2);
        assertEquals(c.getQuantitySingleProduct(53), 0);
    }

    @Test
    void addNewProduct() {
        User testUser = new User();
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56,
                "Descrizione", "path immagine ", new ArrayList<>(), new ArrayList<>(),
                15, "grandezza", 5);
        Cart testCart = new Cart(testUser);
        testCart.addProduct(toAddProduct, 10);
        assertTrue(testCart.contains(toAddProduct.getId()));
    }

    @Test
    void addProductAlreadyIn() {
        User testUser = new User();
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56,
                "Descrizione", "path immagine ", new ArrayList<>(), new ArrayList<>(), 15,
                "grandezza", 5);
        Cart testCart = new Cart(testUser);
        testCart.addProduct(toAddProduct, 10);
        testCart.addProduct(toAddProduct,4);
        assertEquals(14, testCart.getQuantitySingleProduct(toAddProduct.getId()));
    }


    @Test
    void removeNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Product nullProduct = null;
            Cart testCart = new Cart(testUser);
            testCart.removeProduct(nullProduct, 12);
        });
    }

    @Test
    void removeProductNotContent() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Product notContentProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56,
                    "Descrizione", "path immagine ", new ArrayList<>(), new ArrayList<>(),
                    15, "grandezza", 5);
            Cart testCart = new Cart(testUser);
            testCart.removeProduct(notContentProduct, 12);
        });
    }

    @Test
    void removeQuantityNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Cart testCart = new Cart(testUser);
            testCart.addProduct(toAddProduct,6);
            testCart.removeProduct(toAddProduct, null);
        });
    }

    @Test
    void removeZeroQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Cart testCart = new Cart(testUser);
            testCart.addProduct(toAddProduct,6);
            testCart.removeProduct(toAddProduct, 0);
        });
    }

    @Test
    void removeLotQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            User testUser = new User();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                    "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
            Cart testCart = new Cart(testUser);
            testCart.addProduct(toAddProduct,6);
            testCart.removeProduct(toAddProduct, 7);
        });
    }

    @Test
    void removeSomeItemsQuantity() {
            User testUser = new User();
            Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56,
                    "Descrizione", "path immagine ", new ArrayList<>(), new ArrayList<>(),
                    15, "grandezza", 5);
            Cart testCart = new Cart(testUser);
            testCart.addProduct(toAddProduct,9);
            testCart.removeProduct(toAddProduct, 7);
            assertEquals(testCart.getQuantitySingleProduct(toAddProduct.getId()),2);
    }

    @Test
    void removeItemsQuantity() {
        User testUser = new User();
        Product toAddProduct = new PhysicalProduct( 54 ,"Prodotto prova", 55.56, "Descrizione",
                "path immagine ", new ArrayList<>(), new ArrayList<>(), 15, "grandezza", 5);
        Cart testCart = new Cart(testUser);
        testCart.addProduct(toAddProduct,9);
        testCart.removeProduct(toAddProduct, 9);
        //assert verificano che è stato tolto da entrambe le liste
        assertAll(
            () -> assertFalse(testCart.contains(toAddProduct.getId())),
            () -> assertEquals(0, testCart.getQuantitySingleProduct(toAddProduct.getId()))
        );
    }

}