package com.example.transferapp;

import com.example.transferapp.soulmark.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Arrays;

public class HelloController {
    @FXML
    private Label heartBeat;
    @FXML
    private Label addFile;


    @FXML
    protected void heartBeat() throws IOException {
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        byte[] response = client.heartBeat();
        heartBeat.setText("Recieved!");
        client.stopConnection();
        //welcomeText.setText(Arrays.toString(response));
        //welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void addFile() throws IOException {
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        byte[] response = client.addFile();
        heartBeat.setText("Added!");
        client.stopConnection();
    }

    @FXML
    protected void deleteFile() throws IOException {
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        byte[] response = client.deleteFile();
        heartBeat.setText("Deleted!");
        client.stopConnection();
    }

    @FXML
    protected void startPrint() throws IOException {
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        byte[] response = client.startPrint();
        heartBeat.setText("Print started! or not, I need to fix it a little");
        client.stopConnection();
    }

    @FXML
    protected void pausePrint() throws IOException {
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        byte[] response = client.pausePrint();
        heartBeat.setText("Print paused! or not, I need to fix it a little");
        client.stopConnection();
    }

    @FXML
    protected void resumePrint() throws IOException {
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        byte[] response = client.resumePrint();
        heartBeat.setText("Print resumed! or not, I need to fix it a little");
        client.stopConnection();
    }

    @FXML
    protected void stopPrint() throws IOException {
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        byte[] response = client.stopPrint();
        heartBeat.setText("Print stopped! or not, I need to fix it a little");
        client.stopConnection();
    }
    @FXML
    protected void sendRecordsFromCSV() throws IOException{
        Client client = new Client();
        client.startConnection("192.168.1.217", 8001);
        client.sendRecordFromCSV("C:\\Users\\User\\Documents\\transferApp\\transferApp\\node.csv");
        heartBeat.setText("что-то должно произойти");
        client.stopConnection();
    }
}