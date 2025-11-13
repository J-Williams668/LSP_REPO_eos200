package org.howard.edu.lsp.assignment6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test cases for the IntegerSet class.
 * The tests verify:
 * - Correct set behavior (no duplicates)
 * - Correct behavior of all operations
 * - Correct exception handling
 * - Correct implementation of equals, hashCode, and toString
 * - That mutator methods modify the original object
 */
public class IntegerSetTest {

  /**
   * Helper method to create a set initialized with given values.
   *
   * @param values integers to add to the set
   * @return a populated IntegerSet
   */
  private IntegerSet makeSet(int... values) {
      IntegerSet set = new IntegerSet();
      for (int v : values) {
          set.add(v);
      }
      return set;
  }

  /**
   * Tests that the clear() method empties the set
   */
  @Test
  public void testClear() {
      IntegerSet set = makeSet(1, 2, 3);
      assertFalse(set.isEmpty());
      assertEquals(3, set.length());

      set.clear();
      assertTrue(set.isEmpty());
      assertEquals(0, set.length());
  }

  /**
   * Tests that the length() method returns the set size
   */
  @Test
  public void testLength() {
      IntegerSet set = new IntegerSet();
      assertEquals(0, set.length());

      set.add(10);
      set.add(20);
      assertEquals(2, set.length());

      set.remove(10);
      assertEquals(1, set.length());
  }

  /**
   * Tests that the add() method does not allow duplicates
   */
  @Test
  public void testAdd() {
      IntegerSet set = new IntegerSet();
      set.add(5);
      set.add(5);
      set.add(5);

      assertEquals(1, set.length());
      assertTrue(set.contains(5));
      assertEquals("[5]", set.toString());
  }

  /**
   * Tests the contains() method
   */
  @Test
  public void testContains() {
      IntegerSet set = makeSet(1, 2, 3);
      assertTrue(set.contains(1));
      assertTrue(set.contains(2));
      assertTrue(set.contains(3));
      assertFalse(set.contains(4));
  }

  /**
   * Tests that the largest() method returns the maximum value on a
   * non-empty set
   */
  @Test
  public void testLargest() {
      IntegerSet set = makeSet(3, 10, -1, 7);
      assertEquals(10, set.largest());
  }

  /**
   * Tests that the largest() method throws an IllegalStateException on
   * an empty set
   */
  @Test
  public void testLargestThrowsOnEmptySet() {
      IntegerSet empty = new IntegerSet();
      assertThrows(IllegalStateException.class, empty::largest);
  }

  /**
   * Tests that the smallest() method returns the minimum value on a
   * non-empty set
   */
  @Test
  public void testSmallest() {
      IntegerSet set = makeSet(3, 10, -1, 7);
      assertEquals(-1, set.smallest());
  }

  /**
   * Tests that the smallest() method throws an IllegalStateException on
   * an empty set
   */
  @Test
  public void testSmallestThrowsOnEmptySet() {
      IntegerSet empty = new IntegerSet();
      assertThrows(IllegalStateException.class, empty::smallest);
  }

  /**
   * Tests the remove method on an element in the set
   */
  @Test
  public void testRemoveExistingElement() {
      IntegerSet set = makeSet(1, 2, 3);
      set.remove(2);

      assertFalse(set.contains(2));
      assertEquals(2, set.length());
      assertEquals("[1, 3]", set.toString());
  }

  /**
   * Tests the remove method on an element not in the set
   */
  @Test
  public void testRemoveNonExistingElement() {
      IntegerSet set = makeSet(1, 2, 3);
      set.remove(100);

      assertEquals(3, set.length());
      assertEquals("[1, 2, 3]", set.toString());
  }

  /**
   * Tests equality of sets with the same contents in different order.
   */
  @Test
  public void testEqualsSameContentsDifferentOrder() {
      IntegerSet a = makeSet(1, 2, 3);
      IntegerSet b = makeSet(3, 2, 1);

      assertTrue(a.equals(b));
      assertTrue(b.equals(a));
      assertEquals(a.hashCode(), b.hashCode());
  }

  /**
   * Tests equality of sets with different contents.
   */
  @Test
  public void testEqualsDifferentContents() {
      IntegerSet a = makeSet(1, 2, 3);
      IntegerSet b = makeSet(1, 2, 4);

      assertFalse(a.equals(b));
      assertFalse(b.equals(a));
  }

  /**
   * Tests equality of sets with the shared contents and different sizes.
   */
  @Test
  public void testEqualsDifferentSizes() {
      IntegerSet a = makeSet(1, 2, 3);
      IntegerSet b = makeSet(1, 2);

      assertFalse(a.equals(b));
  }

  /**
   * Tests equality of sets with the same reference.
   */
  @Test
  public void testEqualsSameObject() {
      IntegerSet a = makeSet(1, 2, 3);
      assertTrue(a.equals(a));
  }

  /**
   * Tests the string format produced by toString.
   */
  @Test
  public void testToStringFormat() {
      IntegerSet set = makeSet(1, 2, 3);
      assertEquals("[1, 2, 3]", set.toString());

      IntegerSet empty = new IntegerSet();
      assertEquals("[]", empty.toString());
  }

  /**
   * Test the union operation
   */
  @Test
  public void testUnionContainsAllElements() {
      IntegerSet a = makeSet(1, 2);
      IntegerSet b = makeSet(2, 3);

      a.union(b);

      // a should now contain {1,2,3}
      assertEquals(3, a.length());
      assertTrue(a.contains(1));
      assertTrue(a.contains(2));
      assertTrue(a.contains(3));

      // b should remain unchanged
      assertEquals(2, b.length());
      assertEquals("[2, 3]", b.toString());

      // no duplicates
      assertEquals("[1, 2, 3]", a.toString());
  }

  /**
   * Test the intersection operation
   */
  @Test
  public void testIntersectModifiesThisToSharedElements() {
      IntegerSet a = makeSet(1, 2, 3, 4);
      IntegerSet b = makeSet(3, 4, 5);

      // Expect a to become {3,4}
      a.intersect(b);

      assertEquals(2, a.length());
      assertTrue(a.contains(3));
      assertTrue(a.contains(4));
      assertFalse(a.contains(1));
      assertFalse(a.contains(2));

      // b remains unchanged
      assertEquals(3, b.length());
      assertEquals("[3, 4, 5]", b.toString());
  }

  /**
   * Test that the difference operation modifies the instance
   * set to (this \ other)
   */
  @Test
  public void testDiffRemovesElementsFoundInOther() {
      IntegerSet a = makeSet(1, 2, 3, 4);
      IntegerSet b = makeSet(3, 4, 5);

      a.diff(b);

      assertEquals(2, a.length());
      assertTrue(a.contains(1));
      assertTrue(a.contains(2));
      assertFalse(a.contains(3));
      assertFalse(a.contains(4));

      // b unchanged
      assertEquals("[3, 4, 5]", b.toString());
  }

  /**
   * Test that the complement operation modifies the instance
   * set to (other \ this)
   */
  @Test
  public void testComplementBecomesOtherMinusThis() {
      IntegerSet a = makeSet(1, 2);
      IntegerSet b = makeSet(1, 2, 3, 4);

      a.complement(b);  // a = b \ a

      // Now a should be {3,4}
      assertEquals(2, a.length());
      assertTrue(a.contains(3));
      assertTrue(a.contains(4));
      assertFalse(a.contains(1));
      assertFalse(a.contains(2));

      // b unchanged
      assertEquals("[1, 2, 3, 4]", b.toString());
  }

  /**
   * Test that mutator methods (union(), intersect(), diff(),
   * complement()) modify the instance set instead of returning
   * a new set.
   */
  @Test
  public void testMutatorsModifyCurrentInstance() {
      IntegerSet a = new IntegerSet();
      a.add(1);
      a.add(2);

      IntegerSet alias = a;

      a.union(makeSet(2, 3));      // a should be {1,2,3}
      a.intersect(makeSet(2, 3));  // a should be {2,3}
      a.diff(makeSet(3));          // a should be {2}
      a.complement(makeSet(1, 2, 3)); // a should now be {1,3}

      // alias should see the same changes
      assertEquals(alias.toString(), a.toString());
      assertEquals("[1, 3]", a.toString());
  }
}
