package hr.persistence;

import hr.personnel.Subcontractor;

public class SubcontractorSerializer {

    public String serialize(Subcontractor subcontractor) {
        StringBuilder sb = new StringBuilder();

        sb.append("### SUBCONTRACOTR RECORD ####");
        sb.append(System.lineSeparator());
        sb.append("NAME: ");
        sb.append(subcontractor.getName());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("EMAIL: ");
        sb.append(subcontractor.getEmail());
        sb.append(System.lineSeparator());
        sb.append("MONTHLY WAGE: ");
        sb.append(subcontractor.getMonthlyIncome());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
