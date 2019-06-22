package TestNG;

import org.testng.annotations.DataProvider;


public class LoginData {
	@DataProvider
	public static Object[][] loginData() {
		return new Object[][] { { "mngr204279", "UgUdUhu" }, { "mngr204281", "zedEjat" }, { "mngr204282", "rEhEhyh" } };
	}
}
