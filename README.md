# grocery-anlaytics
Spring boot application to process the groceries list and expose REST API end points for analytical data

Steps to run the application in your local machine. 

Prerequisites:

1. You should have JAVA 8 or higher version installed 
2. Ensure you have IDE like IntelliJ or Eclipse J2EE setup 

STEPS : 

1. git clone https://github.com/vamcspark/grocery-anlaytics.git
2. Run the main springboot file GroceryAnlayticsApplication.java

If you do not have IDE 

STEPS :

1. git clone 
2. cd grocery-anlaytics
3. mvn clean install 
4. java -jar grocery-anlaytics-0.0.1-SNAPSHOT.jar

After you see the success message of tomcat started, application end points are exposed at 8080 port. 

APIs 

1. http://localhost:8080/max-price - This API Returns all the ITEMS with max price and max price date. 
2. http://localhost:8080/item-price-history/Amla - This API returns specific ITEM asked for with its price history trend, Input : Item name need to be passed as part of URL as shown. 



