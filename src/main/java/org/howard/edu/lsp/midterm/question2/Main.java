package org.howard.edu.lsp.midterm.question2;

public class Main{
  public static void main(String[] args) {
      System.out.println(AreaCalculator.area(3.0)); // Circle
      System.out.println(AreaCalculator.area(5.0, 2.0)); // Rectangle
      System.out.println(AreaCalculator.area(10, 6)); // Triangle
      System.out.println(AreaCalculator.area(4)); // Square
      System.out.println(AreaCalculator.area(0, 6)); // Triangle (illegal)
  }
  /**
   * Different method names would be better than overloading in this case since
   * shapes with the same number of area parameters have to be distinguished by data type.
   * For example, a rectangle and triangle have two area parameters so for overloading to work
   * the triangle's parameters are restricted to whole numbers.
   */
}