import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class WeatherApp {
    private static final String API_KEY = "f47667a6aac6467596b214543240907";
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";
    private static final Map<String, String> cache = new HashMap<>();
    private static final Set<String> favoriteCities = new HashSet<>();

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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command (weather, add_favorite, show_favorites, quit): ");
            String command = scanner.nextLine();
            if (command.equals("weather")) {
                System.out.print("Enter city names separated by commas: ");
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
            } else if (command.equals("add_favorite")) {
                System.out.print("Enter city name to add to favorites: ");
                String city = scanner.nextLine().trim();
                favoriteCities.add(city);
                System.out.println(city + " added to favorites.");
            } else if (command.equals("show_favorites")) {
                System.out.println("Favorite Cities:");
                for (String city : favoriteCities) {
                    System.out.println(city);
                }
            } else if (command.equals("quit")) {
                break;
            }
        }
        scanner.close();
    }
}
