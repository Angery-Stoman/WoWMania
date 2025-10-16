# WoWMania
Software design techniques Project
WowMania: World of Warcraft Marketplace and LFG Platform 
Team:
-Munteanu Amalia-Nicole 1241EA
-Radu Matei 1241EA
-Francu Teodor-Matei 1241EA

WowMania is a  web platform for World of Warcraft players to safely buy and sell services (like boosting) and virtual items. Our site acts as the secure middleman for these peer-to-peer transactions between Sellers and Buyers. All final transactions are conducted between players in-game, using WoW's internal trade system and currency. Besides being an e-commerce platform, we're building a community feature: a Looking for Group system.

Our main foal is to build a robust, scalable platform that handles online sales smoothly, builds trust in the WoW community, and gives users a unique experience. Scalability and fault tolerance are top priorities.

Key Features and Functionalities

1. User System and Roles: We have three core user types: Buyer, Seller and Admin. We're implementing secure Registration and Authentication so people can log in safely. We'll also build Account Management so users can handle their profiles, check past orders, and change settings.

2. Marketplace: Sellers can post detailed Product/Service Listings with descriptions, categories (like Raid, Dungeons, PvP etc.), prices and details. Buyers find what they need using a Product Catalog with search and filtering features. The Shopping Cart updates instantly with pricing and availability. The core feature is Order Processing, which handles confirmation and agreement finalization. Since we are not handling real money, the focus is on securely logging the transaction details and status updates. We might also add User Reviews and Ratings, plus Discounts and Promotions later on.

3. Looking for Group System: this is a non-transactional system. Any registered user can post requests to find teammates for specific Dungeons, Raids or Parties. It' a dedicated community tool, separate from the buying and selling roles.

Design Patterns Justification

We chose four key design patterns to make our system clean, extensible and reliable: Factory Method, Builder, Observer and Repository.

1. Factory Method pattern

-Problem Addressed: the User System needs to create specific objects (Buyer, Seller, Admin etc.) that require different initial settings, permissions or properties. For instance, a Seller needs access to listing creation tools, while an Admin needs special roles. Using a simple constructor to create these different types of objects makes the creation logic messy and hard to change.

-Justification and Advantages: The Factory Method pattern provides a central way to create different object types without exposing the complex creation logic. We use a central User class to handle creation. This is useful when we need to create different types of LFG posts or different User types. This keeps the code decoupled and flexile. If we introduce a new User role later, we only have to update the User, not every place in the application where a new user object is created.

2. Builder Pattern

-Problem Addressed: Listing service is complicated. It has required fields (Title, Price) and lots of optional ones (Boosting, Description etc.). Using a regular constructor would result in huge, confusing function calls with tons of null values.

-Justification and Advantages: The builder pattern separates how the Listing object is built from the object itself. We use a ListingBuilder class to construct it step-by-step with chained method calls. This keeps the code clean and readable, prevents errors during creation, and guarantees the listing is complete before it's saved.

3. Observer Pattern

-Problem Addressed: We need real-time updates for Order Tracking and LFG System. When an agreement status changes, or a new player joins a group, multiple users and systems need to know right away. Tying all these notifications directly to the Order object creates a big mess of dependencies.

-Justification and Advantages: The Observer pattern defines a mechanism where the Order of LFGPost automatically notifies all its registered dependents. This achieves loose coupling. The Subject doesn't care how the notifications are processed; it just announces the change. This is great scalability since we can add a new notification service later without editing the core Order logic.

4. Repository Pattern

-Problem Addressed: Our main goals are scalability and data access speed. If our business logic contains databases queries directly, it makes it impossible to switch databases later or add chaching without rewriting service code.

-Justification and Advantages: The Repository pattern creates a clean data layer. Our services only talk to an interface with handles all the messy database communication behind scenes. This enforces Separation of Concerns, making our core business logic unaware of database types. This is essential for testability and achieving high scalability by letting us swap out persistence technologies easily.  
