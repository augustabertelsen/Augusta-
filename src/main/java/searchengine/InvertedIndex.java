package searchengine;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class InvertedIndex {
    

throws IOException {
    try {
      // read all lines from input file
      List<String> lines = Files.readAllLines(Paths.get(filename));
      // last index to slice page blocks
      var lastIndex = lines.size();
      // scan backwards to find '*PAGE' markers
      for (var i = lines.size() - 1; i >= 0; --i) {
        // check if line starts a new page block
        if (lines.get(i).startsWith("*PAGE")) {
          // add sublist representing that page
          pages.add(lines.subList(i, lastIndex));
          // update slice boundary
          lastIndex = i;
        }
      }
    } catch (FileNotFoundException e) {
      // print error when file is missing
      e.printStackTrace();
    }

     // reverse pages to restore original order
    Collections.reverse(pages);



}
