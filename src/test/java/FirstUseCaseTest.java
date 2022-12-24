import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.junit.Assert.*;

public class FirstUseCaseTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testGetConversionRate() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("#.####", symbols);
        var rate = Converter.getCurrencyRate();
        String trueString = "1 USD = " + df.format(rate) + " GBP\n";
        Converter.printConversionRate();
        assertEquals(trueString, outContent.toString());
    }

    @Test
    public void testConversionUsdToGbp() throws IOException {
        var rate = Converter.getCurrencyRate();
        var exp = rate * 10.0;
        var amount = Converter.convertToGBP(10);
        assertEquals(exp, amount, 0);
    }

}
