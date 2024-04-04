import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FactoringCalculatorTests {
    
    @BeforeAll
    public static void setUp(){
        Configuration.browser = "chrome";
        Configuration.browserSize="1920x1080";
    }

    @BeforeEach
    public void openBrowser(){
        open("https://www.swedbank.lt/business/finance/trade/factoring?language=ENG");
        dismissCookieMessage();
    }


    @Test
    public void testAutofilledCalculator(){
        String invoiceAmountValue = getInvoiceAmountValue();
        String advanceRateValue = getAdvanceRateValue();
        String interestRateValue = getInterestRateValue();
        String paymentTermValue = getPaymentTermValue();
        String commmissionFeeValue = getCommmissionFeeValue();

        Assertions.assertNotEquals("", invoiceAmountValue);
        Assertions.assertNotEquals("", advanceRateValue);
        Assertions.assertNotEquals("", interestRateValue);
        Assertions.assertNotEquals("", paymentTermValue);
        Assertions.assertNotEquals("", commmissionFeeValue);

        clickCalculateButton();

        double resultPercentage = getResultPercentage();
        double result = getResult();

        Assertions.assertEquals(0.53, resultPercentage);
        Assertions.assertEquals(52.5, result);
    }


    @AfterEach
    public void tearDown(){
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
}
