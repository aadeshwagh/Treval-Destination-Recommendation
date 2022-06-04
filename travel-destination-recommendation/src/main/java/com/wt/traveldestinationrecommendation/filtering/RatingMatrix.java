package filtering;

import java.util.List;
import java.util.Map;

//RatingMatrix should only contain numeric data it shouldn't contain column names
public class RatingMatrix {
    private Map<String,List<Item>> data;

    public Map<String,List<Item>> getData() {
        return data;
    }

    public void setData(Map<String,List<Item>> data) {
        this.data = data;
    }
}
