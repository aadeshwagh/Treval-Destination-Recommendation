package csv.parser.read;

import csv.parser.CSVSchema;
import csv.parser.exception.NoSuchColumnException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderImp implements CSVReader{
    private final CSVSchema csv;
    public CSVReaderImp(CSVSchema csv){
        this.csv = csv;
    }

    @Override
    public List<List<Object>> getRows() {
        return csv.getData().subList(1,csv.getRowSize());
    }

    @Override
    public List<Object> getRow(int rowNo) {
        return csv.getData().get(rowNo);
    }

    @Override
    public List<String> getAllColumnNames() {
        return csv.getData().get(0).stream().map(Object::toString).toList();
    }

    @Override
    public List<Object> getColumn(String name) {
        List<Object> column = new ArrayList<>();
        int index = getAllColumnNames().indexOf(name);
        if(index==-1){
            throw new NoSuchColumnException("No column with name "+name+" present in dataset");
        }
        for(List<Object> obj : csv.getData().subList(1,csv.getRowSize())){
            for(int i = 0 ; i<obj.size();i++){
                if(index==i){
                    column.add(obj.get(i));
                }
            }
        }
        return column;

    }

    @Override
    public int rowSize() {
        return csv.getRowSize();
    }

    @Override
    public int colSize() {
        return csv.getColSize();
    }

}
