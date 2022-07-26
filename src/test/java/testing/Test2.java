package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Test2 extends BaseSeleniumPage {
    @FindBy(xpath = "//button[@id='onetrust-accept-btn-handler']")
    private WebElement cookiesButtonAccept;
    @FindBy(xpath = "//span[contains(text(), 'Attractions')]")
    private WebElement attractionsButton;
    @FindBy(xpath = "//input[@placeholder='Where are you going?']")
    private WebElement inputDestination;
    @FindBy(xpath = "//span[@class='db29ecfbe2' and contains(text(), 'Museums')]")
    private WebElement choseMuseums;
    @FindBy(xpath = "//span[@class='db29ecfbe2' and contains(text(), 'US$21 - US$41')]")
    private WebElement chosePrise;
    @FindBy(xpath = "//span[@class='db29ecfbe2' and contains(text(), 'New York')]")
    private WebElement choseCity;
    @FindBy(xpath = "//div[@class='css-1rrebqu']")
    private List<WebElement> cityList;
    @FindBy(xpath = "//div/div/div/div[@class='css-pori7h']/div[@style='text-align: right;']/div[@class='ac78a73c96' and contains(text(), 'US')]")
    private List<WebElement> priceList;


    public Test2() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
        String urlFromApplicationProperties = System.getProperty("url");
        driver.get(urlFromApplicationProperties);
        PageFactory.initElements(driver, this);

    }

    public void accountCheck(String city) {
        Actions actions = new Actions(driver);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        attractionsButton.click();
        cookiesButtonAccept.click();
        inputDestination.click();
        inputDestination.sendKeys(city);

        WebElement until = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='db29ecfbe2' and contains(text(), 'New York, New York State')]")));
        actions.moveToElement(until).click().build().perform();


        executor.executeScript("arguments[0].scrollIntoView(true);", choseMuseums);
        choseMuseums.click();
        executor.executeScript("arguments[0].scrollIntoView(true);", chosePrise);
        chosePrise.click();
        executor.executeScript("arguments[0].scrollIntoView(true);", choseCity);
        choseCity.click();
        if (cityList.stream().filter((e) -> !(e.getText().contains(city))).toList().size() > 0) {
            throw new AssertionError("There are results that do not match the search location");
        }


        if (priceList.stream().map(s -> (s.getText().replaceAll("[^0-9,]", "").replaceAll(",", "."))).filter(e -> Float.parseFloat(e) > 41).toList().size() > 0) {
            throw new AssertionError("There are results that do not match the price of the search");
        }


    }
}
