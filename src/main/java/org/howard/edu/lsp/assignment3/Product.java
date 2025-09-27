/**
 * Jaleel Williams
 */

package org.howard.edu.lsp.assignment3;

/**
 * Class to store product information
 */
public class Product{
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

  public Product(int id, String name, double price, String category, String priceRange) throws IllegalArgumentException{
  if (price < 0) {throw new IllegalArgumentException("Price cannot be negative");}
  if (productID < 0) {throw new IllegalArgumentException("Price cannot be negative");}
  this.productID = id;
  this.name = name;
  this.price = price;
  this.category = category;
  this.priceRange = priceRange;
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