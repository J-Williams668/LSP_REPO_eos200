package org.howard.edu.lsp.assignment6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegerSetTest {

  private IntegerSet makeSet(int... values) {
      IntegerSet set = new IntegerSet();
      for (int v : values) {
          set.add(v);
      }
      return set;
  }

  @Test
  public void testClear() {
      IntegerSet set = makeSet(1, 2, 3);
      assertFalse(set.isEmpty());
      assertEquals(3, set.length());

      set.clear();
      assertTrue(set.isEmpty());
      assertEquals(0, set.length());
  }

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

  // ---------- add() & no duplicates ----------

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

  // ---------- contains() ----------

  @Test
  public void testContains() {
      IntegerSet set = makeSet(1, 2, 3);
      assertTrue(set.contains(1));
      assertTrue(set.contains(2));
      assertTrue(set.contains(3));
      assertFalse(set.contains(4));
  }

  // ---------- largest() ----------

  @Test
  public void testLargest() {
      IntegerSet set = makeSet(3, 10, -1, 7);
      assertEquals(10, set.largest());
  }

  @Test
  public void testLargestThrowsOnEmptySet() {
      IntegerSet empty = new IntegerSet();
      assertThrows(IllegalStateException.class, empty::largest);
  }

  // ---------- smallest() ----------

  @Test
  public void testSmallest() {
      IntegerSet set = makeSet(3, 10, -1, 7);
      assertEquals(-1, set.smallest());
  }

  @Test
  public void testSmallestThrowsOnEmptySet() {
      IntegerSet empty = new IntegerSet();
      assertThrows(IllegalStateException.class, empty::smallest);
  }

  // ---------- remove() ----------

  @Test
  public void testRemoveExistingElement() {
      IntegerSet set = makeSet(1, 2, 3);
      set.remove(2);

      assertFalse(set.contains(2));
      assertEquals(2, set.length());
      assertEquals("[1, 3]", set.toString());
  }

  @Test
  public void testRemoveNonExistingElement() {
      IntegerSet set = makeSet(1, 2, 3);
      set.remove(100);

      assertEquals(3, set.length());
      assertEquals("[1, 2, 3]", set.toString());
  }

  // ---------- equals() & hashCode() ----------

  @Test
  public void testEqualsSameContentsDifferentOrder() {
      IntegerSet a = makeSet(1, 2, 3);
      IntegerSet b = makeSet(3, 2, 1);

      assertTrue(a.equals(b));
      assertTrue(b.equals(a));
      assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  public void testEqualsDifferentContents() {
      IntegerSet a = makeSet(1, 2, 3);
      IntegerSet b = makeSet(1, 2, 4);

      assertFalse(a.equals(b));
      assertFalse(b.equals(a));
  }

  @Test
  public void testEqualsDifferentSizes() {
      IntegerSet a = makeSet(1, 2, 3);
      IntegerSet b = makeSet(1, 2);

      assertFalse(a.equals(b));
  }

  @Test
  public void testEqualsSameObject() {
      IntegerSet a = makeSet(1, 2, 3);
      assertTrue(a.equals(a));
  }

  // ---------- toString() ----------

  @Test
  public void testToStringFormat() {
      IntegerSet set = makeSet(1, 2, 3);
      assertEquals("[1, 2, 3]", set.toString());

      IntegerSet empty = new IntegerSet();
      assertEquals("[]", empty.toString());
  }

  // ---------- union() (mutates this) ----------

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

  // ---------- intersect() (mutates this) ----------

  @Test
  public void testIntersectModifiesThisToSharedElements() {
      IntegerSet a = makeSet(1, 2, 3, 4);
      IntegerSet b = makeSet(3, 4, 5);

      // Expect a to become {3,4}
      // NOTE: with current implementation, this may throw ConcurrentModificationException.
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

  // ---------- diff() (this \ other) ----------

  @Test
  public void testDiffRemovesElementsFoundInOther() {
      IntegerSet a = makeSet(1, 2, 3, 4);
      IntegerSet b = makeSet(3, 4, 5);

      a.diff(b);  // a = a \ b

      assertEquals(2, a.length());
      assertTrue(a.contains(1));
      assertTrue(a.contains(2));
      assertFalse(a.contains(3));
      assertFalse(a.contains(4));

      // b unchanged
      assertEquals("[3, 4, 5]", b.toString());
  }

  // ---------- complement() (this := other \ this) ----------

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

  // ---------- Mutator methods must modify this (not return new set) ----------

  @Test
  public void testMutatorsModifyCurrentInstance() {
      IntegerSet a = new IntegerSet();
      a.add(1);
      a.add(2);

      // Keep a reference and use it through variable alias
      IntegerSet alias = a;

      a.union(makeSet(2, 3));      // a should be {1,2,3}
      a.intersect(makeSet(2, 3));  // a should be {2,3}
      a.diff(makeSet(3));          // a should be {2}
      a.complement(makeSet(1, 2, 3)); // a should now be {1,3}

      // alias should see the same changes (same object)
      assertEquals(alias.toString(), a.toString());
      assertEquals("[1, 3]", a.toString());
  }
}
