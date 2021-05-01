package hr.main;

import hr.logging.ConsoleLogger;
import hr.persistence.EmployeeFileSerializer;
import hr.persistence.EmployeeRepository;
import hr.persistence.SubcontractorRepository;
import hr.persistence.SubcontractorSerializer;
import hr.personnel.Employee;
import hr.personnel.ServiceLicenseAgreement;
import hr.personnel.Subcontractor;

import java.util.List;

public class ApproveSLAMain {
    public static void main(String[] args) {
        // Create dependencies
        ConsoleLogger consoleLogger = new ConsoleLogger();
        SubcontractorSerializer serializer = new SubcontractorSerializer();
        SubcontractorRepository repository = new SubcontractorRepository(serializer);

        // Define SLA
        int minTimeOffPercent = 98;
        int maxResolutionDays = 2;
        ServiceLicenseAgreement companySla = new ServiceLicenseAgreement(
                minTimeOffPercent,
                maxResolutionDays);

        // Grab subcontractors
        List<Subcontractor> subcontractors = repository.findAll();

        for(Subcontractor s: subcontractors){
            s.approveSLA(companySla);
        }
    }
}
