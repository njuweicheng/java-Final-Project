package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import myworld.battlefield.BattleField;

public class Main extends Application {
    private BattleField battleField=new BattleField();
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        controller=fxmlLoader.getController();
        controller.initialize(battleField);

//        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene scene=new Scene(root, 1000, 600);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                  @Override
                                  public void handle(KeyEvent event) {
                                      if(event.getCode()== KeyCode.SPACE){
                                          controller.start();
                                      }
                                  }
                              }
        );
        primaryStage.setTitle("Calabash Brothers");
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        primaryStage.setTitle("Hello World!");
        Group root = new Group();
//        Image image = new Image("image/red.jpg");


//        GridPane gridPane = new GridPane();
//        gridPane.add(createImageView(image), 0,0);

//        gridPane.add(createImageView(image), 1,0);
//        gridPane.add(createImageView(image), 0,1);
//        gridPane.add(createImageView(image), 1,1);
//        root.getChildren().add(gridPane);

        AnchorPane anchorPane=new AnchorPane();
        ImageView view=new ImageView(new Image("image/red.jpg"));
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        view.setEffect(colorAdjust);
        anchorPane.getChildren().add(view);
//        anchorPane.getChildren().add(createImageView(image));
        root.getChildren().add(anchorPane);

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        */
    }

    /*
    private ImageView createImageView(Image image) {

        ImageView imageView = new ImageView(image);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);

        imageView.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            imageView.setEffect(colorAdjust);
        });
        imageView.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            imageView.setEffect(null);
        });

        return imageView;
    }
    */

    public static void main(String[] args) {
        launch(args);
    }
}
