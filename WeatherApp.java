import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_KEY = "f47667a6aac6467596b214543240907";
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";
    private static final Map<String, String> cache = new HashMap<>();

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

    public static void displayWeather(String data) {
        String temperature = data.split("temp_c\":")[1].split(",")[0];
        String humidity = data.split("humidity\":")[1].split(",")[0];
        String windSpeed = data.split("wind_kph\":")[1].split(",")[0];
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Wind Speed: " + windSpeed + " kph");
    }

    public static void main(String[] args) {
        System.out.print("Enter city names separated by commas: ");
        Scanner scanner = new Scanner(System.in);
        String[] cities = scanner.nextLine().split(",");
        for (String city : cities) {
            city = city.trim();
            String weatherData = cache.get(city);
            if (weatherData == null) {
                weatherData = getWeather(city);
                if (weatherData != null) {
                    cache.put(city, weatherData);
                }
            }
            if (weatherData != null) {
                System.out.println("Weather in " + city + ":");
                displayWeather(weatherData);
                System.out.println();
            }
        }
        scanner.close();
    }
}
