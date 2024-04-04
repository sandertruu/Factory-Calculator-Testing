import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.*;

public class FactoringCalculatorTests {
    
    @BeforeAll
    public static void setUp(){
        Configuration.browser = "chrome";
        Configuration.browserSize="1920x1080";
    }

    @BeforeEach
    public void openBrowser(){
        open("https://www.swedbank.lt/business/finance/trade/factoring?language=ENG");
        sleep(1000);
        dismissCookieMessage();
    }


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

    @Test
    public void testAcceptingInvoiceAmountValue(){
        setInvoiceAmountValue(24500);
        Assertions.assertEquals("24500.0", getInvoiceAmountValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testLowerBoundaryInvoiceAmountValue(){
        setInvoiceAmountValue(1);
        Assertions.assertEquals("1.0", getInvoiceAmountValue());

        clickCalculateButton();

        Assertions.assertEquals(0.52, getResultPercentage());
        Assertions.assertEquals(0.01, getResult());
    }

    @Test
    public void testNegativeInvoiceAmountValue(){
        setInvoiceAmountValue(-10000);
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Value must be greater than or equal 1.", message);
    }

    @Test
    public void testOneDecimalPointStepInvoiceAmountValue(){
        setInvoiceAmountValue(10000.1);
        Assertions.assertEquals("10000.1", getInvoiceAmountValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testTwoDecimalPointStepInvoiceAmountValue(){
        setInvoiceAmountValue(10000.01);
        Assertions.assertEquals("10000.01", getInvoiceAmountValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testMoreThanTwoDecimalPointsInvoiceAmountValue(){
        setInvoiceAmountValue(10000.0001);
        Assertions.assertEquals("10000.0001", getInvoiceAmountValue());
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Step should be 0.01, nearest values are 10000.00 and 10000.01.", message);
    }

    @Test
    public void testLowerBoundaryInterestRateValue(){
        setInterestRateValue(0);
        Assertions.assertEquals("0.0", getInterestRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.3, getResultPercentage());
        Assertions.assertEquals(30.0, getResult());
    }

    @Test
    public void testUpperBoundaryInterestRateValue(){
        setInterestRateValue(20);
        Assertions.assertEquals("20.0", getInterestRateValue());

        clickCalculateButton();

        Assertions.assertEquals(1.8, getResultPercentage());
        Assertions.assertEquals(180.0, getResult());
    }

    @Test
    public void testNegativeInterestRateValue(){
        setInterestRateValue(-1);
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Value must be greater than or equal 0.", message);
    }

    @Test
    public void testGreaterThanUpperBoundaryInterestRateValue(){
        setInterestRateValue(21);
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Value must be less than or equal 20.", message);
    }

    @Test
    public void testOneDecimalPointInterestRateValue(){
        setInterestRateValue(3.1);
        Assertions.assertEquals("3.1", getInterestRateValue());

        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testTwoDecimalPointInterestRateValue(){
        setInterestRateValue(3.01);
        Assertions.assertEquals("3.01", getInterestRateValue());

        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test
    public void testMoreThanTwoDecimalPointInterestRateValue(){
        setInterestRateValue(3.111);
        Assertions.assertEquals("3.111", getInterestRateValue());

        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();

        Assertions.assertEquals("Step should be 0.01, nearest values are 3.11 and 3.12.", message);
    }

    @Test
    public void test90AdvanceRateValue(){
        setAdvanceRateValue(90);
        Assertions.assertEquals("90", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.53, getResultPercentage());
        Assertions.assertEquals(52.5, getResult());
    }

    @Test
    public void test85AdvanceRateValue(){
        setAdvanceRateValue(85);
        Assertions.assertEquals("85", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.51, getResultPercentage());
        Assertions.assertEquals(51.25, getResult());
    }

    @Test
    public void test80AdvanceRateValue(){
        setAdvanceRateValue(80);
        Assertions.assertEquals("80", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.50, getResultPercentage());
        Assertions.assertEquals(50.0, getResult());
    }

    @Test
    public void test75AdvanceRateValue(){
        setAdvanceRateValue(75);
        Assertions.assertEquals("75", getAdvanceRateValue());

        clickCalculateButton();

        Assertions.assertEquals(0.49, getResultPercentage());
        Assertions.assertEquals(48.75, getResult());
    }

    @Test
    public void testNonOpitonalAdvanceRateValue(){
        setAdvanceRateValue(100);
        Assertions.assertEquals("100.0", getAdvanceRateValue());
        Assertions.assertTrue(isErrorDisplayed());
    }

    @Test
    public void test30PaymentTermValue(){
        setPaymentTermValue(30);
        Assertions.assertEquals("30", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(0.53, getResultPercentage());
        Assertions.assertEquals(52.5, getResult());
    }

    @Test
    public void test60PaymentTermValue(){
        setPaymentTermValue(60);
        Assertions.assertEquals("60", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(0.75, getResultPercentage());
        Assertions.assertEquals(75.0, getResult());
    }

    @Test
    public void test90PaymentTermValue(){
        setPaymentTermValue(90);
        Assertions.assertEquals("90", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(0.97, getResultPercentage());
        Assertions.assertEquals(97.5, getResult());
    }

    @Test
    public void test120PaymentTermValue(){
        setPaymentTermValue(120);
        Assertions.assertEquals("120", getPaymentTermValue());

        clickCalculateButton();

        Assertions.assertEquals(1.2, getResultPercentage());
        Assertions.assertEquals(120.0, getResult());
    }

    @Test
    public void testNonOptionalPaymentTermValue(){
        setPaymentTermValue(70);
        Assertions.assertEquals("70", getPaymentTermValue());
        Assertions.assertTrue(isErrorDisplayed());
    }

    @Test
    public void testAcceptingCommissionFeeValue(){
        setCommissionFeeValue(1);
        Assertions.assertEquals("1.0", getCommmissionFeeValue());
        Assertions.assertFalse(isErrorDisplayed());
    }

    @Test



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
