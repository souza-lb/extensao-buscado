package br.com.leonardo.manager;

// Fornece Driver para uso no Main e WebScrapper
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
    public static WebDriver setupDriver() {
        System.setProperty("webdriver.gecko.driver", "./gecko/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        // Habilita a função headless - não exibe tela do navegador.
        options.setHeadless(true);
       return new FirefoxDriver(options);
    }
}
