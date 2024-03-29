package com.unshiny99.shooting_assitant_server.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.unshiny99.shooting_assitant_server.Model.Score;

public interface ScoreRepository extends CrudRepository<Score, Long> {
    List<Score> findAll();
}