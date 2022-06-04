package csv.parser.write;

import csv.parser.CSVSchema;
import csv.parser.exception.NoSuchFileException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSVWrite implements CSVWriteOperations{
    private final CSVSchema csvSchema;
    private final String path;
    public CSVWrite (CSVSchema csvSchema,String path){
        this.csvSchema = csvSchema;
        this.path =path;
    }

    @Override
    public void updateRow(int rowNo, Map<String,Object> map) {
        List<Object> list = csvSchema.getData().get(rowNo);

        for(String s : map.keySet()){
            int index = csvSchema.getData().get(0).indexOf(s);
            list.set(index,map.get(s));
        }
        update(csvSchema);

    }

    @Override
    public void insertRow(Map<String,Object> map) {
        List<List<Object>> data = csvSchema.getData();
        List<Object> list = new ArrayList<Object>(Collections.nCopies(csvSchema.getColSize(), 0));

        for(String s : map.keySet()){
            int index = data.get(0).indexOf(s);
            list.set(index,map.get(s));
        }
        File file = new File(path);
        if(!file.exists()){
            throw new NoSuchFileException("no file with path "+path+" found.");
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            StringBuilder line= new StringBuilder();
            for(int i = 0 ; i<list.size();i++){
                if(i<list.size()-1){
                    line.append(list.get(i)).append(",");
                }else{
                    line.append(list.get(i));
                }
            }
            bw.write(line.toString());
            bw.newLine();
            bw.flush();
            bw.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void update(CSVSchema csvSchema){
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            for(List<Object> list :csvSchema.getData()){
                StringBuilder line= new StringBuilder();
                for(int i = 0 ; i<list.size();i++){
                    if(i<list.size()-1){
                        line.append(list.get(i)).append(",");
                    }else{
                        line.append(list.get(i));
                    }
                }
                bw.write(line.toString());
                bw.newLine();
                bw.flush();

            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
