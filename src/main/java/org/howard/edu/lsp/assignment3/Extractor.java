package org.howard.edu.lsp.assignment3;

import java.util.List;


/**
 * Extactor class interface
 */
public interface Extractor<T> {
  List<T> extract() throws Exception;
}
