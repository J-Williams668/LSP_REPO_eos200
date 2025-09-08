# ETL Pipeline

## Class: ETLPipeline

### Main()

The main method passes the relative file path of `products.csv` to the `ExtractCsv` method to get an ArrayList of Product objects created with the values extracted from `product.csv`. Then,
The method then iterates over the ArrayList and performs transformations on each of the products. Product attributes are then converted to strings using `Product.AsString()`, then written to
`transformed_products.csv ` using PrintWriter to utilize its `println()` method. Any IOExceptions are caught and handled by the try-catch block, and the program terminates when an Exception is caught.

### ExtractCsv()

This method extracts data from a csv file given by the path String parameter. BufferedReader isused in order to use the `readln()` method to read the file line by line. The header is skipped by 
calling `readln()` before the extraction loop and trailing whitespaces or newline characters are removed using the `String.trim()` method. The values are separated and loaded into the String array 
`values` then the elements of the array are used to create Product objects. The product objects are added to ArrayList `products` which is returned at the end of the method. Any exceptions are
caught by the try-catch block.

## Class: Product

Class for objects that store extracted product data. All of its methods except for the constructor and `AsString()` perform one of the required transformations. The class constructor throws
an `IllegalArgumentException` if the the product id or price value given is negative.

### UppercaseName()

Changes a product's name to uppercase

### ElectronicsDiscount()

Reduces a product's price by 10% if it is in the Electronics category. Price is rounded to 2 decimal places by using `Math.round() ` to round `this.price * 100` to the nearest whole number, then
divide by 100 to get the 2 decimal place result. 

### SetPriceRange()

Sets a product's priceRange attribute based on its price attribute. This method is intended to be called only after `ElectronicsDiscount()`.

### AsString()

Returns an array of a product's attributes as strings. This allows the transformed attributes to be written to `transformed_products.csv`.

## Internet Sources

* https://www.baeldung.com/java-csv-file-array - Used for splitting csv files. Adapted to remove trailing whitespaces/newline charatcers and skip the header.
* Google AI Overview ("write from a string array to a csv file java") - Used for writing transformed data to csv file. Adapted to remove FileWriter initialization.
* https://stackoverflow.com/questions/513832/how-do-i-compare-strings-in-java - Used to learn string equality checking. Adapted into `Product.ElectronicsDiscount()` method.
* https://www.geeksforgeeks.org/java/java-string-trim-method-example/ - For trimming whitespace from lines read from products.csv. Adapted into `ETLPipeline.ExtractCsv()` method.
