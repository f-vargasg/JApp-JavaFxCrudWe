
module com.fvgprinc.app.crudfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.apache.commons.configuration2;
    requires commons.dbcp2;
    requires java.sql;    
    requires com.fvgprinc.tools.db;
    requires com.fvgprinc.tools.string;

    opens com.fvgprinc.app.crudfx to javafx.fxml;
    exports com.fvgprinc.app.crudfx;
    exports com.fvgprinc.app.crudfx.be;
    exports com.fvgprinc.app.crudfx.bl;
    exports com.fvgprinc.app.crudfx.dl;
}
