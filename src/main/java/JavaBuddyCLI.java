
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Scanner;

public class JavaBuddyCLI {

    public static void main(String[] args) {
        String[] asciiArt = {
            "       __                     ____            __    __",
            "      / /___ __   ______ _   / __ )__  ______/ /___/ /_  __",
            " __  / / __ `/ | / / __ `/  / __  / / / / __  / __  / / / /",
            "/ /_/ / /_/ /| |/ / /_/ /  / /_/ / /_/ / /_/ / /_/ / /_/ /",
            "\\____/\\__,_/ |___/\\__,_/  /_____/\\__,_/\\__,_/\\__, /\\__, /",
            "                                                  /____/"
        };

        for (String line : asciiArt) {
            System.out.println(line);
            try {
                Thread.sleep(400); // 400 milliseconds delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("📘 Available Commands:");
        System.out.println("  - Type any glossary term or library name to learn about it.");
        System.out.println("  - Type 'exit' to quit the program.");
        System.out.println("  - Type 'help' for a list of all glossary terms or library names.");

        Gson gson = new Gson();
        try {// src/main/resources/glossary.json
            FileReader reader = new FileReader("src/main/resources/glossary.json");
            Type glossaryType = new TypeToken<Map<String, GlossaryEntry>>() {}.getType();
            Map<String, GlossaryEntry> glossary = gson.fromJson(reader, glossaryType);
            reader.close();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nEnter a term or library: ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }
                // Help command
                AtomicInteger tableIndex = new AtomicInteger(0);
                if (input.equalsIgnoreCase("help")) {
                    System.out.println("\nGlossary Entries:");
                    System.out.printf("%-30s | %-30s%n", "Terms", "Libraries");
                    System.out.println("--------------------------------------------------------------");

                    glossary.entrySet().stream()
                        .filter(e -> "term".equalsIgnoreCase(e.getValue().type))
                        .map(Map.Entry::getKey)
                        .sorted()
                        .forEachOrdered(term -> {
                            String lib = glossary.entrySet().stream()
                                .filter(e -> "library".equalsIgnoreCase(e.getValue().type))
                                .map(Map.Entry::getKey)
                                .sorted()
                                .skip(tableIndex.getAndIncrement())
                                .findFirst()
                                .orElse("");
                            System.out.printf("%-30s | %-30s%n", term, lib);
                        });

                    // Reset index for next help call
                    tableIndex.set(0);
                    continue; // ✅ Now only skips when "help" is typed
                }

                GlossaryEntry entry = glossary.get(input);
                if (entry != null) {
                    System.out.println("Definition: " + entry.definition);
                    System.out.println("Example: " + entry.example);
                    System.out.println("Code Example: " + entry.codeExample);
                    System.out.println("Type: " + entry.type);
                } else {
                    System.out.println("Sorry, I couldn't find that term or library.");
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading glossary: " + e.getMessage());
        }
    }

    static class GlossaryEntry {
        String definition;
        String example;
        @SerializedName("code example")
        String codeExample;
        String type;
    }
}