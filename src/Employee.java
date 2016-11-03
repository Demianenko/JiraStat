/**
 * Created by user on 18.08.2016.
 */
public class Employee {
    private String name;
    private int cases;
    public int[] work;

    public Employee(String name, int cases) {
        this.name = name;
        this.cases = cases;
    }

    public String getName() {
        return name;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }
}
