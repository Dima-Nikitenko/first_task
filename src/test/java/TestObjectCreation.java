import org.testng.Assert;
import org.testng.annotations.*;

public class TestObjectCreation extends Assert implements CreateObjectsInterface, GetFieldsValuesInterface {

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
}