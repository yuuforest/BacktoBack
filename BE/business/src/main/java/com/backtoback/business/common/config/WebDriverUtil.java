package com.backtoback.business.common.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.extern.slf4j.Slf4j;

/*
효진이의 왕감동 리팩토링 스토리
 */
@Slf4j
public abstract class WebDriverUtil {

	public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
	// @Value("${web.driver.path}")
	public static String WEB_DRIVER_PATH = "C:/chromedriver_win32/chromedriver.exe"; // WebDriver 경로
	private final Object param;
	private WebDriver driver;

	public WebDriverUtil(Object param) {
		chrome();
		this.param = param;
	}

	// @PostConstruct
	// static void setupClass() {
	// 	WebDriverManager.chromedriver().setup();
	// }

	private void chrome() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// webDriver 옵션 설정.
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		options.addArguments("--lang=ko");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		// options.addArguments("headless"); //브라우저 안 띄움
		options.addArguments("--disable-gpu"); //gpu 비활성화
		// options.addArguments("--disable-popup-blocking"); //팝업 안 띄움
		options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안 받음
		options.addArguments("--remote-allow-origins=*");
		options.setCapability("ignoreProtectedModeSettings", true);

		// weDriver 생성.
		driver = new ChromeDriver(options);
		// driver = WebDriverManager.chromedriver().create();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	public abstract Object process(WebDriver driver, Object param);

	public Object useDriver(String url) {
		// 1. driver url, 초기 설정
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
		log.info("###########driver 작동 시작#############");

		// 2. 비즈니스 로직 처리
		Object result = process(this.driver, this.param);

		// 3. driver 종료 (자원 반납)
		quitDriver();

		return result; //비즈니스 로직 처리 결과물 반환
	}

	private void quitDriver() {
		driver.quit(); // webDriver 종료
	}

}
