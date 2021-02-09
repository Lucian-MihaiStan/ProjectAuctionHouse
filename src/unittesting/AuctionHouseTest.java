package unittesting;


import auction.Auction;
import auction.AuctionBuilder;
import auction_house.AuctionHouse;
import client.User;
import client.individualperson.IndividualPersonBuilder;
import client.legalperson.LegalPerson;
import client.legalperson.LegalPersonBuilder;
import employee.Admin;
import employee.Broker;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import products.Product;
import products.furniture.Furniture;
import products.furniture.FurnitureBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class AuctionHouseTest {
    private final AuctionHouse auctionHouse = AuctionHouse.getInstance();
    private final MySQLConnection mySQLConnection = new MySQLConnection();
    private final Auction auction = new AuctionBuilder().withId(22).withNoCurrentParticipants(0).withNoParticipants(2).withNoMaxSteps(3).withProductId(22).build();


    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        auctionHouse.load(mySQLConnection);
        mySQLConnection.realizeConnectionAsAdmin();
        Product product = auction.getProductInfo();
        auction.setMaxCurrentBid(50);
        auction.setMinBid(product.getMinimumPrice());
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
    @DisplayName("Commission Test IP")
    @Order(4)
    public void testCommissionIndividualPerson() {
        Broker broker = new Broker(-100);
        assertEquals(20, broker.sumValueCalculator((double) 100, new IndividualPersonBuilder().withNoParticipation(4).build()));
        assertEquals(15, broker.sumValueCalculator((double) 100, new IndividualPersonBuilder().withNoParticipation(10).build()));
    }

    @Test
    @DisplayName("Commission Test LP")
    @Order(5)
    public void testCommissionLegalPerson() {
        Broker broker = new Broker(-100);
        assertEquals(25, broker.sumValueCalculator((double) 100, new LegalPersonBuilder().withNoParticipation(4).build()));
        assertEquals(10, broker.sumValueCalculator((double) 100, new LegalPersonBuilder().withNoParticipation(100).build()));
    }

    @Test
    @DisplayName("Auction not won")
    @Order(6)
    public void testAuctionNotWon() {
        Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients = new HashMap<>();
        List<Triple<User, Double, Double>> listTriple1 = new ArrayList<>();
        List<Triple<User, Double, Double>> listTriple2 = new ArrayList<>();
        listTriple1.add(new ImmutableTriple<>(auctionHouse.getUserList().get(0), (double)50 , (double)50));
        listTriple2.add(new ImmutableTriple<>(auctionHouse.getUserList().get(1), (double)50 , (double)50));
        listTriple2.add(new ImmutableTriple<>(auctionHouse.getUserList().get(2), (double)50 , (double)50));

        brokersAndClients.put(auctionHouse.getBrokers().get(0), listTriple1);
        brokersAndClients.put(auctionHouse.getBrokers().get(1), listTriple2);

        List<User> userList = new ArrayList<>();
        userList.add(auctionHouse.getUserList().get(0));
        userList.add(auctionHouse.getUserList().get(1));
        userList.add(auctionHouse.getUserList().get(2));

        User user = auction.mechanismAuction(brokersAndClients, userList);

        assertNull(user);
    }

    @Test
    @DisplayName("Auction won by user 1")
    @Order(7)
    public void testAuctionWon1() {
        Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients = new HashMap<>();
        List<Triple<User, Double, Double>> listTriple1 = new ArrayList<>();
        List<Triple<User, Double, Double>> listTriple2 = new ArrayList<>();
        listTriple1.add(new ImmutableTriple<>(auctionHouse.getUserList().get(0), (double)50 , (double)50));
        listTriple2.add(new ImmutableTriple<>(auctionHouse.getUserList().get(1), (double)9000 , (double)9000));
        listTriple2.add(new ImmutableTriple<>(auctionHouse.getUserList().get(2), (double)50 , (double)50));

        List<User> userList = new ArrayList<>();
        brokersAndClients.put(auctionHouse.getBrokers().get(1), listTriple1);
        brokersAndClients.put(auctionHouse.getBrokers().get(1), listTriple2);

        userList.add(auctionHouse.getUserList().get(0));
        userList.add(auctionHouse.getUserList().get(1));
        userList.add(auctionHouse.getUserList().get(2));

        User user = auction.mechanismAuction(brokersAndClients, userList);

        assertNotNull(user);
    }

    @Test
    @DisplayName("Auction won by user 2")
    @Order(8)
    public void testAuctionWon2() {
        Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients = new HashMap<>();
        List<Triple<User, Double, Double>> listTriple1 = new ArrayList<>();
        List<Triple<User, Double, Double>> listTriple2 = new ArrayList<>();
        listTriple1.add(new ImmutableTriple<>(auctionHouse.getUserList().get(0), (double)400 , (double)500));
        listTriple2.add(new ImmutableTriple<>(auctionHouse.getUserList().get(1), (double)9000 , (double)9000));
        listTriple2.add(new ImmutableTriple<>(auctionHouse.getUserList().get(2), (double)50 , (double)50));

        List<User> userList = new ArrayList<>();
        brokersAndClients.put(auctionHouse.getBrokers().get(0), listTriple1);
        brokersAndClients.put(auctionHouse.getBrokers().get(1), listTriple2);

        userList.add(auctionHouse.getUserList().get(0));
        userList.add(auctionHouse.getUserList().get(1));
        userList.add(auctionHouse.getUserList().get(2));

        User user = auction.mechanismAuction(brokersAndClients, userList);

        assertNotNull(user);
    }

    @Test
    @DisplayName("Create User")
    @Order(9)
    public void testCreateUser() {
        LegalPerson legalPerson = new LegalPersonBuilder()
                .withUsername("test")
                .withEmail("test@hotmail.com")
                .withFirstName("test")
                .withLastName("test")
                .withAddress("testAddress")
                .withTypeCompany(LegalPerson.TypeCompany.SRL)
                .withSocialCapital(5000)
                .build();
        auctionHouse.addNewClient(legalPerson);
        assertEquals(legalPerson, auctionHouse.getUserList().get(auctionHouse.getUserList().size() - 1));
    }

    @Test
    @DisplayName("Add Product")
    @Order(10)
    public void testAddProduct() {
        Furniture furniture = new FurnitureBuilder()
                .withId(2500)
                .withName("TennisTable")
                .withMinimPrice(650)
                .withYear(2017)
                .withType("Entertainment")
                .withMaterial("SteelAndWood")
                .build();
        Admin.getInstance().addProduct(furniture);
        assertEquals(furniture, auctionHouse.getProductsList().get(auctionHouse.getProductsList().size() - 1));
    }
}
