package com.example.transferapp.soulmark;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }

    public byte[] addFile() throws IOException{
        Command command = new Command(0x00000044);
        byte[] addFile=command.addFile("C:\\Users\\User\\Desktop\\test1.VDF");
        out.write(addFile);
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public byte[] deleteFile() throws IOException{
        Command command = new Command(0x00000044);
        byte[] addFile=command.deleteFile("C:\\Users\\User\\Desktop\\test1.VDF");
        out.write(addFile);
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public byte[] heartBeat() throws IOException{
        Command command = new Command(0x00000000);
        out.write(command.getBytes());
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public byte[] startPrint() throws IOException{
        Command command = new Command(0x00000030);
        out.write(command.getBytes());
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public byte[] pausePrint() throws IOException{
        Command command = new Command(0x00000046);
        out.write(command.getBytes());
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public byte[] resumePrint() throws IOException{
        Command command = new Command(0x00000048);
        out.write(command.getBytes());
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public byte[] stopPrint() throws IOException{
        Command command = new Command(0x00000004);
        out.write(command.getBytes());
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public void stopConnection() throws IOException{
        in.close();
        out.close();
        clientSocket.close();
    }

    public byte[] sendFileToPrint() throws IOException {
        Command command = new Command(0x00000006);
        out.write(command.sendFileToPrint("C:\\Users\\User\\Desktop\\test1.VDF"));
        byte[] resp = in.readNBytes(24);
        return resp;
    }

    public byte[] setFieldOrder() throws IOException{
        Command command = new Command(0x414D4943);
        out.write(command.sendFieldOrder("C1#C2"));
        byte[] resp = in.readNBytes(24);
        return resp;
    }
    public byte[] sendRecord() throws IOException{
        Command command = new Command(0x0000003c);
        out.write(command.sendRecord("00000046222619G'on?QRNW1qQVpS#1"));
        byte[] resp = in.readNBytes(24);
        return resp;
    }


    public void sendRecordFromCSV(String csvPath) throws FileNotFoundException, IOException {
        File file = new File(csvPath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        Command command = new Command(0x0000003c);
        String line = "";
        while((line=br.readLine())!=null) {
            out.write(command.sendRecord(line));
            System.out.println(Arrays.toString(in.readNBytes(24)));
        }
    }
}