package hr.persistence;

import hr.personnel.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SubcontractorRepository {
    private SubcontractorSerializer serializer;

    public SubcontractorRepository(SubcontractorSerializer serializer) {
        this.serializer = serializer;
    }

    public List<Subcontractor> findAll() {

        // Subcontractors are kept in memory for simplicity
        Subcontractor anna = new Subcontractor("Rebekah Jackson", "rebekah@gmail.com",3000,15);
        Subcontractor billy = new Subcontractor("Harry Jackson", "harry@gmail.com",3000,15);


        return Arrays.asList(anna, billy);
    }

    public void save(Subcontractor subcontractor) throws IOException {
        String serializedString = this.serializer.serialize(subcontractor);

        Path path = Paths.get(subcontractor.getName()
                .replace(" ", "_") + ".rec");
        Files.write(path, serializedString.getBytes());
    }
}
