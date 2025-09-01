/**
 * Jaleel Williams
 */

package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ETLPipeline{
  public static void main(String[] args) {
    String filePath = "data/products.csv"; // Path of csv file
    ArrayList<Product> productList = ExtractCsv(filePath);
    String[] transformedData; // String array to store one line of transformed data

    // Write to the file data/transformed_products.csv
    try (PrintWriter wr = new PrintWriter(new FileWriter("data/transformed_products.csv"))){
      wr.println("ProductID,Name,Price,Category,PriceRange"); // Write header
      
      // Perform transformations on each product and write transformed products to new CSV
      for (Product product: productList){
        product.UppercaseName();
        product.ElectronicsDiscount();
        product.SetPriceRange();
        transformedData = product.AsString(); // Gets all product attributes as a String array
        wr.println(String.join(",", transformedData)); // Writes comma separated product attributes to one line
      }
    }
    // Catch any errors with writing to the new file
    catch (IOException e){
      System.err.println("Unable to write to CSV file: " + e.getMessage());
      System.exit(1);
    }
  }

  /**
   * Method to extract data from the csv file data/products.csv
   * 
   * @param path The path of the csv file to be read
   * @return An ArrayList of products listed in the csv file
   * @throws IOException if any errors occur when reading the file
   */
  static ArrayList<Product> ExtractCsv(String path){
    String line; // string to store extracted lines
    String delimiter = ","; // comma delimiter
    ArrayList<Product> products = new ArrayList<>(); // List of extracted products

    try (BufferedReader br = new BufferedReader(new FileReader(path))){
      br.readLine(); // skip header

      // Read each line and extract values
      while ((line = br.readLine()) != null){
        line = line.trim(); // Remove trailing whitespaces or newline characters
        String[] values = line.split(delimiter);
        Product product = new Product(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]), values[3]);
        products.add(product); 
      }
    }
    // Catch and print any errors with reading the file
    catch (IOException e){
      System.err.println("Unable to read CSV file: " + e.getMessage());
      System.exit(1); // End the program
    }
    return products;
  }
}

/**
 * Class to store product information
 */
class Product{
  int productID;
  String name;
  double price;
  String category;
  String priceRange;

  /**
   * Constructor for the Product class
   * 
   * @param id
   * @param name
   * @param price
   * @param category
   * @retrun A new Product object
   * @throws IllegalArgumentException If price or id parameters are negative
   */
  public Product(int id, String name, double price, String category) throws IllegalArgumentException{
    if (price < 0) {throw new IllegalArgumentException("Price cannot be negative");}
    if (productID < 0) {throw new IllegalArgumentException("Price cannot be negative");}
    this.productID = id;
    this.name = name;
    this.price = price;
    this.category = category;
  }

  /**
   * Converts product names to all uppercase
   */
  public void UppercaseName(){
    this.name = this.name.toUpperCase();
  }

  /**
   * Gives a 10% discount to products in the Electronics category.
   * If the discount price is over $500.00, the object category is
   * modified to Premium Electronics.
   */
  public void ElectronicsDiscount(){
    if (this.category.equals("Electronics")){
      this.price = this.price * 0.9;
      this.price = Math.round(this.price * 100.0) / 100.0; // Round price to 2 decimal places

      if (this.price > 500.00){
        this.category = "Premium Electronics";
      }
    }
  }

  /**
   * Method to set priceRange attribute
   */
  public void SetPriceRange(){
    if (this.price <= 10.00){this.priceRange = "Low";}
    else if (this.price <= 100.00){this.priceRange = "Medium";}
    else if (this.price <= 500.00){this.priceRange = "High";}
    else{this.priceRange = "Premium";}
  }

  /**
   * Method to return attributes as strings
   */
  public String[] AsString(){
    String[] attributeStrings = {
      String.valueOf(this.productID),
      this.name,
      String.valueOf(this.price),
      this.category,
      this.priceRange
    };

    return attributeStrings;
  }
}