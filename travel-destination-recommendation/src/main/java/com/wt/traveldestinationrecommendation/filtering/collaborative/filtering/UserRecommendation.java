package com.wt.traveldestinationrecommendation.filtering.collaborative.filtering;

import com.wt.traveldestinationrecommendation.filtering.Item;
import com.wt.traveldestinationrecommendation.filtering.RatingMatrix;
import com.wt.traveldestinationrecommendation.filtering.Recommendation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserRecommendation {
    public List<Recommendation> recommendations(List<Item> items , RatingMatrix matrix,String userName ){
        int index = 0;

        items.remove(items.get(index));
        items.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Double.compare(Double.parseDouble(o2.getRating() + ""), Double.parseDouble(o1.getRating() + ""));

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
        if(result.isEmpty()){
            return temp;
        }
        return  result;
    }

}
