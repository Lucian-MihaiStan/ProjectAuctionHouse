# OOP-Royal Auction House Project
##### realized by Stan Lucian-Mihai
###### University Politehnica of Bucharest 2nd year Computer Science 321CB 


    Empashis of this project is to simulate the functionalities and the algorithm of auction house
   
    Main focuses of this projects are
    - multithreading
    - genericity
    - design patterns
    - unit testing
    - SOLID principles
    - socket server
    - mysql database

### Running
The main program starts with running of server client (Main class).
This class must run from the beginning of the execution to the end, else the other clients will crash.

##### Running details
Second step to run this program is to open a client main (ClientMain class) in a terminal for each user who wants to have access to the auction house data.
Each user can write their commands, and the algorithm will execute their requirements. 

At the start of the main program running the auction house is loaded local in memory from a database and will generate a random number of brokers, auctions, based on id products (auction id is the same with product id).
Each client can find and display information about products that exist in the auction house store and can put a bid for a product.
Each client can participate to as many auctions as he wants.

##### Implementation details
The client must be sure that he will have enough money to pay randomly associated broker depending on type of user and number of participation at auctions.
The commissions are not huge but brokers should have a salary.
The algorithm doesn't allow a user to enroll to the same auction two times.

### Run and Commands
Each command should be followed by a command EXECUTE if the client wants to see the message displayed
    
    The run of a new client main should start with login <username> <password> 
    
Users' privileges are different from the privileges of administrator.
Admin and brokers are the only entities that can edit products list.

### Commands for basic users
    SHOW_AUCTIONS - display information about the active auctions from the store
    LIST_PRODUCTS - display information about the products from the store
    ENROLL_AUCTION <id> <max_bid> - enroll to the auction with <id> and the max bid payed will be <max_bid>
    SHOW - show username of the current user
    CREATE_USER - create a new user

### Commands for administrators
    LIST_BROKERS - display infromation about the brokers from the auction house, their ammount of commmission gained
    LIST_USERS - display information about all users that have account to the auction house
    ADD_PRODUCT - add a new product to the list
    DELETE_PRODUCT - delete a product specified by id
    
When an auction reaches the maximum number of participants the auction starts, bids will be chosen randomly and if there is a winner he will be notified on the email.
Also, each broker enrolled to this auction will be notified on the email.
Each user will be notified with the result of the auction. 
Brokers will be paid at the end of auction, even there is no winner.

### ========= Design Patterns =========

#### Adapters
Adapters used to load data from a database, to notify users about auction

#### Commander
Used to structure the program commands better

#### Singleton
To be sure that there is only one auction house object and one administrator

#### Builder
Used to re-structure the new instance of a product, user, command better

#### Factory
Used to create and calculate commission

#### Strategy 
Used to create and choose the strategy of a user at a specific step in an auction.