package com.giahuy.createfile;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class IOController extends Component {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private TextField txt_File;

    @FXML
    private TextField txt_Folder;
    @FXML
    private TextField txt_dlt;
    @FXML
    private TextField txt_NewFile;

    @FXML
    private TextField txt_size;
    @FXML
    private TextArea txt_Files;

    @FXML
    private void openFolder(){
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        DirectoryChooser dc = new DirectoryChooser();
        File file = dc.showDialog(stage);
        if(file != null){
            String path = file.getAbsolutePath();
            txt_Folder.setText(path);
            txt_Files.setText(listAllFiles(path, 0));
        }
    }
    private String listAllFiles(String path, int level){
        File myFile = new File(path);
        if (!myFile.exists()) {return "" ;}
        String result = "";
        for (int i =0; i < level; i++){
            result+="\t";
        }
        result = result + ((level == 0 ?"":"|_") + myFile.getName() + "\n");
        if (myFile.isFile()) return myFile.getName();

        for(File f : myFile.listFiles()){
            result += listAllFiles(f.getAbsolutePath(), level+1);
        }
        return result;
    }
    public void createFile(){
        String filepath = txt_Folder.getText() + "/" + txt_NewFile.getText();
        File myFile = new File(filepath);
        // kiểm tra file có tồn tại
        if (myFile.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("file tồn tại ");
            alert.showAndWait();

        }else if (txt_NewFile.getText().trim().length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông Báo");
            alert.setHeaderText(null);
            alert.setContentText("vui lòng nhập tên file ! ");
            alert.showAndWait();

        }else{
            try {
                myFile.createNewFile();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông Báo");
                alert.setHeaderText(null);
                alert.setContentText("Tạo file thành công ! ");
                alert.showAndWait();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Không thể tạo file ! ");
                alert.showAndWait();
            }
        }
        txt_NewFile.clear();
        txt_Folder.clear();
    }
    public void openFile(){
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file1 = fileChooser.showOpenDialog(stage);
        if(file1 != null){
            String path = file1.getAbsolutePath();
            txt_File.setText(path);
        }
        try{
            String path = file1.getAbsolutePath();
            long size = Files.size(Path.of(path));
            txt_size.setText(size +" KB");
        }catch (Exception e){
            txt_size.setText("Error: " + e.getMessage());
        }
    }
    public void deletebtn(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure delete Dialog ?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                String path = txt_File.getText();
                deleteFile(path);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Xóa file thành công!");
            } else{
                //(buttonType == buttonTypeNo)
            }
            txt_File.clear();
        });
    }
    private void deleteFile(String path){
        try{

            File myFile = new File(path);
            // Nếu là thư mục xóa tập tin con trước
            if(myFile.isDirectory()){
                for(File f : myFile.listFiles()){
                    deleteFile(f.getAbsolutePath());
                }
            }
            myFile.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}