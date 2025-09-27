package org.howard.edu.lsp.assignment3;

import java.util.List;


/**
 * Extacts data from a .csv file
 */
public interface Extractor<T> {
  List<T> extract() throws Exception;
}
