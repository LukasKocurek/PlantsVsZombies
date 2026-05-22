module com.example.plantsvszombies {
    requires javafx.controls;
    requires javafx.fxml;


    opens sk.fri.uniza.pvz to javafx.fxml;
    exports sk.fri.uniza.pvz;
}