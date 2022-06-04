package csv.parser.load;

import csv.parser.CSVSchema;

public interface CSVLoader {
    CSVSchema LoadCSV(String name);

}
