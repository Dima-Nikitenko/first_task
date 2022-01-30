import org.testng.Assert;
import org.testng.annotations.*;

public class TestWriteMethod extends Assert implements CreateObjectsInterface, GetFieldsValuesInterface {

    @DataProvider
    public Object[][] dataForValidCases() {
        return new Object[][]{
                {10, 1.0, "SampleText"},
                {5, 1.0, "SampleText"},
                {10, 1.0, ""},
                {1, 0.1, "SampleText"},
        };
    }

    @DataProvider
    public Object[][] dependenceOfSubstringLengthOnLetterSize() {
        return new Object[][]{
                {4, 1.1, 1.8E+307, "Test"},
                {1, 1.0E-1, 1.8E+307, "SampleTestText"},
        };
    }

    @DataProvider
    public Object[][] inkAmountEqualsZeroAfterWrite() {
        return new Object[][]{
                {5, 1.0, "SampleString"},
                {4, 1.0, "Test"},
                {1, 1.0E-16, "1"}, /* If the test fails with this data set,
                                      it means that with such values of the letter size and the number of characters in the word,
                                      the amount of ink will never decrease.
                                      It can be reproduced with different data sets: e.g. inkValue = 10, letterSize = 1.0E-17 etc. */
        };
    }

    @DataProvider
    public Object[][] WhitespacesInWord() {
        return new Object[][]{
                {5, " "},
                {1, " a"},
                {2, "a "},
        };
    }

    @DataProvider
    public Object[][] noInkNoLetterSize() {
        return new Object[][]{
                {0, 1.0, "Test"},
                {-1, 1.0, "Test"},
                {10, 0.0, "Test"},
                {10, -1.0, "Test"},
        };
    }

    @DataProvider
    public Object[][] inkLongerThanWordLength() {
        return new Object[][]{
                {5, 2, "Test"},
        };
    }


    @Test(dataProvider = "dataForValidCases")
    void WriteWordWithValidData(int ink, double size, String word) {

        Pen WWWVD = createObjectUsingTwoArgumentConstructor(ink, size);

        String actual = WWWVD.write(word);
        String expected;
        if (word.length() * size <= ink) {
            expected = word;
        } else expected = word.substring(0, ink);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "dependenceOfSubstringLengthOnLetterSize")
    void WriteSubstringWithDifferentLetterSize(int ink, double smallerSize, double biggerSize, String word) {

        Pen WSWDLS = createObjectUsingTwoArgumentConstructor(ink, smallerSize);
        String actualForSmallSize = WSWDLS.write(word);

        WSWDLS = createObjectUsingTwoArgumentConstructor(ink, biggerSize);
        String actualForBigSize = WSWDLS.write(word);

        assertNotEquals(actualForSmallSize, actualForBigSize);
    }

    @Test(dataProvider = "inkAmountEqualsZeroAfterWrite")
    void VerifyInkAmountEqualsZeroAfterWrite(int ink, double size, String word) throws NoSuchFieldException, IllegalAccessException {

        Pen IAEZAW = createObjectUsingTwoArgumentConstructor(ink, size);

        IAEZAW.write(word);
        int actualInkAmount = Integer.parseInt(getFieldValue(IAEZAW, "inkContainerValue"));
        assertEquals(actualInkAmount, 0);
    }

    @Parameters({"AmountOfInk"}) // specified in the testng.xml file
    @Test(expectedExceptions = NullPointerException.class)
    void CatchNullPointerException(int ink) {

        Pen CNPE = createObjectUsingOneArgumentConstructor(ink);
        CNPE.write(null);
    }

    @Test(dataProvider = "WhitespacesInWord")
    void DoNotWriteSpaceCharsWithPen(int ink, double size, String word) {

        Pen DNWSCWP = createObjectUsingTwoArgumentConstructor(ink, size);
        assertEquals(DNWSCWP.write(word), word.substring(0, ink).trim());
    }

    @Test(dataProvider = "noInkNoLetterSize")
    void WriteWordWithInvalidInkAmountAndLetterSize(int ink, double size, String word) {

        Pen WWWIIAALS = new Pen(ink, size);
        assertEquals(WWWIIAALS.write(word), "");
    }

    @Test(dataProvider = "inkLongerThanWordLength")
    void CatchStringIndexOutOfBoundsException(int ink, double size, String word) {

        Pen CSIOOBE = createObjectUsingTwoArgumentConstructor(ink, size);

        String actual = null;
        String expected;
        try {
            actual = CSIOOBE.write(word);
        } catch (StringIndexOutOfBoundsException e) {
            fail("StringIndexOutOfBoundsException exception occurs.");
        }
        if (word.length() * size <= ink) {
            expected = word;
        } else expected = word.substring(0, ink);
        assertEquals(actual, expected);
    }
}