package com.backtoback.business;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.backtoback.business.common.config.WebDriverUtil;
import com.backtoback.business.common.dto.GameResultCrawlingDto;
import com.backtoback.business.game.service.GameService;
import com.backtoback.business.team.domain.TeamCode;

@SpringBootTest
class BusinessApplicationTests {

	@Autowired
	GameService gameService;

	@Test
	void afterGameResultTest() {
		gameService.afterGameResult(8L);
	}

	@Test
	void contextLoads() {
		GameResultCrawlingDto gameResultCrawlingDto = GameResultCrawlingDto.builder()
			.date("230516")
			.homeTeamCode(TeamCode.valueOf("OB"))
			.awayTeamCode(TeamCode.valueOf("WO"))
			.build();

		WebDriverUtil util = new WebDriverUtil(gameResultCrawlingDto) {
			@Override
			public Object process(WebDriver driver, Object param) {
				GameResultCrawlingDto gameResultCrawlingDto = (GameResultCrawlingDto)param;

				WebElement tblScordboard = driver.findElement(By.id("tblScordboard1"));
				List<WebElement> teams = tblScordboard.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

				//index 0 : 홈팀
				//index 1 : 원정팀
				WebElement team1 = teams.get(0).findElement(By.tagName("td"));
				WebElement team2 = teams.get(1).findElement(By.tagName("td"));

				if (team1.getText().equals("승")) {
					System.out.println("#########################homeTeam인 " + gameResultCrawlingDto.getHomeTeamCode()
						+ "가 승리###################");
				} else {
					System.out.println("#########################homeTeam인 " + gameResultCrawlingDto.getHomeTeamCode()
						+ "가 패배###################");
				}

				if (team2.getText().equals("승")) {
					System.out.println("#########################homeTeam인 " + gameResultCrawlingDto.getAwayTeamCode()
						+ "가 승리###################");
				} else {
					System.out.println("#########################homeTeam인 " + gameResultCrawlingDto.getAwayTeamCode()
						+ "가 패배###################");
				}
				return param;
			}
		};

		util.useDriver(
			String.format(
				"https://www.koreabaseball.com/Schedule/GameCenter/Main.aspx?gameDate=%s&gameId=20%s%s%s0&section=REVIEW",
				gameResultCrawlingDto.getDate(),
				gameResultCrawlingDto.getDate(),
				gameResultCrawlingDto.getHomeTeamCode(),
				gameResultCrawlingDto.getAwayTeamCode()
			));
	}

	@Test
	void gameSchedule() {
		GameResultCrawlingDto gameResultCrawlingDto = GameResultCrawlingDto.builder()
			.date("230516")
			.homeTeamCode(TeamCode.valueOf("OB"))
			.awayTeamCode(TeamCode.valueOf("WO"))
			.build();

		WebDriverUtil util = new WebDriverUtil(gameResultCrawlingDto) {
			@Override
			public Object process(WebDriver driver, Object param) {

				driver.findElement(By.xpath("//select[@id='ddlMonth']/option[text()='04']"))
					.click();
				WebElement table = driver.findElement(By.id("tblSchedule")).findElement(By.tagName("tbody"));

				List<WebElement> trs = table.findElements(By.tagName("tr"));
				// for (int i = 0; i )

				System.out.println(table.getText());

				return param;
			}
		};

		util.useDriver(
			"https://www.koreabaseball.com/Schedule/Schedule.aspx"
		);
	}

}
