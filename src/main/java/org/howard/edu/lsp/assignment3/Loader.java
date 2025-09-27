package org.howard.edu.lsp.assignment3;

import java.util.List;

public interface Loader<T> {
  void load(List<T> items) throws Exception;
}
