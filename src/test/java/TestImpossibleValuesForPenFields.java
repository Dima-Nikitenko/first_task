import org.testng.annotations.*;

public class TestImpossibleValuesForPenFields {

    @DataProvider
    public Object[][] dataForConstructor() {
        return new Object[][]{
                {-10, 10.0, "RED"},     // Test object creation with negative amount of ink in the Pen.
                {10, -10.0, "GREEN"},   // Test object creation with negative letter size.
                {10, 10.0, null},       // Test object creation with NULL ink color value.
        };
    }

    @Test(dataProvider = "dataForConstructor", expectedExceptions = IllegalArgumentException.class)
    void CreateObjectWithImpossibleFieldsValues(int ink, double size, String color) {

        Pen TIFV = new Pen(ink, size, color);
    }
}
