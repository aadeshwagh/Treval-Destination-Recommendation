package csv.parser;

import csv.parser.read.CSVReaderImp;

import java.util.List;

public class CSVSchema {
    private int rowSize;
    private int colSize;
    List<List<Object>> data;

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }
}
