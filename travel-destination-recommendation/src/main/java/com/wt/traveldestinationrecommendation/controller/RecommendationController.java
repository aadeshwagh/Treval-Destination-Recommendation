package com.wt.traveldestinationrecommendation.controller;

import com.wt.traveldestinationrecommendation.Utility;
import com.wt.traveldestinationrecommendation.csv.parser.CSVSchema;
import com.wt.traveldestinationrecommendation.csv.parser.load.CSVLoader;
import com.wt.traveldestinationrecommendation.csv.parser.load.CSVLoaderImp;
import com.wt.traveldestinationrecommendation.csv.parser.read.CSVReader;
import com.wt.traveldestinationrecommendation.csv.parser.read.CSVReaderImp;
import com.wt.traveldestinationrecommendation.csv.parser.write.CSVWrite;
import com.wt.traveldestinationrecommendation.filtering.Item;
import com.wt.traveldestinationrecommendation.filtering.RatingMatrix;
import com.wt.traveldestinationrecommendation.filtering.collaborative.filtering.CollaborativeFiltering;
import com.wt.traveldestinationrecommendation.filtering.collaborative.filtering.UserRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

@Controller
public class RecommendationController {

    //get recommendations for user
    //get all categories
    //add user rating
    //update user ratings
    private static final String path= "/home/aadeshwagh/Full-stack/Travel-recommendation/travel-destination-recommendation/src/main/resources/static/google_review_ratings.csv";
    private CSVReader reader;
    private final CSVLoader loader;
    private CSVWrite writer;

    @Autowired
    Utility utility;
    RecommendationController(){
        this.loader =new CSVLoaderImp();

    }
    @GetMapping("/")
    public String home(){

        return "index";
    }
    @GetMapping("/getLoginPage")
    public String lpage(){

        return "login";
    }
    @PostMapping("/getRecom")
    public String login(@RequestBody MultiValueMap<String, String> formdata, Model model){
        String userId = formdata.getFirst("userId");
        String password = formdata.getFirst("password");

        List<String> recomCat = getUserRecommendations(userId);
        model.addAttribute("categories",recomCat);
        model.addAttribute("userId",userId);
        return "recom";
    }
    @PostMapping ("/updateRating")
    public String rate(@RequestBody MultiValueMap<String, String> formdata,Model model){
        File file = new File(path);
        CSVSchema schema = loader.LoadCSV(file);
        this.writer = new CSVWrite(schema,file);
        this.reader = new CSVReaderImp(schema);
        Map<String,Object> map = new HashMap<>();
        String userId = formdata.getFirst("userId");
        String des = formdata.getFirst("cat");
        String cat = "";
        for(String str : utility.categories.keySet()){
            if(utility.categories.get(str).equals(des)){
                cat = str;
            }
        }
        map.put(cat,formdata.getFirst("rating"));
        int index = reader.getColumn("User").indexOf(userId)+1;

        writer.updateRow(index,map);

        MultiValueMap<String,String> map1 = new LinkedMultiValueMap<>();
        List<String> list =new ArrayList<>() ;
        list.add(userId);
        map1.put("userId",list);
        map1.put("password",list);
        return login(map1,model);
    }

    public List<String> getUserRecommendations(String id) {
        File file = new File(path);
        CSVSchema schema = loader.LoadCSV(file);
        this.reader = new CSVReaderImp(schema);
        //System.out.println(reader.getColumn("User"));
        if(!reader.getColumn("User").contains(id)){
            System.out.println("no");

        }
        CollaborativeFiltering filtering = new CollaborativeFiltering();

        RatingMatrix ratingMatrix = filtering.createRatingMatrix(schema);

        RatingMatrix avgM = filtering.averageRatings(ratingMatrix);

        List<Item> items = filtering.similarityMatrix(avgM, id);
        UserRecommendation recommendation =new UserRecommendation();
        return recommendation.recommendations(items,ratingMatrix,id).stream().map((obj)->{
         return utility.categories.get(obj.getName().toString());
        }).toList();

    }
    @GetMapping("/getAllCat")
    public List<String> getAllCategories(){
        File file = new File(path);
        CSVSchema schema = loader.LoadCSV(file);
        this.reader = new CSVReaderImp(schema);

        return reader.getAllColumnNames().subList(1,reader.getAllColumnNames().size());
    }


    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
        return e.getMessage();
    }

}
