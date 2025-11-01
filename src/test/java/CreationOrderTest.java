import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.HomePage;
import pageObject.Order;
import pageObject.Rent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreationOrderTest {

    private WebDriver driver;
    private HomePage homePage;
    private Order order;
    private Rent rent;

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "firefox");
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);

        } else if (browser.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            //options.addArguments("--headless");
            options.setAcceptInsecureCerts(true);
            driver = new FirefoxDriver(options);
        }

        driver.get("https://qa-scooter.praktikum-services.ru/");

        homePage = new HomePage(driver);
        order = new Order(driver);
        rent = new Rent(driver);

        WebElement acceptCookiesButton = driver.findElement(homePage.getAcceptCookiesButton());
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acceptCookiesButton);
    }

    @Test
    void clickingOrderButtonTopShouldOpenOrderForm(){
        homePage.clickOrderButtonTop();
        String actual = driver.findElement(order.getOrderHeader()).getText();
        assertEquals("Для кого самокат", actual, "Неверный заголовок начальной страницы заказа или страница заказа не открылась");
    }

    @Test
    void clickingOrderInButtonBottomShouldOpenOrderForm(){
        homePage.clickOrderInButtonBottom();
        String actual = driver.findElement(order.getOrderHeader()).getText();
        assertEquals("Для кого самокат", actual, "Неверный заголовок начальной страницы заказа или страница заказа не открылась");
    }

    @ParameterizedTest
    @MethodSource("clientsAndRentData")
    void submitClientAndRentalForms_orderIsCreated(String name, String surname, String address,
                                                   String mobile, String dateRent, String periodRent){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement orderButtonTop = wait.until(ExpectedConditions.elementToBeClickable(homePage.getOrderButtonTop()));
        orderButtonTop.click();
        order.setRequiredCustomerInfoForOrder(name, surname, address, mobile);
        order.clickNextButton();
        rent.createRentalWithRequiredFields(dateRent, periodRent);
        String actual = driver.findElement(rent.getOrderSuccessHeader()).getText();

        assertEquals("Заказ оформлен\nНомер заказа: .  Запишите его:\nпригодится, чтобы отслеживать статус", actual, "Ожидаемый результат не совпал с фактическим");
    }

    private static Stream<Arguments> clientsAndRentData() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String tomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        return Stream.of(
                Arguments.of("Катерина", "Тестова", "г.Москва, ул.Тестовая", "79154677935", today, "трое суток"),
                Arguments.of("Василий", "Тестов", "г.Химки, ул.Ленина", "79001112233", tomorrow, "сутки")
        );
    }



    @AfterEach
    void teardown() {
        driver.quit();
    }
}