package br.com.leonardo.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.leonardo.manager.LoggerManager;

public class WebScrapperService {
	
    public static String acessarSite(WebDriver driver) {
        try {
            driver.get("https://www.doweb.novaiguacu.rj.gov.br/portal/diario-oficial");
            driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[3]/div[1]/div[2]/div[2]/div[6]/a[2]")).click();
            Thread.sleep(5000);
            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
            return driver.getCurrentUrl();
        } catch (Exception e) {
            LoggerManager.logError(e.getMessage());
            return null;
        }
    }
}
