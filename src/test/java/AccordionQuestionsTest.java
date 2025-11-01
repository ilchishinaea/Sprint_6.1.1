import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccordionQuestionsTest {

    private static WebDriver driver;

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "chrome");
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);

        } else if (browser.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.setAcceptInsecureCerts(true);
            driver = new FirefoxDriver(options);
        }
    }

    @ParameterizedTest
    @MethodSource("accordionData")
    void checkAccordionText(By accordionLocator, By textLocator, String expectedText) {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        WebElement webElement = driver.findElement(accordionLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
        WebElement element = driver.findElement(textLocator);
        if (element.isDisplayed()) {
            String actual = element.getText();
            assertEquals(expectedText, actual);
        }
    }

    private static Stream<Arguments> accordionData() {
        return Stream.of(
                Arguments.of(By.id("accordion__heading-0"), By.xpath(".//div/div[1]/div[2]/p[1]"),
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(By.id("accordion__heading-1"), By.xpath(".//div/div[2]/div[2]/p[1]"),
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(By.id("accordion__heading-2"), By.xpath(".//div/div[3]/div[2]/p[1]"),
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of(By.id("accordion__heading-3"), By.xpath(".//div/div[4]/div[2]/p[1]"),
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(By.id("accordion__heading-4"), By.xpath(".//div/div[5]/div[2]/p[1]"),
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(By.id("accordion__heading-5"), By.xpath(".//div/div[6]/div[2]/p[1]"),
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of(By.id("accordion__heading-6"), By.xpath(".//div/div[7]/div[2]/p[1]"),
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(By.id("accordion__heading-7"), By.xpath(".//div/div[8]/div[2]/p[1]"),
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}