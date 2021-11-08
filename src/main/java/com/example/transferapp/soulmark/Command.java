package com.example.transferapp.soulmark;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Command {

    private static byte[] intToLittleEndian(long numero) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt((int) numero);
        return bb.array();
    }
    long header = 0x414d4943;
    long command;
    long size = 20;
    long type=0;
    long seq = ThreadLocalRandom.current().nextInt(0,2000000);
    byte[] total = new byte[20];


    public Command(long command) {
        this.command=command;
        System.arraycopy(intToLittleEndian(header), 0, total, 0, 4);
        System.arraycopy(intToLittleEndian(this.command), 0, total, 4, 4);
        System.arraycopy(intToLittleEndian(size), 0, total, 8, 4);
        System.arraycopy(intToLittleEndian(type), 0, total, 12, 4);
        System.arraycopy(intToLittleEndian(seq), 0, total, 16, 4);

    }

    public byte[] setAndGetFileName(String s){
        byte[] fileName = new byte[520];
        byte[] stringByte=s.getBytes();
        int j=0;
        for(int i=0;i<2*stringByte.length;i+=2){
            fileName[i]=stringByte[j];
            fileName[i+1]=0;
            j++;
        }
        return fileName;
    }

    public byte[] setAndGetRecord(String s){
        byte[] record = new byte[512];
        byte[] stringByte=s.getBytes();
        int j=0;
        for(int i=0;i<2*stringByte.length;i+=2){
            record[i]=stringByte[j];
            record[i+1]=0;
            j++;
        }
        return record;
    }

    public byte[] setAndGetFileNameWithSpecificSize(String s){
        byte[] fileName = new byte[s.length()*2+2];
        byte[] stringByte=s.getBytes();
        System.out.println("stringbyte");
        System.out.println(Arrays.toString(stringByte));
        int j=0;
        for(int i=0;i<2*stringByte.length;i+=2){
            fileName[i]=stringByte[j];
            fileName[i+1]=0;
            j++;
        }
        byte[] ending="\0\0".getBytes();
        fileName[2*stringByte.length]=ending[0];
        fileName[2*stringByte.length+1]=ending[1];
        System.out.println(Arrays.toString(fileName));
        return fileName;
    }

    public byte[] getBytes(){
        return total;
    }

    public String getStringBytes(){
        return Arrays.toString(total);
    }

    byte[] deleteFile(String s){
        byte[] file = setAndGetFileName(s);
        byte[] request = new byte[544];
        System.arraycopy(intToLittleEndian(header), 0, request, 0, 4);
        System.arraycopy(intToLittleEndian(command), 0, request, 4, 4);
        System.arraycopy(intToLittleEndian(544), 0, request, 8, 4);
        System.arraycopy(intToLittleEndian(0), 0, request, 12, 4);
        System.arraycopy(intToLittleEndian(seq), 0, request, 16, 4);
        System.arraycopy(intToLittleEndian(0), 0, request, 20, 4);
        System.arraycopy(file, 0, request, 24, 520);

        return request;

    }

    byte[] addFile(String s){
        byte[] file = setAndGetFileName(s);
        byte[] request = new byte[544];
        System.arraycopy(intToLittleEndian(header), 0, request, 0, 4);
        System.arraycopy(intToLittleEndian(command), 0, request, 4, 4);
        System.arraycopy(intToLittleEndian(544), 0, request, 8, 4);
        System.arraycopy(intToLittleEndian(0), 0, request, 12, 4);
        System.arraycopy(intToLittleEndian(seq), 0, request, 16, 4);
        System.arraycopy(intToLittleEndian(1), 0, request, 20, 4);
        System.arraycopy(file, 0, request, 24, 520);

        return request;

    }

    byte[] sendFileToPrint(String s){
        byte[] file = setAndGetFileNameWithSpecificSize(s);
        byte[] request = new byte[20+file.length];
        System.arraycopy(intToLittleEndian(header), 0, request, 0, 4);
        System.arraycopy(intToLittleEndian(command), 0, request, 4, 4);
        System.arraycopy(intToLittleEndian(request.length), 0, request, 8, 4);
        System.arraycopy(intToLittleEndian(0), 0, request, 12, 4);
        System.arraycopy(intToLittleEndian(seq), 0, request, 16, 4);
        System.arraycopy(file, 0, request, 20, file.length);
        System.out.println(request.length);
        return request;
    }

    byte[] sendFieldOrder(String s){
        byte[] file = setAndGetFileNameWithSpecificSize(s);
        byte[] request = new byte[20+file.length];
        System.arraycopy(intToLittleEndian(header), 0, request, 0, 4);
        System.arraycopy(intToLittleEndian(command), 0, request, 4, 4);
        System.arraycopy(intToLittleEndian(request.length), 0, request, 8, 4);
        System.arraycopy(intToLittleEndian(0), 0, request, 12, 4);
        System.arraycopy(intToLittleEndian(seq), 0, request, 16, 4);
        System.arraycopy(file, 0, request, 20, file.length);
        System.out.println(request.length);
        return request;
    }
    byte[] sendRecord(String s){
        byte[] record = setAndGetRecord(s);
        byte[] request = new byte[24+record.length];
        System.arraycopy(intToLittleEndian(header), 0, request, 0, 4);
        System.arraycopy(intToLittleEndian(command), 0, request, 4, 4);
        System.arraycopy(intToLittleEndian(request.length), 0, request, 8, 4);
        System.arraycopy(intToLittleEndian(0), 0, request, 12, 4);
        System.arraycopy(intToLittleEndian(seq), 0, request, 16, 4);
        System.arraycopy(intToLittleEndian(0),0,request,20,4);
        System.arraycopy(record, 0, request, 24, record.length);
        return request;
    }

}
