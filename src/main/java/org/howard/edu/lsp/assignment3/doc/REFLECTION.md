# Assignment 3 Reflection

##  Differences Between Assigment 2 and 3 Implementations

The implementation of the ETL Pipeline in assigment 2 had one .java file with the ETLPipeline public class and a Products class.

* The ETLPipeline class was responsible for extracting and transforming data from products.csv and then writing the transformed data
to transformed_products.csv

* The Products class was repsonsible for storing data extracted from products.csv in its objects. The class also had a method for each of the three transformations to be applied.

* Product data would be transformed by calling public Product methods on a Product object.

The implementation in assignment 3 implements encapsulation by splitting the ETL Pipeline tasks into separate classes, each with 1 task.

* CsvExtractor extracts data from products.csv and returns a List of Product objects for each of the objects.

* ProductTransformer performs transformations on the product data via private methods and returns a List of transformed Product objects.

* CsvLoader takes the List of transformed products as input and writes the data to transformed_products.csv

* ETLPipeline is simplified to be an orchestrator that uses the above classes to extract, transform, and load product data.

* The Product class is simplified to a data class.

Abstraction is also implemented by using the interfaces `Extractor`, `Transformer`, and `Loader`. This allows the general behaviour of `CsvExtractor`, `CsvTransformer`, and `CsvLoader` to be known while hiding their exact implementation.