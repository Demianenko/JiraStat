import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19.04.2016.
 */
public class Main {
    public static ArrayList<User> users = new ArrayList<>();
    public static void main(String[] args) {
        String urlToTestLink = "http://testlink.dins.ru/index.php";
        TestProject testLinkProject = new TestProject();
        testLinkProject.loginTestLink(urlToTestLink);
        String rc831 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2109146";
        String rc822 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2063232";
        String rc821 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2044387";
        String rc812 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=1994455";
        String rc811 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=1994431";
        String w811 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=1994449";
        String w812 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=1994451";
        String w821 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2047660";
        String w822 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2062366";
        String w831 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2114533";


        String r901 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2269845";
        String w901 = "http://testlink.dins.ru/lib/results/resultsByTesterPerBuild.php?format=0&tplan_id=2269717";

       /* testLinkProject.goToResult(w811);
        List<Employee> mapWOneOne = testLinkProject.grab();

        testLinkProject.goToResult(w812);
        List<Employee> mapWOneTwo = testLinkProject.grab();

        testLinkProject.goToResult(rc812);
        List<Employee> mapOneTwo = testLinkProject.grab();

        testLinkProject.goToResult(rc811);
        List<Employee> mapOneOne = testLinkProject.grab();

        testLinkProject.goToResult(w821);
        List<Employee> mapWTwoOne = testLinkProject.grab();

        testLinkProject.goToResult(w822);
        List<Employee> mapWTwoTwo = testLinkProject.grab();

        testLinkProject.goToResult(w831);
        List<Employee> mapWThreeOne = testLinkProject.grab();

        testLinkProject.goToResult(rc831);
        List<Employee> mapThreeOne = testLinkProject.grab();

        testLinkProject.goToResult(rc822);
        List<Employee> mapTwoTwo = testLinkProject.grab();

        testLinkProject.goToResult(rc821);
        List<Employee> mapTwoOne = testLinkProject.grab();*/

        testLinkProject.goToResult(w901);
        List<Employee> mapW = testLinkProject.grab();

        testLinkProject.goToResult(r901);
        List<Employee> mapR = testLinkProject.grab();


        List<Employee> finaleList = sumList(mapW,mapR);
        System.out.println("STOP");

       // Employee[] nameArray = {new Employee("dima.shlomin",0)};
        String[] nameArray = {
                "alexey.kudlaenko","sergei.demyanenko","mykhailo.somov","viktoria.strahova","sergey.shamshurin","svyatoslav.polozov","norman.kalyuzhny",
                "sergey.visotskiy","veronika.genkinova","stanislav.gurushchuk",
                "sergey.kharchenko","roman.gavriluk","leonid.haidanov",
                "yulia.samorokovskaya","dima.shlomin","olha.mudrak","vladimir.tishchenko",
                "kirill.khrinenko","pavel.polskiy","oleksii.gych","oleksandr.frolov","dmitriy.rumelets","lidiia.borisenko","alexander.gorb","alexey.katelin",
                "sergiy.alyeksyeyenko"};

       /* String[] nameArray = {"alexander.rozdorojny","Alexander.Zudenko","alexandr.kononenko","andrey.ignatenko",
                "artem.demeshkevich","arthur.bratikov", "dmytro.zagorodnjuk","dmitriy.dzyuba","dmitry.ternovoy",
                "elena.yeremenko","kirill.ivanov","lena.kakovkina","mariia.malinovska","maxim.kolesnik",
                "maxim.shabrov","olexiy.stratulat","pavel.popov","sergey.magdenko","sergey.medvediev","serhii.drobinko",
                "stanislav.koskov","victoriia.liubchenko","tatyana.vasylkova", "valentin.semikov",
                "Irina.bogomolova"};*/
        LoginPage page = new LoginPage();
        LoginPage requestPage = page.login().search();
        System.out.println("|Name| "+"|ALL Test Cases| "+"|All good bug| "+"|Low| "+"|Normal| "+"|High| "+"|Critical| "+"|Incident| "+"|Not a bug|");
        for(Employee employee: finaleList){
            printAnswer(requestPage.sandRequest(employee));
            System.out.println();
        }

        System.out.println("Stop+");

    }
    public static void printAnswer(User u){
        users.add(u);
        System.out.print(u.name+" ");
        for (int i: u.work) {
            System.out.print(i);
            System.out.print(" ");
        }
    }
    public static void employeeListPrint(List<Employee> list) {
        for(Employee e:list){
            System.out.println(e.getName()+" "+e.getCases());
        }
    }
    public static List<Employee> sumList(List<Employee>...lists){
        List<Employee> resultList = new ArrayList<>();
        List<Employee> external = new ArrayList<>();
        for (List<Employee> list: lists) {
            for(Employee out: list) {
                if(resultList.size()==0){
                    resultList = new ArrayList<>(list);
                } else {
                    boolean notExist = true;
                    for (Employee in: resultList){
                        if(in.getName().equals(out.getName())){
                            in.setCases(in.getCases()+out.getCases());
                            notExist = false;
                            break;
                        }
                    }
                    if(notExist) {
                        external.add(out);
                    }
                }
            }
            for(Employee em: external) {
                resultList.add(em);
            }
            external = new ArrayList<>();
        }
        return resultList;
    }
    public static List listsOfCases(TestProject testLinkProject, String...addresses){
        List answer = new ArrayList<>();
        for(String s:addresses){
            testLinkProject.goToResult(s);
            List<Employee> temp = testLinkProject.grab();
            answer.add(temp);
        }

        return answer;
    }
}
