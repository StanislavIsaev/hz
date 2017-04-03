package ua.bot.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.bot.domain.Announcement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static javax.swing.text.html.HTML.Tag.A;
import static org.openqa.selenium.By.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by stani on 02-Apr-17.
 */
@Service
@Getter
@Setter
@Log
public class KlubokService {
    @Value("${klubok.login}")
    private String klubokLogin;
    @Value("${klubok.password}")
    private String klubokPassword;
    @Value("${klubok.url}")
    private String klubokURL;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private ChromeDriverService chromeDriverService;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private ChromeDriver driver;

    @PostConstruct
    private void init() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File chromeDriver = new File(classLoader.getResource("driver/chromedriver.exe").getFile());
        chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(chromeDriver)
                .usingAnyFreePort()
                .build();
        chromeDriverService.start();

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("user-data-dir=C:/Users/SEED/AppData/Local/Google/Chrome/User Data");
        driver = new ChromeDriver(chromeDriverService, options);
        driver.get(getKlubokURL());
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.findElementById("login-btn").click();

        WebElement loginForm = new WebDriverWait(driver, 3).until(visibilityOfElementLocated(name("loginForm")));

        loginForm.findElement(name("email")).sendKeys(getKlubokLogin());
        loginForm.findElement(name("password")).sendKeys(getKlubokPassword());

        loginForm.findElement(tagName("button")).click();
        log.info("Klubok is open");
    }

    public void addAnnouncement(Announcement announcement) {
        driver.findElementByClassName("add-item-head")
                .findElement(tagName(A.toString()))
                .click();
        WebElement anounceForm = new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(id("add-anounce")));
        WebElement summary = anounceForm.findElement(id("item-summary"));
        summary.sendKeys(announcement.getSummary());

        WebElement description = anounceForm.findElement(id("item-description"));
        description.sendKeys(announcement.getDescription());

        WebElement price = anounceForm.findElement(id("item-price"));
        price.sendKeys(String.valueOf(announcement.getPrice()));

        WebElement category = anounceForm.findElement(id("section"));
//        //"Детские вещи"
        new Select(category).selectByVisibleText(announcement.getCategory());

        WebElement subCategory = anounceForm.findElement(cssSelector(".select-category:not(.b-hidden)"));
        //"Одежда"
        new Select(subCategory).selectByVisibleText(announcement.getSubCategory());

        WebElement type = anounceForm.findElement(className("sub-select-category"));
        new Select(type).selectByVisibleText(announcement.getType());

        announcement.getPhotos().stream().forEach(file -> {
            WebElement photo = anounceForm.findElement(id("b-form__photo-upload"));
            photo.sendKeys(file.getAbsolutePath());
            new WebDriverWait(driver, 20)
                    .until(presenceOfElementLocated(cssSelector(String.format("div[data-origin-name$='%s']", file.getName()))));
        });
    }

    @PreDestroy
    private void destroy() {
        driver.quit();
        chromeDriverService.stop();
        log.info("Klubok is closed");
    }
}
