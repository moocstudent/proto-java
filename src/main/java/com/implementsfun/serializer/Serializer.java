package com.implementsfun.serializer;

import com.google.protobuf.util.JsonFormat;
import com.implementsfun.protoj.LaptopMessage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer {
    public void writeBinaryFile(LaptopMessage.Laptop laptop,String filename)throws IOException {
        FileOutputStream out = new FileOutputStream(filename);
        laptop.writeTo(out);
        out.close();
    }
    public LaptopMessage.Laptop readBinaryFile(String filename)throws IOException{
        FileInputStream in = new FileInputStream(filename);
        LaptopMessage.Laptop laptop = LaptopMessage.Laptop.parseFrom(in);
        in.close();;
        return laptop;
    }
    public void writeJSONFile(LaptopMessage.Laptop laptop,String filename)throws IOException{
        JsonFormat.Printer printer = JsonFormat.printer()
                .includingDefaultValueFields()
                .preservingProtoFieldNames();
        String jsonString = printer.print(laptop);
        FileOutputStream outputStream = new FileOutputStream(filename);
        outputStream.write(jsonString.getBytes());
        outputStream.close();
    }
}
