import org.testng.Assert;
import org.testng.annotations.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestDoSomethingElseMethod extends Assert implements CreateObjectsInterface, GetFieldsValuesInterface {

    PrintStream setSystemOut = System.out;
    ByteArrayOutputStream captureBytes = new ByteArrayOutputStream();

    @BeforeMethod
    public void setup() {
        System.setOut(new PrintStream(captureBytes));
    }

    @Parameters({ "AmountOfInk" }) // specified in the testng.xml file
    @Test
    void SystemOutPrintlnWithDefaultColor(int ink) throws NoSuchFieldException, IllegalAccessException {

        Pen SOPWDC = createObjectUsingOneArgumentConstructor(ink);

        String expected = getFieldValue(SOPWDC,"color");
        SOPWDC.doSomethingElse();
        assertEquals(captureBytes.toString().trim(), expected);
    }

    @Parameters({ "AmountOfInk", "SizeOfLetter", "WhatColor" }) // specified in the testng.xml file
    @Test
    void SystemOutPrintlnWithChangedColor(int ink, double size, String color) {

        Pen SOPWCC = createObjectUsingThreeArgumentConstructor(ink, size, color);

        SOPWCC.doSomethingElse();
        assertEquals(captureBytes.toString().trim(), color.trim());
    }

    @AfterMethod
    public void teardown() {
        System.setOut(setSystemOut);
        captureBytes.reset();
    }
}