package com.unshiny99.shooting_assitant_server.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.unshiny99.shooting_assitant_server.Model.Score;
import com.unshiny99.shooting_assitant_server.Repository.ScoreRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ScoreController {
    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/scores")
    public List<Score> getScores() {
        return scoreRepository.findAll();
    }
    
}
