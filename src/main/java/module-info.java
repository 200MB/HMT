module com.MB.hmt {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.MB.hmt to javafx.fxml;
    exports com.MB.hmt;
}