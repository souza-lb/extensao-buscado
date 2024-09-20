package br.com.leonardo.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
    public static WebDriver setupDriver() {
        // Utiliza o WebDriverManager na gestão do driver.
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        // Habilita a opção headless para não exibir tela do navegador.
        options.setHeadless(true);
        
        return new FirefoxDriver(options);
    }
}
