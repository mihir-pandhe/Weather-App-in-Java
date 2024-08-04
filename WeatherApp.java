import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_KEY = "f47667a6aac6467596b214543240907";
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";

    public static String getWeather(String city) {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(BASE_URL + "?key=" + API_KEY + "&q=" + city);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.print("Enter city name: ");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.nextLine();
        String weatherData = getWeather(city);
        System.out.println(weatherData);
        scanner.close();
    }
}
