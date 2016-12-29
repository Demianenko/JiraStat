import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by user on 19.04.2016.
 */
public class Page {
    protected String logName = "sergei.demyanenko";
    protected String logPassword ="Erlond+85!";
    public WebDriver wd = new ChromeDriver();
    public int implicitlyWait = 120;
    private void waiting(By selector) {
        new WebDriverWait(wd,implicitlyWait).until(ExpectedConditions.elementToBeClickable(selector));
    }
    protected void waitingVisible(By selector) {
        new WebDriverWait(wd, implicitlyWait).until(ExpectedConditions.visibilityOfElementLocated(selector));

    }
    protected void waitForAjax(int timeoutInSeconds) {
        //System.out.println("Checking active ajax calls by calling jquery.active");
        try {
            if (wd instanceof JavascriptExecutor) {
                JavascriptExecutor jsDriver = (JavascriptExecutor)wd;

                for (int i = 0; i< timeoutInSeconds; i++)
                {
                    Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
                    // return should be a number
                    if (numberOfAjaxConnections instanceof Long) {
                        Long n = (Long)numberOfAjaxConnections;
                        //System.out.println("Number of active jquery ajax calls: " + n);
                        if (n.longValue() == 0L)
                            break;
                    }
                    Thread.sleep(1000);
                }
            }
            else {
                System.out.println("Web driver: " + wd + " cannot execute javascript");
            }
        }
        catch (InterruptedException| WebDriverException e) {
            System.out.println(e);
        }
    }

    protected void clickButtonByXpath(String selector) {
        WebElement element = null;
        waiting(By.xpath(selector));
        element = wd.findElement((By.xpath(selector)));
        waitForAjax(250000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
    }
    protected void selectValueFromDropdown(String selector, String choose) {
        Select dropdown = new Select(wd.findElement(By.xpath(selector)));
        dropdown.selectByVisibleText(choose);

    }

    protected void fillFormByXpath( String selector, String info) {
        waiting(By.xpath(selector) );
        wd.findElement(By.xpath(selector)).click();
        waitForAjax(10000);
        wd.findElement(By.xpath(selector)).clear();
        wd.findElement(By.xpath(selector)).sendKeys(info);

    }

    protected String textFromPage(String path){
        waitForAjax(1000);
        if (wd.findElements(By.xpath(path)).size()==0) {
            return "0";
        }
        try{
            waitingVisible(By.xpath(path));
            return wd.findElement(By.xpath(path)).getText();
        }
        catch (WebDriverException e) {
            return "0";
        }


    }
}
