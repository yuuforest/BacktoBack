// package com.backtoback.business.common.config;
//
// import java.util.List;
// import java.util.concurrent.TimeUnit;
//
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
//
// import lombok.extern.slf4j.Slf4j;
//
// /*
// 실행 코드
//
// WebDriverUtil webDriverUtil = new WebDriverUtil();
// webDriverUtil.useDriver("https://www.youtube.com/c/youtubekorea/videos");
//  */
// @Slf4j
// public class WebDriverUtil_lagacy {
//
// 	public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
// 	public static String WEB_DRIVER_PATH = "C:/chromedriver_win32/chromedriver.exe"; // WebDriver 경로
// 	private WebDriver driver;
//
// 	public WebDriverUtil_lagacy() {
// 		chrome();
// 	}
//
// 	// @PostConstruct
// 	// static void setupClass() {
// 	// 	WebDriverManager.chromedriver().setup();
// 	// }
//
// 	private void chrome() {
// 		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//
// 		// webDriver 옵션 설정.
// 		ChromeOptions options = new ChromeOptions();
// 		options.setHeadless(true);
// 		options.addArguments("--lang=ko");
// 		options.addArguments("--no-sandbox");
// 		options.addArguments("--disable-dev-shm-usage");
// 		// options.addArguments("headless"); //브라우저 안 띄움
// 		options.addArguments("--disable-gpu"); //gpu 비활성화
// 		// options.addArguments("--disable-popup-blocking"); //팝업 안 띄움
// 		options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안 받음
// 		options.addArguments("--remote-allow-origins=*");
// 		options.setCapability("ignoreProtectedModeSettings", true);
//
// 		// weDriver 생성.
// 		driver = new ChromeDriver(options);
// 		// driver = WebDriverManager.chromedriver().create();
// 		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
// 	}
//
// 	// @AspectConfig.PerCrawling
// 	public void useDriver(String date, String homeTeamCode, String awayTeamCode) {
// 		driver.get(
// 			String.format(
// 				"https://www.koreabaseball.com/Schedule/GameCenter/Main.aspx?gameDate=%s&gameId=20%s%s%s0&section=REVIEW",
// 				date,
// 				date,
// 				homeTeamCode,
// 				awayTeamCode
// 			));
// 		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
// 		log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
// 		// log.info(driver.getPageSource());
//
// 		WebElement tblScordboard = driver.findElement(By.id("tblScordboard1"));
// 		List<WebElement> teams = tblScordboard.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//
// 		for (WebElement t :
// 			teams) {
// 			log.info("$$$$$$$$$$ {}", t.getText());
// 		}
//
// 		//index 0 : 홈팀
// 		//index 1 : 원정팀
// 		WebElement team1 = teams.get(0).findElement(By.tagName("td"));
// 		WebElement team2 = teams.get(1).findElement(By.tagName("td"));
//
// 		if (team1.getText().equals("승")) {
// 			log.info("#########################homeTeam인 {} 가 승리###################", homeTeamCode);
// 		} else {
// 			log.info("#########################homeTeam인 {} 가 패배###################", homeTeamCode);
// 		}
//
// 		if (team2.getText().equals("승")) {
// 			log.info("#########################awayTeam인 {} 가 승리###################", awayTeamCode);
// 		} else {
// 			log.info("#########################awayTeam인 {} 가 패배###################", awayTeamCode);
// 		}
//
// 		quitDriver();
// 	}
//
// 	private void quitDriver() {
// 		driver.quit(); // webDriver 종료
// 	}
//
// }
