package hr.personnel;

public class Subcontractor {

    private String name;
    private String email;
    private int monthlyIncome;
    private int nbHoursPerWeek;

    public Subcontractor(String name, String email, int monthlyIncome, int nbHoursPerWeek) {
        this.name = name;
        this.email = email;
        this.monthlyIncome = monthlyIncome;
        this.nbHoursPerWeek = nbHoursPerWeek;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getNbHoursPerWeek() {
        return nbHoursPerWeek;
    }

    public boolean approveSLA(ServiceLicenseAgreement sla) {
        /*
        Business logic for approving a
        service license agreement for a
        for a subcontractor
         */
        boolean result = false;
        if (sla.getMinUptimePercent() >= 98
                && sla.getProblemResolutionTimeDays() <= 2) {
            result=  true;
        }

        System.out.println("SLA approval for " + this.getName() + ": " + result);
        return result;
    }
}
