module com.example.psarchfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.logging;

    exports com.example.ps_arch_fx.controller;
    opens com.example.ps_arch_fx.controller to javafx.fxml;
    exports com.example.ps_arch_fx.launcher;
    opens com.example.ps_arch_fx.launcher to javafx.fxml;
    exports com.example.ps_arch_fx.simulation;
    exports com.example.ps_arch_fx.data;
    opens com.example.ps_arch_fx.data to javafx.fxml;
}