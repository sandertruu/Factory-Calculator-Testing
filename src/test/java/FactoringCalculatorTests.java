import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }


    @Test
    public void testTest(){
        System.out.println("Working");
    }


    @AfterEach
    public void tearDown(){
        Selenide.closeWebDriver();
    }
}
