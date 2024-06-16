package com.unshiny99.shooting_assitant_server.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unshiny99.shooting_assitant_server.ScoreNotFoundException;
import com.unshiny99.shooting_assitant_server.Entity.Score;
import com.unshiny99.shooting_assitant_server.Repository.ScoreRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    @Autowired
    private ScoreRepository scoreRepository;
    private ObjectMapper mapper = new ObjectMapper();

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

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Score newScore) throws JsonProcessingException {
        Optional<Score> optionalScore = scoreRepository.findById(id);

        if (optionalScore.isPresent()) {
            Score score = optionalScore.get();
            score.setWeaponType(newScore.getWeaponType());
            score.setTargetType(newScore.getTargetType());
            score.setTotalPointsMax(newScore.getTotalPointsMax());
            score.setTotalPointsDone(newScore.getTotalPointsDone());
            score.setIsTournament(newScore.getIsTournament());
            score.setDate(newScore.getDate());
            
            // update the object
            scoreRepository.save(score);

            String response = mapper.writeValueAsString(score);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le score n'a pas été trouvé");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<Score> score = scoreRepository.findById(id);

        if (score.isPresent()) {
            scoreRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Le score a bien été supprimé");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le score n'a pas été trouvé");
        }
    }
}
