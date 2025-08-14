
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JavaBuddy extends Application {

private Map<String, Map<String, String>> loadGlossaryFromJson(String filename) {
    try {
      Gson gson = new Gson();
      FileReader reader = new FileReader(filename);
      return gson.fromJson(reader, new TypeToken<Map<String, Map<String, String>>>(){}.getType());
    } catch (Exception e) {
      System.out.println("Error loading glossary: " + e.getMessage());
      return new HashMap<>();
    }
}

  private Map<String, String> glossary;

  @Override
  public void start(Stage primaryStage) {
      // Initialize glossary
      glossary = new HashMap<>();
      glossary.put("class", "Definition: A class is a blueprint for creating objects. \\nExample: \\npublic class Dog {String breed;}");
      glossary.put("interface", "Definition: An interface defines a contract that classes can implement. \\nExample: \\ninterface Animal {void makeSound();}");
      glossary.put("inheritance", "Definition: Inheritance allows a class to inherit properties and methods from another class. \\nExample: \\nclass Dog extends Animal {}");
      glossary.put("object", "Definition: An object is an instance of a class.\\nExample:\\nDog myDog = new Dog();");
      glossary.put("method", "Definition: A method is a block of code that performs a specific task.\\nExample:\\nvoid bark() {\\n  System.out.println(\\\"Woof!\\\");\\n}");
      glossary.put("constructor", "Definition: A constructor initializes a new object.\\nExample:\\npublic Dog(String breed) {\\n  this.breed = breed;\\n}");
      glossary.put("polymorphism", "Definition: Polymorphism allows objects to be treated as instances of their parent class.\\nExample:\\nAnimal a = new Dog();\\na.makeSound();");
      glossary.put("encapsulation", "Definition: Encapsulation hides internal state and requires all interaction to be performed through methods.\\nExample:\\nprivate int age;\\npublic void setAge(int age) {\\n  this.age = age;\\n}");
      glossary.put("abstraction", "Definition: Abstraction hides complex implementation details and shows only essential features.\\nExample:\\nabstract class Animal {\\n  abstract void makeSound();\\n}");


      // UI Elements
      TextField searchField = new TextField();
      searchField.setPromptText("Enter Java term...");
      Button searchButton = new Button("Search");
      TextArea resultArea = new TextArea();
      resultArea.setWrapText(true);
      resultArea.setEditable(false);

      // Search button action
      searchButton.setOnAction(e -> {
          String term = searchField.getText().trim().toLowerCase();
          String result = glossary.getOrDefault(term, "Term not found. Try 'class', 'interface', or 'inheritance'.");
          resultArea.setText(result);
      });

      // Layout
      VBox layout = new VBox(10);
      layout.setPadding(new Insets(10));
      layout.getChildren().addAll(searchField, searchButton, resultArea);

      // Scene and Stage
      Scene scene = new Scene(layout, 400, 300);
      primaryStage.setTitle("JavaBuddy");
      primaryStage.setScene(scene);
      primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
