import org.testng.Assert;
import org.testng.annotations.*;

public class TestGetColorMethod extends Assert implements CreateObjectsInterface, GetFieldsValuesInterface {

    Pen obj = new Pen(1);

    @Parameters({ "AmountOfInk" }) // specified in the testng.xml file
    @Test
    void ReturnDefaultColor(int ink) throws NoSuchFieldException, IllegalAccessException {

        Pen RDC = createObjectUsingOneArgumentConstructor(ink);

        String expected = getFieldValue(RDC, "color");
        assertEquals(RDC.getColor(), expected);
    }

    @Parameters({ "AmountOfInk", "SizeOfLetter", "WhatColor" }) // specified in the testng.xml file
    @Test(dependsOnMethods = { "ReturnDefaultColor" })
    void ReturnColorAfterItsChange(int ink, double size, String color) throws NoSuchFieldException, IllegalAccessException {

        Pen DefaultColorObject = createObjectUsingOneArgumentConstructor(ink);
        Pen RCAIC = createObjectUsingThreeArgumentConstructor(ink, size, color);

        if (color != getFieldValue(DefaultColorObject, "color")) {
            assertEquals(RCAIC.getColor(), color);
        } else System.out.println("Test color is the same as the default one.");
    }
}
