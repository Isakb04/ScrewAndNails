# Project Documentation
## Screws&NailsRUs Stock Management System 

### Overview
The system manages screws, nails, and wall plugs in various materials, lengths, and types. It displays current stock levels, handles sales, restocking, and generates detailed reports. Features include a secure login with different user roles (Employee and Admin) and a product search function.

### Features
- **Product Management:** Handles multiple types and variants of products.
- **Inventory Tracking:** Tracks current stock levels accurately.
- **Sales and Purchase Management:** Facilitates sales and purchases.
- **Reporting:** Generates detailed reports for decision-making.
- **Search Functionality:** Allows quick access to product information.
- **User Management:** Secure login with role-based access control.
- **Messaging Function:** Enables communication between users and admins.
- **File Format for Messages:** Utilizes .TXT format for storing messages.
- **Add Entire New Stock:** Admin functionality to manage stocks and users.

### Files Included
- Main.java
- LoginController.java
- StockController.java
- AddStockController.java
- AdminScreenController.java
- AddUserController.java
- README.md
- LICENSE

### Getting Started
- clone the repository
- Download Java JDK Development Kit 17
- Ensure MySQL is installed for database operations. [SetUpMySQL](https://www.youtube.com/watch?v=u96rVINbAUI)
- Downlaod Database Schema stocksystem for users and stock
- Open project folder in a chosen IDE (Recommended IntelliJ)
- open hibernae.cfg.xml and change the connection.password to your own (what you would have used in MySQL configurator) and the database URL
  ```sh
    <?xml version = "1.0" encoding = "utf-8"?>
  <!DOCTYPE hibernate-configuration SYSTEM
          "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
  <hibernate-configuration>
      <session-factory>
          <property name="connection.url">**Database URL**</property>
          <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
          <property name="connection.username">root</property>
          <property name="connection.password">**DataBasePassword**</property>
          <property name="connection.pool_size">10</property>
          <property name="show_sql">true</property>
          <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
          <property name="current_session_context_class">thread</property>
          <mapping resource = "Stock.hbm.xml"/>
          <mapping resource = "User.hbm.xml"/>
      </session-factory>
  </hibernate-configuration>
  ```
- crete a new connection name

### How to Contribute
Contributors can help by improving existing functionalities, adding new features, or documenting the code. Please follow the existing coding style and add comments where necessary.

### Versioning
This project uses simple file-based versioning for tracking changes in data files and source code.

### License
This project is licensed under the MIT License - see the LICENSE file for details.

### Author
- Isak Jonsson

### Acknowledgments
- Reuben Shaw
