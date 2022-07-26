package testing;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.io.IOException;
import java.util.List;


public class Test1 extends BaseSeleniumPage {
    @FindBy(xpath = "//input[@aria-label='Please type your destination']")
    private WebElement destination;
    @FindBy(xpath = " //ul[@class='c-autocomplete__list sb-autocomplete__list sb-autocomplete__list-with_photos -visible']//li[@data-label='New York, New York State, United States']")
    private WebElement choseCity;
    @FindBy(xpath = "//div[@class=\"bui-calendar__control bui-calendar__control--next\"]")
    private WebElement calendarNext;
    @FindBy(xpath = "//td[@data-date='2022-12-01']")
    private WebElement checkinDate;
    @FindBy(xpath = "//td[@data-date='2022-12-30']")
    private WebElement checkoutDate;
    @FindBy(xpath = "//div[@class='sb-searchbox-submit-col -submit-button ']")
    private WebElement buttonSearch;
    @FindBy(xpath = "//span[@data-testid='address']")
    private List<WebElement> addressOnPage;
    @FindBy(xpath = "//a[@class=\"e13098a59f\"]")
    private List<WebElement> datesOnPage;
    @FindBy(xpath = "//span[@aria-label='1 December 2022']")
    private List<WebElement> dateOfStartList;

    public Test1() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
        String urlFromApplicationProperties = System.getProperty("url");
        driver.get(urlFromApplicationProperties);
        PageFactory.initElements(driver, this);
    }

    public void start(String destinationValue) {
        Actions actions = new Actions(driver);
        destination.click();
        destination.sendKeys(destinationValue);
        choseCity.click();

        while (dateOfStartList.size() == 0) {
            calendarNext.click();
        }

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", checkinDate);
        checkinDate.click();
        actions.moveToElement(checkoutDate).click().build().perform();
        buttonSearch.click();

        if (addressOnPage.stream().filter((e) -> !(e.getText().contains(destinationValue))).toList().size() > 0) {
            throw new AssertionError("There are results that do not match the search location");
        }

        if (datesOnPage.stream().filter(a -> a.getAttribute("href").contains("checkin=2022-12-01")).toList().size() < datesOnPage.size()) {
            throw new AssertionError("There are results that do not match the first date ");


        }

        if (datesOnPage.stream().filter(a -> a.getAttribute("href").contains("checkout=2022-12-30")).toList().size() < datesOnPage.size()) {
            throw new AssertionError("There are results that do not match the last date ");
        }

    }


}
