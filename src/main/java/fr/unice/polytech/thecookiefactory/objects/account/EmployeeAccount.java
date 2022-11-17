package fr.unice.polytech.thecookiefactory.objects.account;

public abstract class EmployeeAccount extends Account {

    private final String employeePosition;

    public EmployeeAccount(String employeePosition) {
        super();
        this.employeePosition = employeePosition;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }
}
