import org.testng.Assert;
import org.testng.annotations.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class TestAuxiliaryMethods extends Assert implements CreateObjectsInterface, GetFieldsValuesInterface {

    Random rand = new Random();
    PrintStream setSystemOut = System.out;
    ByteArrayOutputStream captureBytes = new ByteArrayOutputStream();

    @BeforeGroups({"TestOutputDefaultColor", "TestOutputChangedColor"})
    public void setup() {
        System.setOut(new PrintStream(captureBytes));
    }

    @AfterGroups({"TestOutputDefaultColor", "TestOutputChangedColor"})
    public void teardown() {
        System.setOut(setSystemOut);
        captureBytes.reset();
    }


    @Parameters({ "AmountOfInk" }) // specified in the testng.xml file
    @Test(groups = { "TestOutputDefaultColor" })
    void SystemOutPrintlnWithDefaultColor(int ink) throws NoSuchFieldException, IllegalAccessException {

        Pen SOPWDC = createObjectUsingOneArgumentConstructor(ink);

        String expected = getFieldValue(SOPWDC,"color");
        SOPWDC.doSomethingElse();
        assertEquals(captureBytes.toString().trim(), expected);
    }

    @Parameters({ "AmountOfInk", "SizeOfLetter", "WhatColor" }) // specified in the testng.xml file
    @Test(groups = { "TestOutputChangedColor" })
    void SystemOutPrintlnWithChangedColor(int ink, double size, String color) {

        Pen SOPWCC = createObjectUsingThreeArgumentConstructor(ink, size, color);

        SOPWCC.doSomethingElse();
        assertEquals(captureBytes.toString().trim(), color.trim());
    }

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

        Pen RCAIC = createObjectUsingThreeArgumentConstructor(ink, size, color);
        assertEquals(RCAIC.getColor(), color);
    }

    @Test
    void isWorkTrue() {
        Pen iWT = createObjectUsingOneArgumentConstructor(rand.nextInt(2147483647) + 1);
        assertEquals(iWT.isWork(), true);
    }

    @Test
    void isWorkFalse() {
        Pen iWF = createObjectUsingOneArgumentConstructor(rand.nextInt(2147483647) - 2147483647);
        assertEquals(iWF.isWork(), false);
    }
}