import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// --- 1. OBSERVER PATTERN ---
// Defines the interfaces for real-time notifications

     // Observer interface - implemented by any class that needs to be notified

    interface Observer {
        void update(Subject subject);
    }

     // Subject interface - implemented by any class that needs to be "watched"

    interface Subject {
        void attach(Observer o);
        void detach(Observer o);
        void notifyObservers();
    }


     // Order class - when its status changes it notifies all attached observers

    class Order implements Subject {
        private String orderId;
        private String status;
        private Listing listing;
        private User buyer;
        private List<Observer> observers = new ArrayList<>();

        public Order(Listing listing, User buyer) {
            this.orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
            this.listing = listing;
            this.buyer = buyer;
            this.status = "PENDING_AGREEMENT";
        }

        public String getStatus() {
            return status;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setStatus(String newStatus) {
            this.status = newStatus;
            System.out.println("\n--- Order " + orderId + " status changed to: " + newStatus + " ---");
            // this is the trigger for the Observer pattern
            notifyObservers();
        }

        @Override
        public void attach(Observer o) {
            observers.add(o);
        }

        @Override
        public void detach(Observer o) {
            observers.remove(o);
        }

        @Override
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(this);
            }
        }
    }

     // observer type 1: Notifies the buyer with a simulated message

    class BuyerNotifier implements Observer {
        @Override
        public void update(Subject subject) {
            if (subject instanceof Order) {
                Order order = (Order) subject;
                System.out.println("[BuyerNotifier]: Hello Buyer! Your order " + order.getOrderId() + " is now " + order.getStatus());
            }
        }
    }


     // observer type 2: Writes a log entry for system auditing
    class LoggingService implements Observer {
        @Override
        public void update(Subject subject) {
            if (subject instanceof Order) {
                Order order = (Order) subject;
                System.out.println("[LoggingService]: AUDIT - Order " + order.getOrderId() + " updated to " + order.getStatus());
            }
        }
    }

// --- 2. REPOSITORY PATTERN ---
// Defines the interfaces and classes for abstracting data persistence

     // Generic Repository Interface - provides a standard contract for all
     // data access, hiding the implementation (e.g., HashMap, SQL, etc.).

    interface IRepository<T> {
        T getById(String id);
        void save(T entity);
        List<T> getAll();
    }

     // Simulated Database - holds all our data in HashMaps - repositories will interact with this class

    class InMemoryDatabase {
        // Static maps to simulate database tables
        public static final Map<String, User> users = new HashMap<>();
        public static final Map<String, Listing> listings = new HashMap<>();
        public static final Map<String, Order> orders = new HashMap<>();
    }


     // User Repository - implements the IRepository interface
     // to handle User objects, interacting with the InMemoryDatabase

    class UserRepository implements IRepository<User> {
        @Override
        public User getById(String id) {
            return InMemoryDatabase.users.get(id);
        }

        @Override
        public void save(User entity) {
            InMemoryDatabase.users.put(entity.getUserId(), entity);
            System.out.println("[Repository]: Saved User " + entity.getUsername() + " to DB.");
        }

        @Override
        public List<User> getAll() {
            return new ArrayList<>(InMemoryDatabase.users.values());
        }
    }

     // Repository for Listings
    class ListingRepository implements IRepository<Listing> {
        @Override
        public Listing getById(String id) {
            return InMemoryDatabase.listings.get(id);
        }

        @Override
        public void save(Listing entity) {
            InMemoryDatabase.listings.put(entity.getListingId(), entity);
            System.out.println("[Repository]: Saved Listing " + entity.getTitle() + " to DB.");
        }

        @Override
        public List<Listing> getAll() {
            return new ArrayList<>(InMemoryDatabase.listings.values());
        }
    }

     // Concrete Repository for Orders

    class OrderRepository implements IRepository<Order> {
        @Override
        public Order getById(String id) {
            return InMemoryDatabase.orders.get(id);
        }

        @Override
        public void save(Order entity) {
            InMemoryDatabase.orders.put(entity.getOrderId(), entity);
            System.out.println("[Repository]: Saved Order " + entity.getOrderId() + " to DB.");
        }

        @Override
        public List<Order> getAll() {
            return new ArrayList<>(InMemoryDatabase.orders.values());
        }
    }


// --- 3. FACTORY METHOD PATTERN ---
// Defines interfaces and classes for creating different types of Users

     // User Interface (Product): The common interface for all user types.
    interface User {
        String getUserId();
        String getUsername();
        String getRole();
    }

     // Concrete Product 1: Buyer
    class Buyer implements User {
        private String userId;
        private String username;

        public Buyer(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        @Override
        public String getUserId() { return userId; }
        @Override
        public String getUsername() { return username; }
        @Override
        public String getRole() { return "BUYER"; }
    }

     // Concrete Product 2: Seller
    class Seller implements User {
        private String userId;
        private String username;

        public Seller(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        @Override
        public String getUserId() { return userId; }
        @Override
        public String getUsername() { return username; }
        @Override
        public String getRole() { return "SELLER"; }
    }

     // Concrete Product 3: Admin
    class Admin implements User {
        private String userId;
        private String username;

        public Admin(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        @Override
        public String getUserId() { return userId; }
        @Override
        public String getUsername() { return username; }
        @Override
        public String getRole() { return "ADMIN"; }
    }

     // The Factory - creates the correct User object based on a role string
     // This decouples the client (UserService) from the concrete classes
    class UserFactory {
        public static User createUser(String role, String username) {
            String userId = "user-" + UUID.randomUUID().toString().substring(0, 8);
            switch (role.toUpperCase()) {
                case "BUYER":
                    return new Buyer(userId, username);
                case "SELLER":
                    return new Seller(userId, username);
                case "ADMIN":
                    return new Admin(userId, username);
                default:
                    throw new IllegalArgumentException("Unknown role: " + role);
            }
        }
    }

// --- 4. BUILDER PATTERN ---
// Defines classes for constructing a complex Listing object.

     // Product - the complex Listing object
     // Note its constructor is private; it can only be created by the Builder

    class Listing {
        // Required fields
        private String listingId;
        private String title;
        private double price;
        private User seller;

        // Optional fields
        private String description;
        private String category;

        // Private constructor that takes the builder
        private Listing(ListingBuilder builder) {
            this.listingId = "list-" + UUID.randomUUID().toString().substring(0, 8);
            this.title = builder.title;
            this.price = builder.price;
            this.seller = builder.seller;
            this.description = builder.description;
            this.category = builder.category;
        }

        public String getListingId() { return listingId; }
        public String getTitle() { return title; }
        // + other getters

        @Override
        public String toString() {
            return "Listing [ID=" + listingId + ", Title=" + title + ", Price=" + price +
                    ", Seller=" + seller.getUsername() + ", Desc=" + description + ", Category=" + category + "]";
        }

         // The Builder - provides a fluent API to construct the Listing step-by-step
        public static class ListingBuilder {
            // Required fields
            private String title;
            private double price;
            private User seller;

            // Optional fields
            private String description = ""; // Default value
            private String category = "General"; // Default value

            // Constructor for required fields
            public ListingBuilder(String title, double price, User seller) {
                this.title = title;
                this.price = price;
                this.seller = seller;
            }

            // Chained methods for optional fields
            public ListingBuilder withDescription(String description) {
                this.description = description;
                return this; // Returns itself for chaining
            }

            public ListingBuilder withCategory(String category) {
                this.category = category;
                return this; // Returns itself for chaining
            }

            // The final build method that creates the Listing
            public Listing build() {
                return new Listing(this);
            }
        }
    }

// --- 5. SERVICE "GLUE" CLASSES ---
// These classes use the patterns to perform business logic

     // UserService - uses Factory and Repository patterns
    class UserService {
        private final IRepository<User> userRepository;

        // The Repository is "injected" (Dependency Injection)
        public UserService(IRepository<User> userRepository) {
            this.userRepository = userRepository;
        }

        public User registerUser(String role, String username) {
            System.out.println("\n[UserService]: Registering new " + role);
            // Uses Factory to create the user
            User newUser = UserFactory.createUser(role, username);

            // Uses Repository to save the user
            userRepository.save(newUser);
            return newUser;
        }
    }

     // MarketplaceService - Uses Builder and Repository patterns
    class MarketplaceService {
        private final IRepository<Listing> listingRepository;

        public MarketplaceService(IRepository<Listing> listingRepository) {
            this.listingRepository = listingRepository;
        }

        public Listing createListing(String title, double price, User seller, String description, String category) {
            System.out.println("\n[MarketplaceService]: Creating new listing for " + seller.getUsername());
            // Uses Builder to construct the complex Listing object
            Listing listing = new Listing.ListingBuilder(title, price, seller)
                    .withDescription(description)
                    .withCategory(category)
                    .build();

            // Uses Repository to save the listing
            listingRepository.save(listing);
            return listing;
        }
    }

     // OrderService - uses Repository and configures the Observer pattern.
    class OrderService {
        private final IRepository<Order> orderRepository;

        // Observers to attach to every new order
        private final Observer globalLogger;
        private final Observer buyerNotifier;

        public OrderService(IRepository<Order> orderRepository, Observer globalLogger, Observer buyerNotifier) {
            this.orderRepository = orderRepository;
            this.globalLogger = globalLogger;
            this.buyerNotifier = buyerNotifier;
        }

        public Order createOrder(Listing listing, User buyer) {
            System.out.println("\n[OrderService]: Creating new order for " + listing.getTitle());
            Order order = new Order(listing, buyer);

            // Attaches observers to the new order (Configuring the Observer pattern)
            order.attach(globalLogger);
            order.attach(buyerNotifier);

            // Uses Repository to save the order
            orderRepository.save(order);
            return order;
        }

        public void updateOrderStatus(Order order, String newStatus) {
            // This triggers the Observer pattern's notify() method
            order.setStatus(newStatus);

            // Persist the change
            orderRepository.save(order);
        }
    }


// --- 6. MAIN DEMO CLASS ---
// This main method runs the Proof-of-Concept and shows the pattern interactions.

    public class WowManiaDemo {

        public static void main(String[] args) {
            System.out.println("--- WowMania Proof-of-Concept Demo Starting ---");

            // 1. Setup Repositories (REPOSITORY PATTERN)
            IRepository<User> userRepository = new UserRepository();
            IRepository<Listing> listingRepository = new ListingRepository();
            IRepository<Order> orderRepository = new OrderRepository();

            // 2. Setup Observers (OBSERVER PATTERN)
            Observer logger = new LoggingService();
            Observer notifier = new BuyerNotifier();

            // 3. Setup Services (Injecting Dependencies)
            UserService userService = new UserService(userRepository);
            MarketplaceService marketplaceService = new MarketplaceService(listingRepository);
            OrderService orderService = new OrderService(orderRepository, logger, notifier);

            // --- SCENARIO 1: FACTORY + REPOSITORY ---
            // Registering two users. The UserService uses the UserFactory to create
            // them and the UserRepository to save them.
            User seller = userService.registerUser("SELLER", "BoostGod");
            User buyer = userService.registerUser("BUYER", "Newbie");

            // Proof it worked
            System.out.println("Users in DB: " + userRepository.getAll().size());

            // --- SCENARIO 2: BUILDER + REPOSITORY ---
            // The Seller creates a new complex listing. The MarketplaceService
            // uses the ListingBuilder and saves it with the ListingRepository.
            Listing listing = marketplaceService.createListing(
                    "Mythic+ 10 Carry",
                    100.00, // Price
                    seller,
                    "Fast run, guaranteed timer.", // Optional Description
                    "Dungeon" // Optional Category
            );

            // Proof it worked
            System.out.println("Created Listing: " + listing.toString());

            // --- SCENARIO 3: OBSERVER + SERVICE INTERACTION ---
            // The Buyer creates an order. The OrderService creates the Order,
            // attaches the Logger and Notifier observers, and saves it.
            Order order = orderService.createOrder(listing, buyer);

            // Now, we update the order status. This will trigger the
            // setStatus() method, which calls notifyObservers().
            // Both the LoggingService and BuyerNotifier will activate automatically.

            orderService.updateOrderStatus(order, "AGREEMENT_FINALIZED");

            // Second update to show it works multiple times
            orderService.updateOrderStatus(order, "IN_PROGRESS");

            System.out.println("\n--- WowMania Demo Finished ---");
        }
    }
