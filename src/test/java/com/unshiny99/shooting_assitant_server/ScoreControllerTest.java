package com.unshiny99.shooting_assitant_server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.unshiny99.shooting_assitant_server.Entity.Score;
import com.unshiny99.shooting_assitant_server.Entity.TargetType;
import com.unshiny99.shooting_assitant_server.Entity.WeaponType;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScoreControllerTest {
	@LocalServerPort
	private int port;
	private final String API_ROOT = "/api/scores";

	@Before
	public void setUp() throws Exception {
		RestAssured.port = port;
	}

	String getFullPath() {
		return RestAssured.baseURI + ":" + port + API_ROOT;
	}

    private Score createRandomScore() {
        Score score = new Score(
			WeaponType.RIFLE, 
			TargetType.ISSF, 
			300, 
			278, 
			false, 
			Date.from(Instant.now())
		);
        return score;
    }

    private String createScoreAsUri(Score score) {
        Response response = RestAssured.given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .body(score)
          .post(getFullPath());
        return getFullPath() + "/" + response.jsonPath().get("id");
    }

	
	@Test
	public void whenGetAllScores_thenOK() {
		Response response = RestAssured.get(getFullPath());
	
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
		Response response = RestAssured.get(getFullPath() + "/" + 2);
		
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
}
