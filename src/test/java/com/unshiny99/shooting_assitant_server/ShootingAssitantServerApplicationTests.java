package com.unshiny99.shooting_assitant_server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.unshiny99.shooting_assitant_server.Entity.Score;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest
class ShootingAssitantServerApplicationTests {
	@Test
	void contextLoads() {
	}

	private static final String API_ROOT = "http://localhost:8080/api/scores";

    private Score createRandomScore() {
        Score score = new Score();
        score.setName("test nom");
        score.setDate(Date.from(new Date().toInstant()));
        return score;
    }

    private String createScoreAsUri(Score score) {
        Response response = RestAssured.given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .body(score)
          .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

	@Test
	public void whenGetAllScores_thenOK() {
		Response response = RestAssured.get(API_ROOT);
	
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void whenGetCreatedScoreById_thenOK() {
		Score score = createRandomScore();
		String location = createScoreAsUri(score);
		Response response = RestAssured.get(location);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals(score.getName(), response.jsonPath().get("name"));
	}

	@Test
	public void whenGetNotExistScoreById_thenNotFound() {
		Response response = RestAssured.get(API_ROOT + "/" + 2);
		
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
}
