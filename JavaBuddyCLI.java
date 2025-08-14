
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JavaBuddyCLI {

    private static Map<String, Map<String, String>> glossary;

    public static void main(String[] args) {
        loadGlossary();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to JavaBuddy CLI! Type a Java term to learn about it, or type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter a Java term: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (glossary.containsKey(input)) {
                Map<String, String> entry = glossary.get(input);
                System.out.println("Definition: " + entry.get("definition"));
                System.out.println("Example: " + entry.get("example"));
            } else {
                System.out.println("Sorry, that term was not found in the glossary.");
            }
        }

        scanner.close();
    }

    private static void loadGlossary() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("src/main/resources/glossary.json");
            glossary = gson.fromJson(reader, new TypeToken<Map<String, Map<String, String>>>(){}.getType());
            reader.close();
        } catch (IOException e) {
            System.out.println("Failed to load glossary: " + e.getMessage());
            glossary = Map.of();
        }
    }
}
