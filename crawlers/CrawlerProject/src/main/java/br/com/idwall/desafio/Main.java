package br.com.idwall.desafio;

//import java.io.File;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.opera.OperaDriver;
//import org.openqa.selenium.opera.OperaOptions;
//
//import br.com.idwall.desafio.model.SubredditThread;

public class Main {

	// private static final String OPERA_DRIVER = "operadriver.exe";
	// private static final StringBuilder BASE_URL = new
	// StringBuilder("http://www.reddit.com/r");
	// private static final String TOP_URL = "/top";
	// private static WebDriver driver;
	// private static Map<String, List<SubredditThread>> threadsList;
	//
	// public static void main(String[] args) {
	//
	// // Parameters passed via terminal
	// String[] subredditParameters = null;
	//
	// if (args.length >= 1) {
	// subredditParameters = args.toString().split(";");
	// } else {
	// System.out.println("You must give the parameters separated by ';'.." +
	// "\nStart the program again!");
	// }
	//
	// ini();
	//
	// for (String subreddit : subredditParameters) {
	// getSubredditInfo(subreddit);
	// }
	//
	// }
	//
	// private static void getSubredditInfo(String subredit) {
	// driver.get(BASE_URL.append(subredit).append(TOP_URL).toString());
	// }
	//
	// /**
	// * Opera Driver Configuration and System Initialization
	// */
	// private static void ini() {
	// ClassLoader classLoader = new Main().getClass().getClassLoader();
	// File file = new File(classLoader.getResource(OPERA_DRIVER).getFile());
	// OperaOptions options = new OperaOptions();
	// options.setBinary(new File("C:\\Program Files (x86)\\Opera\\launcher.exe"));
	// System.setProperty("webdriver.opera.driver", file.getAbsolutePath());
	//
	// // Initialize Variables
	// driver = new OperaDriver(options);
	// threadsList = new HashMap<>();
	// }

}
