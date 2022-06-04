package com.wt.traveldestinationrecommendation.controller;
import com.wt.traveldestinationrecommendation.csv.parser.CSVSchema;
import com.wt.traveldestinationrecommendation.csv.parser.load.CSVLoader;
import com.wt.traveldestinationrecommendation.csv.parser.load.CSVLoaderImp;
import com.wt.traveldestinationrecommendation.csv.parser.read.*;
import com.wt.traveldestinationrecommendation.csv.parser.write.CSVWrite;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private static final String path= "/home/aadeshwagh/Full-stack/Travel-recommendation/travel-destination-recommendation/src/main/resources/static/google_review_ratings.csv";
    private CSVReader reader;
    private final CSVLoader loader;
    private final CSVWrite writer;
    UserController(){
        this.loader =new CSVLoaderImp();
        File file = new File(path);
        CSVSchema schema = loader.LoadCSV(file);
        this.reader = new CSVReaderImp(schema);
        this.writer = new CSVWrite(schema,file);
    }
    //add user
    //get All of users

    @GetMapping("/getAllUsers")
    public List<String> getAllUsers(){


       return reader.getColumn("User").stream().map(Object::toString).toList();

    }
    @PostMapping("/addUser")
    public int addUser(){
        CSVSchema schema = loader.LoadCSV(new File(path));
        this.reader = new CSVReaderImp(schema);
        int userId = reader.rowSize()+1;
        String user = "User "+userId;
        Map<String , Object> map = new HashMap<>();
        map.put("User",user);
       writer.insertRow(map);
       return userId;
    }

}
