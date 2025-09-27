package org.howard.edu.lsp.assignment3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class for loading product data into a new .csv file
 * 
 * @param path The path to the new .csv file
 */
public class CsvLoader implements Loader<Product> {
  private final String path;

  public CsvLoader(String path){
    this.path = path;
  }
  
  /**
   * Method to write product data to a .csv file
   * 
   * @param items A List of Product objects containing transformed product data
   */
  @Override
  public void load(List<Product> items) throws Exception {
    try (PrintWriter wr = new PrintWriter(new FileWriter(path))) {
      wr.println("ProductID,Name,Price,Category,PriceRange");
      for (Product p : items) {
        wr.println(String.join(",", p.AsString()));
      }
    }
    // Catch any errors with writing to the new file
    catch (IOException e){
      System.err.println("Unable to write to CSV file: " + e.getMessage());
      System.exit(1);
    }
  }
}
