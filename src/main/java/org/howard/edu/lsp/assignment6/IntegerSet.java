package org.howard.edu.lsp.assignment6;

import java.util.ArrayList; 
import java.util.List;

/**
 * Class representing a mathematical set of integers.
 * Does not allow for duplicate values.
 * Supports union, intersection, difference, and complement operations.
 */
 
public class IntegerSet  { 
  private List<Integer> set = new ArrayList<Integer>(); 
 
  /**
   * Removes all elements from the set.
   */
  public void clear() {
    set.clear();
  } 
 
  /**
   * Returns the number of elements in the set.
   *
   * @return The size of the set
   */
  public int length() {
    return set.size();
  } 
 
  /**
     * Checks whether this set is equal to another object.
     * Two sets are equal if they contain exactly the same values,
     * regardless of order.
     *
     * @param o the object to compare
     * @return true if both sets contain the same elements, false otherwise
     */
  @Override 
  public boolean equals(Object o) {
    if (this == o){
      return true;
    }
    if (!(o instanceof IntegerSet)){
      return false;
    }

    IntegerSet otherSet = (IntegerSet) o;

    // Sets cannot be equal if they are of different length
    if (this.length() != otherSet.length()){
      return false;
    }

    return this.set.containsAll(otherSet.set) && otherSet.set.containsAll(this.set); 
  }

  /**
     * Computes a hash code consistent with equals.
     * The hash code is independent of element order.
     *
     * @return the hash code value
     */
  @Override
  public int hashCode() {
    int hash = 0;
    for (Integer i : set) {
        // handle null just in case
        hash ^= (i == null ? 0 : i.hashCode());
    }
    return hash;
  } 
 
  /**
   * Checks whether a value is contained in the set.
   *
   * @param value the integer to search for
   * @return true if the value is present, false otherwise
   */
  public boolean contains(int value) {
    for (int num: set){
      if (num == value){
        return true;
      }
    }
    return false;
  } 
 
  /**
     * Returns the largest integer in the set.
     *
     * @return the largest value
     * @throws IllegalStateException if the set is empty
     */ 
  public int largest() throws IllegalStateException {
    if (this.isEmpty()){
      throw new IllegalStateException("Cannot perform operation on empty set");
    }

    int maxNum = Integer.MIN_VALUE;

    for (int num: set){
      if (num > maxNum){
        maxNum = num;
      }
    }
    return maxNum;
  } 
 
  /**
   * Returns the smallest integer in the set.
   *
   * @return the smallest value
   * @throws IllegalStateException if the set is empty
   */
  public int smallest() throws IllegalStateException {
    if (this.isEmpty()){
      throw new IllegalStateException("Cannot perform operation on empty set");
    }

    int minNum = Integer.MAX_VALUE;

    for (int num: set){
      if (num < minNum){
        minNum = num;
      }
    }
    return minNum;
  } 
 
  /**
   * Adds a value to the set if it is not already present.
   *
   * @param item the value to add
   */
  public void add(int item) {
    if(!(this.contains(item))){
      set.add(item);
    }
  } 
 
  /**
   * Removes a value from the set if it exists.
   *
   * @param item the value to remove
   */
  public void remove(int item) {
    if (this.contains(item)){
      set.remove(Integer.valueOf(item));
    }
  } 
 
  /**
   * Performs the union operation.
   * Adds all elements from the other set that are not already present.
   *
   * @param other the set to union with
   */
  public void union(IntegerSet other) {
    for (int num : other.set){
      if (!(this.contains(num))){
        this.add(num);
      }
    }
  } 
 
  /**
   * Performs the intersection operation.
   * Retains only the elements that are also in the other set.
   *
   * @param other the set to intersect with
   */ 
  public void intersect(IntegerSet other) {
    for (int num : other.set){
      if (!(this.contains(num))){
        this.remove(num);
      }
    }
  } 
 
  /**
   * Performs the difference operation (this \ other).
   * Removes all elements that appear in the other set.
   *
   * @param other the set whose values should be removed from this set
   */
  public void diff(IntegerSet other) {
    for (int num : other.set){
      if (this.contains(num)){
        this.remove(num);
      }
    }
  } 
 
  /**
   * Performs the complement operation.
   * Replaces this set with all elements that are in the other set but not in this set.
   *
   * @param other the reference set
   */
  public void complement(IntegerSet other) {
    List<Integer> result = new ArrayList<Integer>();
    for (int num : other.set){
      if (!(this.contains(num))){
        result.add(num);
      }
    }

    this.clear();
    for (int num : result){
      this.add(num);
    }
  } 
 
  /**
   * Checks whether the set contains no elements.
   *
   * @return true if the set is empty, false otherwise
   */
  public boolean isEmpty() {
    return set.isEmpty();
  } 
 
   /**
   * Returns a string representation of the set
   * in the form [x, y, z].
   *
   * @return the formatted string
   */ 
  @Override 
  public String toString() {
    StringBuilder setSb = new StringBuilder("[");

    for (int i = 0; i < set.size(); i++){
      setSb.append(set.get(i).toString());
      if (i != set.size() - 1){
        setSb.append(", ");
      }
    }
    setSb.append("]");
    return setSb.toString();
  } 
}
