package com.example.spark.controller;

import com.example.spark.model.Count;
import com.example.spark.service.WordCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CountController {

    private final WordCounterService wordCounterService;

    @Autowired
    public CountController(WordCounterService wordCounterService) {
        this.wordCounterService = wordCounterService;
    }

    @RequestMapping("")
    public ResponseEntity<List<Count>> getCount(){
        return new ResponseEntity<List<Count>>(wordCounterService.count(), HttpStatus.OK);
    }
}
