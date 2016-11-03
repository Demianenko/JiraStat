import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

/**
 * Created by user on 19.04.2016.
 */
public class LoginPage extends Page {

    String urlToTestLink = "https://jira.ringcentral.com/secure/Dashboard.jspa";
    String logNamePath = "//*[@id=\"login-form-username\"]";
    String logPasswordPath = "//*[@id=\"login-form-password\"]";
    String button = "//*[@id=\"login\"]";
    public LoginPage login(){
        wd.get(urlToTestLink);
        wd.manage().window().maximize();
        fillFormByXpath(logNamePath,logName);
        fillFormByXpath(logPasswordPath,logPassword);
        clickButtonByXpath(button);
        return this;
    }
    String searchPath = "//*[@id=\"quickSearchInput\"]";
    String searchText = "Text";
    public LoginPage search() {
        fillFormByXpath(searchPath, searchText);
        wd.findElement(By.xpath(searchPath)).sendKeys(Keys.ENTER);
        return this;
    }
    String newSearchPath = "//*[@id=\"advanced-search\"]";
    String newSearchAnswer = "//*[@id=\"content\"]/div[1]/div[4]/div/div/div/div/div/div/div[1]/div[1]/span/span[3]";
    public User sandRequest(Employee employee){
        String startDate = " and created >= \"2016/02/01\" ";
        String requestBody = " AND resolution in (\"Fixed\", \"Won't Fix\", \"Completed\",\"Unresolved\") AND type in (\"Bug\")";
        String low = "and priority = Low";
        String normal = "and priority = Normal";
        String high = "and priority = High";
        String critical = "and priority = Critical";
        String requestIncident = " AND type not in (\"Bug\")";
        String requestNot = " AND resolution in (\"Not A Bug\", Duplicate, \"Cannot Reproduce\") AND type in (\"Bug\")";
        int[] temp = new int[8];
        String requestStart = "reporter = ";
        String fullRequest = requestStart+employee.getName()+startDate;

        String ansLow = prepareToSend(fullRequest,requestBody+low);
        String ansNormal = prepareToSend(fullRequest,requestBody+normal);
        String ansHigh = prepareToSend(fullRequest,requestBody+high);
        String ansCritical = prepareToSend(fullRequest,requestBody+critical);

        String ansInc = prepareToSend(fullRequest,requestIncident);
        String ansNot = prepareToSend(fullRequest,requestNot);

        temp[0] = employee.getCases();
        temp[1] = sum(ansLow,ansNormal,ansHigh,ansCritical);
        temp[2] = sum(ansLow);
        temp[3] = sum(ansNormal);
        temp[4] = sum(ansHigh);
        temp[5] = sum(ansCritical);
        temp[6] = sum(ansInc);
        temp[7] = sum(ansNot);
        return new User(employee.getName(),temp);
    }
    private String prepareToSend(String fullReq, String type) {
        waitForAjax(500);
        String temp = fullReq+type;
        fillFormByXpath(newSearchPath,temp);
        wd.findElement(By.xpath(newSearchPath)).sendKeys(Keys.ENTER);
        waitForAjax(500);
        return textFromPage(newSearchAnswer);
    }

    private int sum(String...sArr){
        int answer = 0;
        for(String s: sArr) {
            answer = answer+Integer.parseInt(s);
        }
        return answer;
    }

}
