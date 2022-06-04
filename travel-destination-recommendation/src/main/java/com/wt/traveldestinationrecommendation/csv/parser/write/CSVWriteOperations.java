package csv.parser.write;

import java.util.List;
import java.util.Map;

public interface CSVWriteOperations {
    void insertRow(Map<String,Object> map);
    //void removeNullValues();
    void updateRow(int rowNo, Map<String,Object> values);
}
