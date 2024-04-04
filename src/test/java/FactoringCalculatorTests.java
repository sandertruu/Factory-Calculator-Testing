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

        Assertions.assertNotEquals("", getInvoiceAmountValue());
        Assertions.assertNotEquals("", getAdvanceRateValue());
        Assertions.assertNotEquals("", getInterestRateValue());
        Assertions.assertNotEquals("", getPaymentTermValue());
        Assertions.assertNotEquals("", getCommmissionFeeValue());

        clickCalculateButton();


        Assertions.assertEquals(0.53, getResultPercentage());
        Assertions.assertEquals(52.5, getResult());
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
    public void testNegativeInterestRateValue(){
        setInterestRateValue(-1);
        Assertions.assertTrue(isErrorDisplayed());
        String message = getErrorMessage();
        Assertions.assertEquals("Value must be greater than or equal 0.", message);
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
