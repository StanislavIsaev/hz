package ua.bot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by SEED on 30.03.2017.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Test.class.getClassLoader();
        File chromeDriver = new File(classLoader.getResource("driver/chromedriver.exe").getFile());
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(chromeDriver)
                .usingAnyFreePort()
                .build();
        service.start();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("user-data-dir=C:/Users/SEED/AppData/Local/Google/Chrome/User Data");
        ChromeDriver driver = new ChromeDriver(service, options);
        driver.get("https://klubok.com");
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.findElementById("login-btn").click();

        WebElement loginForm = new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.name("loginForm")));
        /*
        loginForm.findElement(By.name("email")).sendKeys("");
        loginForm.findElement(By.name("password")).sendKeys("");

        loginForm.findElement(By.tagName("button")).click();
        */
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /*
        driver.quit();
        service.stop();
        */
    }

}
