package com.example.ps_arch_fx.launcher;

import com.example.ps_arch_fx.data.SharedDataHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        SharedDataHolder dataHolder = new SharedDataHolder();

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> {
            try {
                Constructor<?>[] constructors = controllerClass.getConstructors();

                for (Constructor<?> constructor : constructors) {
                    if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0] == SharedDataHolder.class) {
                        return constructor.newInstance(dataHolder);
                    }
                }

                // no suitable constructor found, just use default:
                return controllerClass.getDeclaredConstructor().newInstance();
            }
            catch (Exception exc) {
                System.out.println("Could not create controller:");
                exc.printStackTrace();
                return null;
            }
        });

        fxmlLoader.setLocation(Objects.requireNonNull(
                App.class.getResource("/com/example/ps_arch_fx/view/main-view.fxml")));

        Scene scene = new Scene(fxmlLoader.load(), 1300, 800);

        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/ps_arch_fx/style/global.css")).toExternalForm());

        stage.getIcons().add(new Image(Objects.requireNonNull(
                App.class.getResourceAsStream("/com/example/ps_arch_fx/icon/favicon.png"))));

        stage.setTitle("CMO");
        stage.setScene(scene);
        stage.show();
    }
}