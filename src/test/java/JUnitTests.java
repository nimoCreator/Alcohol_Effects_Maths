import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.polsl.classes.*;
import pl.polsl.model.Model;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTests {

    private Model model;

    @BeforeEach
    void setUp() {
        ArgValues argValues = new ArgValues("", "", false);
        ErrorHandler errorHandler = new ErrorHandler();

        List<StudentData> testData = new ArrayList<>();
        testData.add(new StudentData(
                "GP", "F", 18, "U", "GT3", "A", 4, 4, "at_home", "teacher",
                "course", "mother", 2, 2, 0, "yes", "no", "no", "no", "yes",
                "yes", "no", "no", 4, 3, 4, 1, 4, 3, 6, 5, 6, 6));
        testData.add(new StudentData(
                "GP", "M", 18, "U", "GT3", "A", 4, 4, "at_home", "teacher",
                "course", "mother", 2, 2, 0, "yes", "no", "no", "no", "yes",
                "yes", "no", "no", 4, 3, 4, 1, 2, 3, 6, 5, 6, 6));

        model = new Model(argValues, errorHandler, testData);
    }

    @Test
    void testLoadFromCSV() {
        //model.loadFromCSV();
        assertFalse(model.getData().isEmpty());
        for (StudentData student : model.getData()) {
            assertNotNull(student);
            assertNotNull(student.getSex());
        }
    }

    @Test
    void testPearsonCorrelation() {
        model.pearsonCorrelation();

        Results results = model.getResults();
        assertNotNull(results.getNthResult(0));

        Result result = results.getNthResult(0);
        assertEquals("PearsonCorrelation", result.getName());
        assertEquals(0.9999999999999998, Double.valueOf(result.getValue()));
    }

    @Test
    void testSortAlcoholByAge() {
        model.sortAlcoholByAge();

        Results results = model.getResults();
        assertNotNull(results.getNthResult(0));
       
        assertEquals(3.33, Double.parseDouble(results.getNthResult(1).getValue()));
    }

    @Test
    void testFamilyRelInfluenceWalc() {
        model.familyRelInfluenceWalc();

        Results results = model.getResults();
        assertNotNull(results.getNthResult(0));

        assertEquals("3.0", results.getNthResult(0).getValue());
        assertEquals("10.0", results.getNthResult(1).getValue());
        assertEquals("3", results.getNthResult(2).getValue());
        assertEquals("3.3333333333333335", results.getNthResult(3).getValue());
        assertEquals("11.111111111111116", results.getNthResult(4).getValue());
    }

    @Test
    void testSchoolMaxFemaleDrinking() {
        model.schoolMaxFemaleDrinking();

        Results results = model.getResults();
        assertNotNull(results.getNthResult(0));

        assertEquals("", results.getNthResult(0).getValue());
    }

    @Test
    void testCalculateAll() {
        Results results = model.calculateAll();
        assertNotNull(results.getNthResult(0));
        for (Result result : results.getResultsList()) {
            assertNotNull(result);
        }
    }
}
