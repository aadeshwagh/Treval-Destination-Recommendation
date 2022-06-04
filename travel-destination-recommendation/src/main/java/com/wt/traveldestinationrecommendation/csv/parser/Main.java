package csv.parser;

import csv.parser.load.CSVLoader;
import csv.parser.load.CSVLoaderImp;
import filtering.Item;
import filtering.RatingMatrix;
import filtering.Recommendation;
import filtering.collaborative.filtering.CollaborativeFiltering;

import java.util.*;

public class Main {
    static CSVLoader csvLoader;
    public static void main(String[] args) {
        Main main = new Main();
        CSVLoader loader = new CSVLoaderImp();
        CSVSchema csvSchema = loader.LoadCSV("google_review_ratings.csv");
//        CSVReader csv = new CSVReaderImp(csvSchema);
//        CSVWrite writer = new CSVWrite(csvSchema,"google_review_ratings.csv");
//        Map<String ,Object> map = new HashMap<>();
        CollaborativeFiltering filtering = new CollaborativeFiltering();

        RatingMatrix ratingMatrix = filtering.createRatingMatrix(csvSchema);

        RatingMatrix avgM = filtering.averageRatings(ratingMatrix);

//        for(String user : avgM.getData().keySet().stream().limit(5).toList()){
//            List<Object> ratings = avgM.getData().get(user).stream().map(Item::getRating).toList();
//            System.out.println(ratings);
//        }
       //System.out.println(filtering.similarityMatrix(avgM,"User 1").stream().map(item->"{ "+item.getColumnName()+" = "+item.getRating()+" }").limit(1).toList());
        System.out.println(main.recommendations(filtering.similarityMatrix(avgM,"User 2") , ratingMatrix).stream().map(o->"{\nName: "+o.getName()+"\n"+"Priority: "+o.getPriority()+"\nVote: "+o.getVote()+"\n}").toList());



    }

    public List<Recommendation> recommendations(List<Item> items , RatingMatrix matrix ){
      int index = 0;
      String userName = "";
      for(int i = 0 ; i<items.size();i++){
          if(Double.parseDouble(items.get(i).getRating()+"") == 1.0){
              index =i;
          }
      }
      userName = items.get(index).getColumnName();
      items.remove(items.get(index));
      items.sort(new Comparator<Item>() {
          @Override
          public int compare(Item o1, Item o2) {
              if(Double.parseDouble(o2.getRating()+"") > Double.parseDouble(o1.getRating()+"")){
                  return 1;
              }else if(Double.parseDouble(o2.getRating()+"") < Double.parseDouble(o1.getRating()+"")){
                  return -1;
              }
              return 0 ;
          }
      });

       // System.out.println(items.stream().map(item->"{ "+item.getColumnName()+" = "+item.getRating()+" }").toList().get(0));
        List<Recommendation> temp= new ArrayList<>();
        List<Item> user = matrix.getData().get(userName);

        for(int i = 0 ; i< items.size();i++){

            for(Item item : matrix.getData().get(items.get(i).getColumnName())){
                Recommendation rec = null;
               double rat = Double.parseDouble(item.getRating().toString());
                //System.out.println(item.getRating()+"");
                if(rat > 3.0){
                    for (Recommendation recommendation : temp) {
                        if (recommendation.getName().equals(item.getColumnName())) {
                            rec = recommendation;
                            rec.setVote(recommendation.getVote() + 1);
                        }
                    }
                        if(rec==null) {
                            rec = new Recommendation();
                            rec.setName(item.getColumnName());
                            rec.setVote(1);
                            rec.setPriority(i+1);
                            temp.add(rec);
                        }

                    }

                }
            }
        List<Recommendation> result = new ArrayList<>();
        for(Recommendation recom : temp){
            for(int i = 0 ; i< user.size();i++){

                if(Double.parseDouble(user.get(i).getRating().toString())== 0.0 && user.get(i).getColumnName().equals(recom.getName())){
                    result.add(recom);
                }
            }

        }

      return  result;
    }


}
