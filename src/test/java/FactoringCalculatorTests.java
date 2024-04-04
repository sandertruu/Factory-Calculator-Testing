import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.*;

public class FactoringCalculatorTests {

    /**
     * set browser to Chrome and window size
     */
    @BeforeAll
    public static void setUp(){
        Configuration.browser = "chrome";
        Configuration.browserSize="1920x1080";
    }

    /**
     * open the Factoring calculator, wait 1 second to make sure the cookie notification is shown
     * accept the cookies
     */
    @BeforeEach
    public void openBrowser(){
        open("https://www.swedbank.lt/business/finance/trade/factoring?language=ENG");
        sleep(1000);
        dismissCookieMessage();
    }


    /**
     * Test the calculator when it is first opened with the autofilled values.
     * In further tests the window is reopened always, and all calculations are done
     * using the autofilled values except the ones that are changed during testing
     */
    @Test
    public void testAutofilledCalculator(){

        Assertions.assertEquals("10000", getInvoiceAmountValue());
        Assertions.assertEquals("90", getAdvanceRateValue());
        Assertions.assertEquals("3", getInterestRateValue());
        Assertions.assertEquals("30", getPaymentTermValue());
        Assertions.assertEquals("0.3", getCommmissionFeeValue());

        clickCalculateButton();


        Assertions.assertEquals(0.53, getResultPercentage());
        Assertions.assertEquals(52.5, getResult());
    }

    /**
     * Test an accepting value in the Invoice Amount field of 24500
     */
    @Test
    public void testAcceptingInvoiceAmountValue(){
        setInvoiceAmountValue(24500);
        Assertions.assertEquals("24500.0", getInvoiceAmountValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    /**
     * Test the lower boundary value (Upper is missing) with the value 1 for the Invoice Amount field
     */
    @Test
    public void testLowerBoundaryInvoiceAmountValue(){
        setInvoiceAmountValue(1);
        Assertions.assertEquals("1.0", getInvoiceAmountValue());

        clickCalculateButton();

        Assertions.assertEquals(0.52, getResultPercentage());
        Assertions.assertEquals(0.01, getResult());
    }

    /**
     * Test negative value of -10000 in the Invoice amount field
     */
    @Test
    public void testNegativeInvoiceAmountValue(){
        setInvoiceAmountValue(-10000);
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Value must be greater than or equal 1.", message);
    }

    /**
     * Test value with one decimal point value of 10000.1 in Invoice Amount field
     */
    @Test
    public void testOneDecimalPointStepInvoiceAmountValue(){
        setInvoiceAmountValue(10000.1);
        Assertions.assertEquals("10000.1", getInvoiceAmountValue());
        Assertions.assertFalse(isErrorDisplayed());
    }
    /**
     * Test value with two decimal point value of 10000.01 in Invoice Amount field
     */
    @Test
    public void testTwoDecimalPointStepInvoiceAmountValue(){
        setInvoiceAmountValue(10000.01);
        Assertions.assertEquals("10000.01", getInvoiceAmountValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    /**
     * Test value with n>2 decimal point value of 10000.0001 in Invoice Amount field
     */
    @Test
    public void testMoreThanTwoDecimalPointsInvoiceAmountValue(){
        setInvoiceAmountValue(10000.0001);
        Assertions.assertEquals("10000.0001", getInvoiceAmountValue());
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Step should be 0.01, nearest values are 10000.00 and 10000.01.", message);
    }

    /**
     * Test lower boundary value 0 in Interest Rate field
     */
    @Test
    public void testLowerBoundaryInterestRateValue(){
        setInterestRateValue(0);
        Assertions.assertEquals("0.0", getInterestRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.3, getResultPercentage());
        Assertions.assertEquals(30.0, getResult());
    }
    /**
     * Test upper boundary value 20 in Interest Rate field
     */
    @Test
    public void testUpperBoundaryInterestRateValue(){
        setInterestRateValue(20);
        Assertions.assertEquals("20.0", getInterestRateValue());

        clickCalculateButton();

        Assertions.assertEquals(1.8, getResultPercentage());
        Assertions.assertEquals(180.0, getResult());
    }
    /**
     * Test negative value -1 in Interest Rate field
     */
    @Test
    public void testNegativeInterestRateValue(){
        setInterestRateValue(-1);
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Value must be greater than or equal 0.", message);
    }

    /**
     * Test out of outer boundary value 21 in Interest Rate field
     */
    @Test
    public void testGreaterThanUpperBoundaryInterestRateValue(){
        setInterestRateValue(21);
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Value must be less than or equal 20.", message);
    }

    /**
     * Test one decimal point value 3.1  in Interest Rate field
     */
    @Test
    public void testOneDecimalPointInterestRateValue(){
        setInterestRateValue(3.1);
        Assertions.assertEquals("3.1", getInterestRateValue());

        Assertions.assertFalse(isErrorDisplayed());
    }
    /**
     * Test two decimal point value 3.01  in Interest Rate field
     */
    @Test
    public void testTwoDecimalPointInterestRateValue(){
        setInterestRateValue(3.01);
        Assertions.assertEquals("3.01", getInterestRateValue());

        Assertions.assertFalse(isErrorDisplayed());
    }
    /**
     * Test n>2 decimal point value 3.111  in Interest Rate field
     */
    @Test
    public void testMoreThanTwoDecimalPointInterestRateValue(){
        setInterestRateValue(3.111);
        Assertions.assertEquals("3.111", getInterestRateValue());

        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();

        Assertions.assertEquals("Step should be 0.01, nearest values are 3.11 and 3.12.", message);
    }
    /**
     * Test 90 in Advance Rate field
     */
    @Test
    public void test90AdvanceRateValue(){
        setAdvanceRateValue(90);
        Assertions.assertEquals("90", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.53, getResultPercentage());
        Assertions.assertEquals(52.5, getResult());
    }
    /**
     * Test 85 in Advance Rate field
     */
    @Test
    public void test85AdvanceRateValue(){
        setAdvanceRateValue(85);
        Assertions.assertEquals("85", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.51, getResultPercentage());
        Assertions.assertEquals(51.25, getResult());
    }
    /**
     * Test 80 in Advance Rate field
     */
    @Test
    public void test80AdvanceRateValue(){
        setAdvanceRateValue(80);
        Assertions.assertEquals("80", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.50, getResultPercentage());
        Assertions.assertEquals(50.0, getResult());
    }
    /**
     * Test 75 in Advance Rate field
     */
    @Test
    public void test75AdvanceRateValue(){
        setAdvanceRateValue(75);
        Assertions.assertEquals("75", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.49, getResultPercentage());
        Assertions.assertEquals(48.75, getResult());
    }
    /**
     * Test 100 in Advance Rate value (Field stays 90, but value used in calculation is 100)
     * Should produce an error, because if someone manages to do it, they get false results from the calculation
     */
    @Test
    public void testNonOpitonalAdvanceRateValue(){
        setAdvanceRateValue(100);
        Assertions.assertEquals("100.0", getAdvanceRateValue());
        Assertions.assertTrue(isErrorDisplayed());
    }
    /**
     * Test 30 in Payment Term field
     */
    @Test
    public void test30PaymentTermValue(){
        setPaymentTermValue(30);
        Assertions.assertEquals("30", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(0.53, getResultPercentage());
        Assertions.assertEquals(52.5, getResult());
    }
    /**
     * Test 60 in Payment Term field
     */
    @Test
    public void test60PaymentTermValue(){
        setPaymentTermValue(60);
        Assertions.assertEquals("60", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(0.75, getResultPercentage());
        Assertions.assertEquals(75.0, getResult());
    }
    /**
     * Test 90 in Payment Term field
     */
    @Test
    public void test90PaymentTermValue(){
        setPaymentTermValue(90);
        Assertions.assertEquals("90", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(0.97, getResultPercentage());
        Assertions.assertEquals(97.5, getResult());
    }
    /**
     * Test 120 in Payment Term field
     */
    @Test
    public void test120PaymentTermValue(){
        setPaymentTermValue(120);
        Assertions.assertEquals("120", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(1.2, getResultPercentage());
        Assertions.assertEquals(120.0, getResult());
    }
    /**
     * Test 70 in Payment Term value (value used in calculation is 70, field is 60).
     * Should produce an error, otherwise calculates with value 70 and gives false results to the user
     */
    @Test
    public void testNonOptionalPaymentTermValue(){
        setPaymentTermValue(70);
        Assertions.assertEquals("70", getPaymentTermValue());
        Assertions.assertTrue(isErrorDisplayed());
    }

    /**
     * Test accepting value in Commission Fee field
     */
    @Test
    public void testAcceptingCommissionFeeValue(){
        setCommissionFeeValue(1);
        Assertions.assertEquals("1.0", getCommmissionFeeValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    /**
     * Test negative commission fee value
     * From my logic, it should not be possible, because there is a very very low chance
     * The bank would want to pay more (maybe when knowing they will get more benefits from the payment in the end)
     */
    @Test
    public void testNegativeCommissionFeeValue(){
        setCommissionFeeValue(-1);
        Assertions.assertEquals("-1.0", getCommmissionFeeValue());
        Assertions.assertTrue(isErrorDisplayed());

        String message = getErrorMessage();
        Assertions.assertEquals("Value must be greater than or equal 0.", message);
    }

    /**
     * According to my last test logic, I set 0 as the lower boundary
     */
    @Test
    public void testLowerBoundaryCommissionFeeValue(){
        setCommissionFeeValue(0);
        Assertions.assertEquals("0.0", getCommmissionFeeValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    /**
     * And 100 as the upper boundary, not likely to pay more than 100% of the invoice as Commission fees
     */
    @Test
    public void testUpperBoundaryCommissionFeeValue(){
        setCommissionFeeValue(100);
        Assertions.assertEquals("100.0", getCommmissionFeeValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testOneDecimalPointCommissionFeeValue(){
        setCommissionFeeValue(0.3);
        Assertions.assertEquals("0.3", getCommmissionFeeValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testTwoDecimalPointCommissionFeeValue(){
        setCommissionFeeValue(0.33);
        Assertions.assertEquals("0.33", getCommmissionFeeValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testMoreThanTwoDecimalPointCommissionFeeValue(){
        setCommissionFeeValue(0.6784);
        Assertions.assertEquals("0.6784", getCommmissionFeeValue());
        Assertions.assertTrue(isErrorDisplayed());

        String message = getErrorMessage();
        Assertions.assertEquals("Step should be 0.01, nearest values are 0.67 and 0.68.", message);
    }

    //Test for empty fields

    @Test
    public void testInvoiceAmountEmpty(){
        String inputId = "D5";
        $("#" + inputId).setValue("");

        Assertions.assertEquals("", getInvoiceAmountValue());
        Assertions.assertTrue(isErrorDisplayed());

        String message = getErrorMessage();

        Assertions.assertEquals("Please fill out this field.", message);

    }

    @Test
    public void testIntrestRateEmpty(){
        String inputId = "D7";
        $("#" + inputId).setValue("");

        Assertions.assertEquals("", getInterestRateValue());
        Assertions.assertTrue(isErrorDisplayed());

        String message = getErrorMessage();

        Assertions.assertEquals("Please fill out this field.", message);

    }

    @Test
    public void testCommissionFeeEmpty(){
        String inputId = "D9";
        $("#" + inputId).setValue("");

        Assertions.assertEquals("", getCommmissionFeeValue());
        Assertions.assertTrue(isErrorDisplayed());

        String message = getErrorMessage();

        Assertions.assertEquals("Please fill out this field.", message);

    }



    @AfterEach
    public void close(){
        Selenide.closeWindow();
    }
    @AfterAll
    public static void tearDown(){
        Selenide.closeWebDriver();
    }
    public String getInvoiceAmountValue(){
        String inputId = "D5";
        return $("#" + inputId).shouldBe(Condition.visible).getValue();
    }
    public String getAdvanceRateValue() {
        String id = "D6";
        return $(String.format("#%s", id)).getSelectedOptionValue();
    }
    public String getInterestRateValue(){
        String inputId = "D7";
        return $("#" + inputId).shouldBe(Condition.visible).getValue();
    }
    public String getPaymentTermValue() {
        String id = "D8";
        return $(String.format("#%s", id)).getSelectedOptionValue();
    }
    public String getCommmissionFeeValue(){
        String inputId = "D9";
        return $("#" + inputId).shouldBe(Condition.visible).getValue();
    }




    public void setInvoiceAmountValue(double value){
        String inputId = "D5";
        $("#" + inputId).setValue(String.valueOf(value));
    }
    public void setAdvanceRateValue(int value){
        String id = "D6";
        $(String.format("#%s", id)).selectOptionByValue(String.valueOf(value));
    }
    public void setInterestRateValue(double value){
        String inputId = "D7";
        $("#" + inputId).setValue(String.valueOf(value));
    }
    public void setPaymentTermValue(int value){
        String id = "D8";
        $(String.format("#%s", id)).selectOptionByValue(String.valueOf(value));
    }
    public void setCommissionFeeValue(double value){
        String inputId = "D9";
        $("#" + inputId).setValue(String.valueOf(value));
    }

    public double getResultPercentage(){
        return Double.parseDouble($("#result_perc").getText());
    }

    public double getResult(){
        return Double.parseDouble($("#result").getText());
    }

    public void clickCalculateButton(){
        $("#calculate-factoring").click();
    }

    public void dismissCookieMessage() {
        // Check if the cookie message is present
        if ($(".ui-cookie-consent__accept-button").isDisplayed()) {
            // If present, locate the "Accept" button and click it to dismiss the message
            $(".ui-cookie-consent__accept-button").click();
        }

    }

    public boolean isErrorDisplayed(){
        return $("ui-hint[type='error']").isDisplayed();
    }

    public String getErrorMessage(){
        return $("ui-hint[type='error']").getText();
    }
}
