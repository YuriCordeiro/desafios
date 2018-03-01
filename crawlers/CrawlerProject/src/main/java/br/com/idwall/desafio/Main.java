package br.com.idwall.desafio;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class Main {

	private static final String OPERA_DRIVER = "operadriver.exe";
	private static WebDriver driver;

	public static void main(String[] args) {

		if (args.length >= 1) {
			String[] redditParameters = args.toString().split(";");
		}

		driver.get("https://www.reddit.com");

	}

	private static void teste(String subredits) {
		String[] subreditParameters = subredits.split(";");

		for (String subreditName : subreditParameters) {

		}
	}

	private static void ini() {
		ClassLoader classLoader = new Main().getClass().getClassLoader();
		File file = new File(classLoader.getResource(OPERA_DRIVER).getFile());

		OperaOptions options = new OperaOptions();
		options.setBinary(new File("C:\\Program Files (x86)\\Opera\\launcher.exe"));

		System.setProperty("webdriver.opera.driver", file.getAbsolutePath());

		driver = new OperaDriver(options);

	}

}
