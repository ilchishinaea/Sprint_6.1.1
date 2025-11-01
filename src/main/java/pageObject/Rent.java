package pageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Rent {

    private WebDriver driver;

    //поле "Когда привезти самокат"
    private By deliveryDateField = By.xpath(".//div[1]/div[1]/div/input");
    //поле варианты периода аренды
    private By rentalPeriodField = By.cssSelector(".Dropdown-control");
    //дропдаун варианты периода аренды
    private By rentalPeriodDropdown = By.className("Dropdown-option");
    //кнопка "Заказать" при оформлении заказа
    private By placeAnOrderButton = By.xpath(".//div[2]/div[3]/button[2]");
    //кнопка "Да" для подтверждения заказа
    private By confirmOrderYesButton = By.xpath(".//div[5]/div[2]/button[2]");
    //заголовок об успешном оформлении заказа
    private By orderSuccessHeader = By.xpath(".//div[2]/div[5]/div[1]");

    public Rent(WebDriver driver) {
        this.driver = driver;
    }

    //метод заполняет обязательные поля для аренды
    void setRequiredCustomerInfoForRental(String dateRent, String periodText){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(deliveryDateField));
        dateField.clear();
        dateField.sendKeys(dateRent);

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();

        WebElement periodField = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        periodField.click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rentalPeriodDropdown));
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(periodText)) {
                option.click();
                return;
            }
        }
        throw new RuntimeException("Не найден вариант аренды с текстом: " + periodText);

    }

    //метод кликает на кнопку "Заказать"
    public void clickPlaceAnOrderButton() {
        driver.findElement(placeAnOrderButton).click();
    }

    //метод кликает на кнопку "Да" для оформления заказа
    public void clickConfirmOrderYesButton() {
        driver.findElement(confirmOrderYesButton).click();
    }

    //метод оформляет аренду и создает заказ
    public void createRentalWithRequiredFields(String dateRent, String periodText){
        setRequiredCustomerInfoForRental(dateRent, periodText);
        clickPlaceAnOrderButton();
        clickConfirmOrderYesButton();
    }

    //метод возвращает локатор заголовка об успешном заказе
    public By getOrderSuccessHeader(){
        return orderSuccessHeader;
    }

}