package com.example.spark.service;

import com.example.spark.model.Count;
import com.example.spark.model.Word;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

@Service
public class WordCounterService {

    private final SparkSession sparkSession;

    @Autowired
    public WordCounterService(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    @Async
    public List<Count> count(){
        String input = "hello world ooo hello hello hello asd qqq ooo ooo ooo";
        String[] _words = input.split(" ");

        List<Word> words = Arrays.stream(_words).map(Word::new).collect(Collectors.toList());

        Dataset<Row> dataFrame = sparkSession.createDataFrame(words, Word.class);
        dataFrame.show();
        //StructType structType = dataFrame.schema();

        RelationalGroupedDataset groupedDataset = dataFrame.groupBy(col("word"));
        groupedDataset.count().show();
        List<Row> rows = groupedDataset.count().collectAsList();//JavaConversions.asScalaBuffer(words)).count();

        return rows
                .stream()
                .map( row -> new Count(row.getString(0), row.getLong(1)))
                .collect(Collectors.toList());
    }
}
