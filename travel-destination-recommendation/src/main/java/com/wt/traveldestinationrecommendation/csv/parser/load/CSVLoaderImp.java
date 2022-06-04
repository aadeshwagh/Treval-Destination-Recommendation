package csv.parser.load;

import csv.parser.CSVSchema;
import csv.parser.exception.NoSuchFileException;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CSVLoaderImp implements CSVLoader{
    @Override
    public CSVSchema LoadCSV(String path) {
        File file = new File(path);
        if(!file.exists()){
            throw new NoSuchFileException("File with path "+path+"does not exists");
        }
        String extension = FilenameUtils.getExtension(file.getName());
        if(!extension.equals("csv")){
            throw new RuntimeException("Only csv files are allowed");
        }
        CSVSchema csvSchema =new CSVSchema();
        List<List<Object>> data = resolveData(file);
        csvSchema.setData(data);
        csvSchema.setRowSize(data.size()-1);
        csvSchema.setColSize(data.get(0).size());
        return csvSchema;

    }
    private List<List<Object>> resolveData(File file){
        List<List<Object>> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line=br.readLine())!=null){
                List<Object> obj = new ArrayList<>();
                for(String str : line.split(",")){
                    obj.add(resolveDataType(str));
                }
                list.add(obj);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private Object resolveDataType(String str) {
        try {
            return Integer.parseInt(str);
        }catch (Exception e){
                try {
                    return Double.parseDouble(str);
                }catch (Exception e2){
                    try {
                        return Float.parseFloat(str);
                    }catch (Exception e3){
                        return str;
                    }
                }
            }
        }

    }

