package org.howard.edu.lsp.assignment3;

import java.util.List;
/**
 * Class to apply transformations on a list of products
 * 
 * @param products A List of product objects to have their attributes transformed
 */
public class ProductTransformer implements Transformer<List<Product>>{
  private final List<Product> products;

  public ProductTransformer(List<Product> products){
    this.products = products;
  }

  /**
   * Method to return a transformed product
   * 
   * @return A new product object with transformed attributes
   */
  @Override
  public List<Product> transform(){
    this.UppercaseName();
    this.ElectronicsDiscount();
    this.SetPriceRange();
    return this.products;
  }

  /**
   * Converts product names to all uppercase
   */
  private void UppercaseName(){
    for (Product p: this.products){
      p.name = p.name.toUpperCase();
    }
  }

  /**
   * Gives a 10% discount to products in the Electronics category.
   * If the discount price is over $500.00, the object category is
   * modified to Premium Electronics.
   */
  private void ElectronicsDiscount(){
    for (Product p: this.products){
      if (p.category.equals("Electronics")){
        p.price = p.price * 0.9;
        p.price = Math.round(p.price * 100.0) / 100.0; // Round price to 2 decimal places

        if (p.price > 500.00){
          p.category = "Premium Electronics";
        }
      }
    }
  }

  /**
   * Method to set priceRange attribute
   */
  private void SetPriceRange(){
    for (Product p: this.products)
    if (p.price <= 10.00){p.priceRange = "Low";}
    else if (p.price <= 100.00){p.priceRange = "Medium";}
    else if (p.price <= 500.00){p.priceRange = "High";}
    else{p.priceRange = "Premium";}
  }

}
