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
import products.jewellery.JewelleryBuilder;
import products.painting.Painting;
import products.painting.PaintingBuilder;

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
        productList.add(new FurnitureBuilder().withId(50).withName("GlassTableCarmen").withSellingPrice(-1).withMinimPrice(600).withYear(2017).withType("table").withMaterial("GlassandSteel").build());
        productList.add(new FurnitureBuilder().withId(51).withName("CouchGoodNight").withSellingPrice(-1).withMinimPrice(500).withYear(2016).withType("Sitting").withMaterial("Textile").build());
        productList.add(new FurnitureBuilder().withId(52).withName("BedGoodNight").withSellingPrice(-1).withMinimPrice(900).withYear(2019).withType("Sleeping").withMaterial("wood").build());
        productList.add(new FurnitureBuilder().withId(59).withName("ActiveTennistable").withSellingPrice(-1).withMinimPrice(650).withYear(2017).withType("entertainment").withMaterial("steelandwood").build());
        productList.add(new FurnitureBuilder().withId(61).withName("SCAUN").withSellingPrice(-1).withMinimPrice(200).withYear(2015).withType("dormitor").withMaterial("lemn").build());
        productList.add(new JewelleryBuilder().withId(47).withName("BraceletDiesel").withSellingPrice(-1).withMinimPrice(120).withYear(1930).withMaterial("Leather").withGemstone(false).build());
        productList.add(new JewelleryBuilder().withId(53).withName("ringPandora").withSellingPrice(-1).withMinimPrice(300).withYear(2017).withMaterial("silver").withGemstone(true).build());
        productList.add(new JewelleryBuilder().withId(54).withName("BoysPoliceNecklage").withSellingPrice(-1).withMinimPrice(200).withYear(2020).withMaterial("StainlessSteel").withGemstone(false).build());
        productList.add(new JewelleryBuilder().withId(55).withName("DieselRingBoys").withSellingPrice(-1).withMinimPrice(150).withYear(2019).withMaterial("StainlessSteel").withGemstone(false).build());
        productList.add(new JewelleryBuilder().withId(58).withName("BBNecklace").withSellingPrice(-1).withMinimPrice(500).withYear(2020).withMaterial("Silver").withGemstone(true).build());
        productList.add(new JewelleryBuilder().withId(60).withName("RolexWatch").withSellingPrice(-1).withMinimPrice(2000).withYear(2020).withMaterial("Swarovski").withGemstone(true).build());
        productList.add(new PaintingBuilder().withId(46).withName("MonaLisa").withSellingPrice(-1).withMinimPrice(2000).withYear(1870).withNameArtist("LeonardoDaVinci").withColors(Painting.Colors.tempera).build());
        productList.add(new PaintingBuilder().withId(48).withName("CarulCuBoi").withSellingPrice(-1).withMinimPrice(420).withYear(1920).withNameArtist("NicolaeGrigorescu").withColors(Painting.Colors.acrylic).build());
        productList.add(new PaintingBuilder().withId(49).withName("SelfPortrait").withSellingPrice(-1).withMinimPrice(3000).withYear(1850).withNameArtist("VanGogh").withColors(Painting.Colors.oil).build());
        productList.add(new PaintingBuilder().withId(56).withName("Guernica").withSellingPrice(-1).withMinimPrice(5000).withYear(1940).withNameArtist("PabloPicasso").withColors(Painting.Colors.tempera).build());
        productList.add(new PaintingBuilder().withId(57).withName("StarryNight").withSellingPrice(-1).withMinimPrice(300).withYear(1882).withNameArtist("VanGogh").withColors(Painting.Colors.oil).build());
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
