
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JavaBuddy extends Application {

    private Map<String, Map<String, String>> glossary;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        glossary = loadGlossaryFromJson("glossary.json");

        Label titleLabel = new Label("JavaBuddy - Learn Java Terms");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter a Java term...");
        Button searchButton = new Button("Search");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);

        searchButton.setOnAction(e -> {
            String term = searchField.getText().trim().toLowerCase();
            if (glossary.containsKey(term)) {
                Map<String, String> entry = glossary.get(term);
                String definition = entry.get("definition");
                String example = entry.get("example");
                resultArea.setText("Definition: " + definition + "\n\nExample:\n" + example);
            } else {
                resultArea.setText("Term not found in glossary.");
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(titleLabel, searchField, searchButton, resultArea);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("JavaBuddy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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
}
