package unittesting;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import auction_house.AuctionHouse;
import client.User;
import client.individualperson.IndividualPersonBuilder;
import client.legalperson.LegalPersonBuilder;
import loginsql.MySQLConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import products.Product;
import products.furniture.FurnitureBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AuctionHouseTest {
    private final AuctionHouse auctionHouse = AuctionHouse.getInstance();
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        auctionHouse.load(mySQLConnection);
        mySQLConnection.realizeConnectionAsAdmin();
    }

    @Test
    @DisplayName("Test reading product")
    @Order(1)
    public void testReadingProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new FurnitureBuilder().withId(22).withName("Biblioteca").withSellingPrice(-1).withMinimPrice(200).withYear(2000).withType("dormitor").withMaterial("lemn").build());
        productList.add(new FurnitureBuilder().withId(42).withName("WARDROBE").withSellingPrice(-1).withMinimPrice(100).withYear(1990).withType("storage").withMaterial("wood").build());
        productList.add(new FurnitureBuilder().withId(44).withName("BIROU").withSellingPrice(-1).withMinimPrice(300).withYear(2020).withType("DORMITOR").withMaterial("PLASTIC").build());
        productList.add(new FurnitureBuilder().withId(45).withName("BiliardTableJack").withSellingPrice(-1).withMinimPrice(1000).withYear(2020).withType("entertaiment").withMaterial("wood").build());
        assertEquals(productList, auctionHouse.getProductsList());
    }

    @Test
    @DisplayName("Test reading clients")
    @Order(2)
    public void testReadingClients() {
        List<User> userList = new ArrayList<>();
        userList.add(new IndividualPersonBuilder().withUsername("lucibig").withEmail("luci_stan@yahoo.com").build());
        userList.add(new IndividualPersonBuilder().withUsername("lucianmihai").withEmail("stanlucianmihai@yahoo.com").build());
        userList.add(new IndividualPersonBuilder().withUsername("luciferMKS").withEmail("stanlucianmihai@gmail.com").build());
        userList.add(new IndividualPersonBuilder().withUsername("client1").withEmail("client1@gmail.com").build());
        userList.add(new IndividualPersonBuilder().withUsername("client2").withEmail("client2@gmail.com").build());
        userList.add(new IndividualPersonBuilder().withUsername("amot21").withEmail("andreiitomaa@gmail.com").build());
        userList.add(new IndividualPersonBuilder().withUsername("cristismecheru").withEmail("alexandruilie2000@gmail.com").build());
        userList.add(new LegalPersonBuilder().withUsername("Cristian").withEmail("cristian@gmail.com").build());
        assertEquals(userList, auctionHouse.getUserList());
    }

    @Test
    @DisplayName("Test number of brokers")
    @Order(3)
    public void testNumberOfBrokers() {
        assertTrue(auctionHouse.getBrokers().keySet().size() >= 2);
    }

    @Test
    @DisplayName("Commission Test")
    @Order(4)
    public void testCommission() {

    }
}
