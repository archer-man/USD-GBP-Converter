import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;

public class SecondUseCaseTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));

    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testGetConversionRate() {
        DecimalFormat df = new DecimalFormat("#.####");
        var rate = Converter.getCurrencyRate();
        String trueString = "1 USD = " + df.format(rate).replace(".", ",") + " GBP\n";
        Converter.printConversionRate();
        assertEquals(trueString, outContent.toString());
    }

    @Test
    public void testConversionGbpToUsd() throws IOException {
        var rate = 1.0 / Converter.getCurrencyRate();
        var exp = rate * 25.0;
        var amount = Converter.convertToUSD(25);
        assertEquals(exp, amount, 0);
    }

    @Test
    public void wrongInputWarningMessage() {
        ByteArrayInputStream inContent = new ByteArrayInputStream(("dsad\n" + "4").getBytes());
        System.setIn(inContent);
        Converter.main(null);
        assertEquals("Improper command number. Type in number of command.", outContent.toString().split("\\n")[7].trim());
    }

}
