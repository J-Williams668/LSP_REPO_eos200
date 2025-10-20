package org.howard.edu.lsp.midterm.question2;

public class AreaCalculator{
  // Circle Area
  public static double area(double radius) throws IllegalArgumentException{
    if (radius <= 0.0){
      throw new IllegalArgumentException("Dimensions must be greater than zero!");
    }
    return Math.PI * radius * radius;
  }

  // Rectangle Area
  public static double area(double width, double height) throws IllegalArgumentException{
    if (width <= 0.0 || height <= 0.0){
      throw new IllegalArgumentException("Dimensions must be greater than zero!");
    }
    return width * height;
  }

  // Triangle Area
  public static double area(int base, int height) throws IllegalArgumentException{
    if (base <= 0 || height <= 0){
      throw new IllegalArgumentException("Dimensions must be greater than zero!");
    }
    return 0.5 * base * height;
  }

  // Square Area
  public static double area(int side) throws IllegalArgumentException{
    if (side <= 0){
      throw new IllegalArgumentException("Dimensions must be greater than zero!");
    }
    return side * side;
  }
}