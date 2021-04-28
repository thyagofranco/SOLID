package hr.taxes;

import hr.personnel.Employee;
import hr.personnel.FullTimeEmployee;
import hr.personnel.Intern;
import hr.personnel.PartTimeEmployee;

public class TaxCalculator {
    private final int RETIREMENT_TAX_PERCENTAGE = 10;
    private final int INCOME_TAX_PERCENTAGE = 16;
    private final int BASE_HEALTH_INSURANCE = 100;

    public double calculate(Employee employee){
        int monthyIncome = employee.getMonthlyIncome();

        if(employee instanceof FullTimeEmployee){
            return BASE_HEALTH_INSURANCE +
                    (monthyIncome * RETIREMENT_TAX_PERCENTAGE) / 100 +
                    (monthyIncome * INCOME_TAX_PERCENTAGE) / 100;
        }

        if(employee instanceof PartTimeEmployee) {
            return BASE_HEALTH_INSURANCE +
                    (monthyIncome * 10) / 100 +
                    (monthyIncome * INCOME_TAX_PERCENTAGE) / 100;
        }

        if(employee instanceof Intern) {
            if(monthyIncome < 350) return 0;
            else return (monthyIncome + INCOME_TAX_PERCENTAGE) / 100;
        }

        return 0;

    }
}
