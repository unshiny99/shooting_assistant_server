package com.unshiny99.shooting_assitant_server.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.unshiny99.shooting_assitant_server.ScoreNotFoundException;
import com.unshiny99.shooting_assitant_server.Entity.Score;
import com.unshiny99.shooting_assitant_server.Repository.ScoreRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping
    public List<Score> findAll() {
        return scoreRepository.findAll();
    }

    @GetMapping("/{id}")
    public Score findOne(@PathVariable Long id) {
        return scoreRepository.findById(id)
          .orElseThrow(() -> new ScoreNotFoundException("Le score n'a pas été trouvé", null));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Score create(@RequestBody Score score) {
        return scoreRepository.save(score);
    }
}
