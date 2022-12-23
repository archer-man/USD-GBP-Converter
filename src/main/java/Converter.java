import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Converter {

    public static void main(String[] args) {
        System.out.print("Welcome to USD-GBP converter\n");
        System.out.println("\nType in the corresponding number of command in order to select it:\n"
                + "1 - Show the current conversion rate\n"
                + "2 - Convert USD to GBP\n"
                + "3 - Exit program");
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            var done = false;
            do {
                var cmd = reader.readLine();
                if (cmd.equals("1")) {
                    printConversionRate();
                } else if (cmd.equals("2")) {
                    System.out.println("Type in USD amount");
                    convertToGBP(Double.parseDouble(reader.readLine()));
                } else if (cmd.equals("3")) {
                    done = true;
                } else {
                    System.out.println("Improper command");
                }
            } while (!done);
        } catch (IOException e) {
            throw new RuntimeException("#INTERNAL", e);
        }

    }

    public static void printConversionRate() {
        System.out.printf("1 USD = %.4f GBP\n", getCurrencyRate());
    }

    public static double getCurrencyRate() {
        Double rate = null;
        try {
            Document doc = Jsoup.connect("https://www.exchangerates.org.uk/Dollars-to-Pounds-currency-conversion-page.html").get();
            rate = Double.valueOf(doc.getElementById("shd2b;").text());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rate;
    }

    public static void convertToGBP(double usdValue) throws IOException {
        var rate = getCurrencyRate();
        var gbpAmount = usdValue * rate;
        System.out.printf("Rate is %.4f GBP\n"
                + "Total amount: %f USD * %.4f GBP = %f GBP\n", rate, usdValue, rate, gbpAmount);
    }
}

