package com.unshiny99.shooting_assitant_server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

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

	private String updateScoreAsUri(int id, Score score) {
        Response response = RestAssured.given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .body(score)
          .put(getFullPath() + "/" + id);
        return getFullPath() + "/" + response.jsonPath().get("id");
    }

	private Response deleteScoreAsUri(long id) {
        Response response = RestAssured.given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .delete(getFullPath() + "/" + id);
        return response;
    }
	
	@Test
	public void whenGetAllScores_thenOK() {
		Response response = RestAssured.get(getFullPath());
	
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	
	@Test
	public void whenGetCreatedScoreById_thenOK() throws ParseException {
		Score score = createRandomScore();
		String location = createScoreAsUri(score);
		Response response = RestAssured.get(location);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals(score.getWeaponType().toString(), response.jsonPath().get("weaponType"));
		assertEquals(score.getTargetType().toString(), response.jsonPath().get("targetType"));
		assertEquals(score.getTotalPointsMax(), response.jsonPath().get("totalPointsMax"));
		assertEquals(score.getTotalPointsDone(), response.jsonPath().get("totalPointsDone"));
		assertEquals(score.getName(), response.jsonPath().get("name"));
		assertEquals(score.getIsTournament(), response.jsonPath().get("isTournament"));
		assertEquals(score.getDate(), dateFormatter.parse(response.jsonPath().get("date")));
		assertEquals(score.getComment(), response.jsonPath().get("comment"));
	}

	@Test
	public void whenGetNotExistScoreById_thenNotFound() {
		Response response = RestAssured.get(getFullPath() + "/" + 2);
		
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
		assertEquals(response.getBody().asString(), "Score non trouvé");
	}

	@Test
	public void whenGetUpdatedScoreById_thenOK() throws ParseException {
		Score oldScore = createRandomScore();
		String location = createScoreAsUri(oldScore);
		Response responseTemp = RestAssured.get(location);

		int scoreId = responseTemp.jsonPath().getInt("id");
		Score score = new Score(
			WeaponType.RIFLE, 
			TargetType.ISSF, 
			300, 
			279, 
			false, 
			Date.from(Instant.now())
		);
		String location2 = updateScoreAsUri(scoreId, score);
		Response response = RestAssured.get(location2);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals(score.getWeaponType().toString(), response.jsonPath().get("weaponType"));
		assertEquals(score.getTargetType().toString(), response.jsonPath().get("targetType"));
		assertEquals(score.getTotalPointsMax(), response.jsonPath().get("totalPointsMax"));
		assertEquals(score.getTotalPointsDone(), response.jsonPath().get("totalPointsDone"));
		assertEquals(score.getName(), response.jsonPath().get("name"));
		assertEquals(score.getIsTournament(), response.jsonPath().get("isTournament"));
		assertEquals(score.getDate(), dateFormatter.parse(response.jsonPath().get("date")));
		assertEquals(score.getComment(), response.jsonPath().get("comment"));
	}

	@Test
	public void whenDeleteScoreById_thenOK() {
		Score oldScore = createRandomScore();
		String location = createScoreAsUri(oldScore);
		Response responseTemp = RestAssured.get(location);

		int scoreId = responseTemp.jsonPath().getInt("id");

		Response response = deleteScoreAsUri(scoreId);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals(response.getBody().asString(), "Le score a bien été supprimé");
	}
}
