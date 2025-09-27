package org.howard.edu.lsp.assignment3;

import java.util.List;

/**
 * Writes data to a .csv file
 */
public interface Loader<T> {
  void load(List<T> items) throws Exception;
}
