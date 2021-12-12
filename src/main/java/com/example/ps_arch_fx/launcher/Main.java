package com.example.ps_arch_fx.launcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static Logger log;

    static {
        try (InputStream ins = Main.class.getResourceAsStream("/com/example/ps_arch_fx/logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            log = Logger.getLogger("");
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App.main(args);
    }
}