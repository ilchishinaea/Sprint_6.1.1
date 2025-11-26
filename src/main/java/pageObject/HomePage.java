package pageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    //кнопка "Заказать" в шапке
    private By orderButtonTop = By.xpath(".//div/div/div[1]/div[2]/button[1]");
    //кнопка "Заказать" в конце страницы
    private By orderInButtonBottom = By.xpath(".//div[2]/div[5]/button");
    //кнопка для принятия куки
    private By acceptCookiesButton = By.className("App_CookieButton__3cvqF");

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    //метод кликает по кнопке "Заказать" в шапке страницы
    public void clickOrderButtonTop(){
        driver.findElement(orderButtonTop).click();
    }

    //метод кликает по кнопке "Заказать" внизу страницы
    public void clickOrderInButtonBottom(){
        driver.findElement(orderInButtonBottom).click();
    }

    //вернуть локатор кнопки принятия кук
    public By getAcceptCookiesButton() {
        return acceptCookiesButton;
    }

    //вернуть локатор кнопки "Заказать" в шапке
    public By getOrderButtonTop() {
        return orderButtonTop;
    }
}