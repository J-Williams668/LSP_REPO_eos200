package org.howard.edu.lsp.assignment3;

/**
 * Performs transformations on data
 */
public interface Transformer<T> {
  T transform();
}