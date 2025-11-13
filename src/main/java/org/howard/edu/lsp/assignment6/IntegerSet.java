package org.howard.edu.lsp.assignment6;

import java.util.ArrayList; 
import java.util.List;
 
public class IntegerSet  { 
  private List<Integer> set = new ArrayList<Integer>(); 
 
  // Clears the internal representation of the set. 
  public void clear() {
    set.clear();
  } 
 
  // Returns the number of elements in the set. 
  public int length() {
    return set.size();
  } 
 
  /* 
   * Returns true if the 2 sets are equal, false otherwise; 
   * Two sets are equal if they contain all of the same values in ANY order. 
   * This overrides the equals method from the Object class. 
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

  @Override
  public int hashCode() {
    int hash = 0;
    for (Integer i : set) {
        // handle null just in case
        hash ^= (i == null ? 0 : i.hashCode());
    }
    return hash;
  } 
 
  // Returns true if the set contains the value, otherwise false. 
  public boolean contains(int value) {
    for (int num: set){
      if (num == value){
        return true;
      }
    }
    return false;
  } 
 
  // Returns the largest item in the set (throws IllegalStateException if empty). 
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
 
  // Returns the smallest item in the set (throws IllegalStateException if empty). 
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
 
  // Adds an item to the set or does nothing if already present. 
  public void add(int item) {
    if(!(this.contains(item))){
      set.add(item);
    }
  } 
 
  // Removes an item from the set or does nothing if not there. 
  public void remove(int item) {
    if (this.contains(item)){
      set.remove(Integer.valueOf(item));
    }
  } 
 
  // Set union: modifies this to contain all unique elements in this or other. 
  public void union(IntegerSet other) {
    for (int num : other.set){
      if (!(this.contains(num))){
        this.add(num);
      }
    }
  } 
 
  // Set intersection: modifies this to contain only elements in both sets. 
  public void intersect(IntegerSet other) {
    for (int num : other.set){
      if (!(this.contains(num))){
        this.remove(num);
      }
    }
  } 
 
  // Set difference (this \ other): modifies this to remove elements found in other. 
  public void diff(IntegerSet other) {
    for (int num : other.set){
      if (this.contains(num)){
        this.remove(num);
      }
    }
  } 
 
  // Set complement: modifies this to become (other \ this). 
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
 
  // Returns true if the set is empty, false otherwise. 
  public boolean isEmpty() {
    return set.isEmpty();
  } 
 
  // Returns a String representation; overrides Object.toString(). 
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
