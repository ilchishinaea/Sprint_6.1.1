package pageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Order {

    private WebDriver driver;

    //поле "Имя" для заказа
    private By nameField = By.xpath(".//div[2]/div[1]/input");
    //поле "Фамилия" для заказа
    private By surnameField = By.xpath(".//div[2]/div[2]/input");
    //поле "Адрес" для заказа
    private By addressField = By.xpath(".//div[2]/div[3]/input");
    //поле "Станции метро"
    private By stationsInput = By.xpath(".//div[4]/div/div[1]/input");
    //кнопка станция метро "Бульвар Рокоссовского"
    private By stationBulvarRokosovskogo = By.xpath(".//div[2]/ul/li[1]/button");
    //поле "Телефон" для заказа
    private By mobileField = By.xpath(".//div[2]/div[5]/input");
    //кнопка "Далее" при заказе
    private By nextButton = By.xpath(".//div[2]/div[3]/button");
    //залоголовок формы для заполнения данных клиента
    private By orderHeader = By.className("Order_Header__BZXOb");

    public Order(WebDriver driver){
        this.driver = driver;
    }

    //метод заполняет обязательные поля: имя, фамилия, адрес, телефон и выбирает станцию метро
    public void setRequiredCustomerInfoForOrder(String name, String surname, String address, String mobile){
        driver.findElement(nameField).clear();
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).clear();
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).clear();
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(stationsInput).click();
        driver.findElement(stationBulvarRokosovskogo).click();
        driver.findElement(mobileField).clear();
        driver.findElement(mobileField).sendKeys(mobile);
    }

    //метод нажимает на кнопку "Далее"
    public void clickNextButton(){
        driver.findElement(nextButton).click();
    }

    //метд возвращает локатор заголовка формы для заполнения данных клиента
    public By getOrderHeader(){
        return orderHeader;
    }
}