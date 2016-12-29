import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 22.03.2016.
 */
public class TestProject extends Page{
    String loginSelector = "//*[@id=\"login\"]";
    String passwordSelector = "//*[@name=\"tl_password\"]";
    String loginButton =  "//*[@name=\"login_submit\"]";
    String titleBarFrame = "titlebar";
    String projectSelector = "//*[@name=\"testproject\"]";
    String projectName = "RC UBP ALL";

    String mainFrame = "mainframe";
    String testPlanSelector = "//*[@name=\"testplan\"]";
    String planName = "8.0 Telco SWAT 8.0";

    public TestProject loginTestLink(String urlToTestLink) {
        wd.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
        wd.get(urlToTestLink);
        wd.manage().window().maximize();
        fillFormByXpath(loginSelector,logName);
        fillFormByXpath(passwordSelector,"McLaud85");
        clickButtonByXpath(loginButton);
        return this;
    }
    public TestProject selectProject() {
        wd.switchTo().frame(titleBarFrame);
        clickButtonByXpath(projectSelector);
        selectValueFromDropdown(projectSelector,projectName);
        return this;
    }
    public TestProject selectTestPlan() {
        wd.switchTo().defaultContent();
        wd.switchTo().frame(mainFrame);
        clickButtonByXpath(testPlanSelector);
        selectValueFromDropdown(testPlanSelector,planName);
        return this;
    }

    public void goToResult(String address){
        wd.get(address);
    }

    public List<Employee> grab() {
        List<WebElement> elementsName = wd.findElements(By.xpath("//*[@class='x-grid-group-body']//tr/td[2]//a"));
        List<WebElement> elementsValue = wd.findElements(By.xpath("//*[@class='x-grid-group-body']//tr/td[3]/div"));
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < elementsName.size(); i++) {
            Employee employee = new Employee(elementsName.get(i).getText(),Integer.parseInt(elementsValue.get(i).getText()));
            list.add(employee);
        }
        return list;
    }





}
