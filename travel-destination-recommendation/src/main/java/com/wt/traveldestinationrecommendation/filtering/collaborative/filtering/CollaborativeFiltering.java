package filtering.collaborative.filtering;

import csv.parser.CSVSchema;
import csv.parser.read.CSVReader;
import csv.parser.read.CSVReaderImp;
import filtering.Item;
import filtering.RatingMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CollaborativeFiltering {
    public RatingMatrix averageRatings(RatingMatrix matrix) {
        RatingMatrix matrix1 =new RatingMatrix();
        Map<String,List<Item>> map = new HashMap<>();
        for(String user : matrix.getData().keySet()){
            List<Object> ratings = matrix.getData().get(user).stream().map(Item::getRating).toList();
            double average = average(ratings);
            List<Item> list = new ArrayList<>();
            for(Item item : matrix.getData().get(user)){
                Item item1 = new Item();
                item1.setRating(Double.parseDouble(item.getRating().toString())-average);
                item1.setColumnName(item.getColumnName());
                list.add(item1);
            }
            map.put(user,list);
        }
        matrix1.setData(map);
        return matrix1;
    }
    public double average( List<Object> array){
        double average = 0;
        for(Object o : array){
            average+=Double.parseDouble(o.toString());
        }
        return average/array.size();
    }
    public RatingMatrix createRatingMatrix(CSVSchema schema){
        List<List<Object>> data = schema.getData();
        CSVReader reader = new CSVReaderImp(schema);
        List<String> col = reader.getAllColumnNames();
            Map<String, List<Item>> map = new HashMap<>();
            for (List<Object> obj : data.subList(1,data.size())) {
                List<Item> list = new ArrayList<>();
                for (int i = 1; i < obj.size(); i++) {
                    Item item = new Item();
                    item.setColumnName(col.get(i));
                    item.setRating(obj.get(i));
                    list.add(item);
                }
                map.put(obj.get(0).toString(), list);
            }


        RatingMatrix ratingMatrix =new RatingMatrix();
        ratingMatrix.setData(map);

        return ratingMatrix;
    }

    public List<Item> similarityMatrix(RatingMatrix averageRatings , String userName){
        List<Item> similarityList = new ArrayList<>();
        List<Double> userList = averageRatings.getData().get(userName).stream().map(o-> Double.parseDouble(o.getRating().toString())).toList();
        for(String user: averageRatings.getData().keySet()){
            double value = similarity(userList , averageRatings.getData().get(user).stream().map(o->Double.parseDouble(o.getRating().toString())).toList());
            Item item = new Item();
            item.setColumnName(user);
            item.setRating(value);
            similarityList.add(item);
        }
        return  similarityList;

    }
    public double similarity(List<Double> userList , List<Double> list){
        double sum =0;
        double sqSum1=0;
        double sqSum2=0;
        for(int i = 0 ; i<userList.size();i++){
            double mul = userList.get(i)*list.get(i);
            sum+=mul;
            sqSum1+= Math.pow(userList.get(i),2);
            sqSum2+= Math.pow(list.get(i),2);
        }

        return sum/(Math.sqrt(sqSum1)*Math.sqrt(sqSum2));

    }
}
