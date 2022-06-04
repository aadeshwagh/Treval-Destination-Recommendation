package csv.parser.read;

import java.util.List;

public interface CSVReader {
    List<List<Object>> getRows();
    List<Object> getRow(int rowNo);
    List<String> getAllColumnNames();
    List<Object> getColumn(String name);

    int rowSize();
    int colSize();

}
