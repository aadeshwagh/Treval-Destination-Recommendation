package com.wt.traveldestinationrecommendation.csv.parser.load;


import com.wt.traveldestinationrecommendation.csv.parser.CSVSchema;

import java.io.File;

public interface CSVLoader {
    CSVSchema LoadCSV(File file);

}
