module com.giahuy.createfile {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.giahuy.createfile to javafx.fxml;
    exports com.giahuy.createfile;
}