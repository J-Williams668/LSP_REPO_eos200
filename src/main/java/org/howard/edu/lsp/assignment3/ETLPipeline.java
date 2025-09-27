/**
 * Jaleel Williams
 */
package org.howard.edu.lsp.assignment3;

import java.util.List;

public class ETLPipeline{
  public static void main(String[] args) throws Exception{
    Extractor<Product> extractor = new CsvExtractor("data/products.csv"); // Path of csv file
    List<Product> productList = extractor.extract();
    Transformer<List<Product>> transformer = new ProductTransformer(productList);
    List<Product> transformedProducts = transformer.transform();
    Loader<Product> loader = new CsvLoader("data/transformed_products.csv");
    loader.load(transformedProducts);
  }

}