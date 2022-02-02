import org.testng.Assert;
import org.testng.annotations.*;

public class TestObjectCreation extends Assert implements CreateObjectsInterface, GetFieldsValuesInterface {

    @DataProvider
    public Object[][] dataForConstructor() {
        return new Object[][]{
                {-10, 10.0, "RED"},     // Test object creation with negative amount of ink in the Pen.
                {10, -10.0, "GREEN"},   // Test object creation with negative letter size.
                {10, 10.0, null},       // Test object creation with NULL ink color value.
        };
    }

    @Parameters({ "AmountOfInk", "SizeOfLetter", "WhatColor" }) // specified in the testng.xml file
    @Test
    /* No need to test each separate constructor, since the Pen.class uses' constructor overloading by this() reference.
       It means that the third constructor won't create an object if the other two don't work. */
    void CreateObjectUsingAllConstructors(Integer ink, Double size, String color) throws NoSuchFieldException, IllegalAccessException {

        Pen COUAC = createObjectUsingThreeArgumentConstructor(ink, size, color);

        String[] actual = getAllFieldsValues(COUAC);
        String[] expected = {ink.toString(), size.toString(), color};
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "dataForConstructor")
    void CreateObjectWithImpossibleFieldsValues(int ink, double size, String color) {

        try {
            Pen TIFV = new Pen(ink, size, color);
            fail("No IllegalArgumentException exception was caught.");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Successfully caught exception:" + e);
        }
    }
}