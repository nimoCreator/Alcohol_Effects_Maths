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
                "yes", "no", "no", 4, 3, 4, 1, 1, 3, 6, 5, 6, 6));

        model = new Model(argValues, errorHandler, testData);
    }

    @Test
    void testLoadFromCSV() {
        model.loadFromCSV();
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
        assertEquals(0, Double.parseDouble(result.getValue()));
    }

    @Test
    void testSortAlcoholByAge() {
        model.sortAlcoholByAge();

        Results results = model.getResults();
        assertNotNull(results.getNthResult(0));

        Result result = results.getNthResult(0);
        assertEquals("WeeklyAgeConsumption", result.getName());
        assertEquals(2, Double.parseDouble(result.getValue()), result.getValue());
    }

    @Test
    void testFamilyRelInfluenceWalc() {
        model.familyRelInfluenceWalc();

        Results results = model.getResults();
        assertNotNull(results.getNthResult(0));

        Result result = results.getNthResult(0);
        assertEquals("FamilyRelInfluenceWalc", result.getName());
        assertEquals("FamilyRelInfluenceWalc", result.getName());
        assertEquals("baselineAverage: 4", results.getNthResult(1).getValue());
        assertEquals("totalWeekendAlcohol: 4", results.getNthResult(2).getValue());
        assertEquals("totalCount: 1", results.getNthResult(3).getValue());
        assertEquals("averageWeekendAlcohol: 4", results.getNthResult(4).getValue());
        assertEquals("FamilyRelInfluenceWalc: 0%", results.getNthResult(5).getValue());
    }

    @Test
    void testSchoolMaxFemaleDrinking() {
        model.schoolMaxFemaleDrinking();

        Results results = model.getResults();
        assertNotNull(results.getNthResult(0));

        Result result = results.getNthResult(0);
        assertEquals("SchoolWithHighestAlcoholConsumption", result.getName());
        assertEquals("SchoolWithHighestAlcoholConsumption", result.getName());
        assertEquals("GP (0.00%)", result.getValue());
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
