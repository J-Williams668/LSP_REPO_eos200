package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to extract data from a .csv file
 * 
 * @param path The path of the .csv file to extract from
 */
public class CsvExtractor implements Extractor<Product> {
  private final String path;

  public CsvExtractor(String path){
    this.path = path;
  }

  /**
   * Method to extract data from the csv file data/products.csv
   * 
   * @return A List of products listed in the csv file
   * @throws IOException if any errors occur when reading the file
   */
  @Override
  public List<Product> extract() throws Exception{
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
