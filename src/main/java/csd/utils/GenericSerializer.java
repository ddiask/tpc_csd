package csd.utils;

import lombok.SneakyThrows;

import java.io.*;

/**
 * Helps serialize classes that implement serializable interface
 */
public class GenericSerializer {

    @SneakyThrows
    public static <T extends Serializable> byte[] serialize(T val){
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(out)){
            objOut.writeObject(val);
            return out.toByteArray();
        }
    }

    @SneakyThrows
    public static <T extends Serializable> T deserialize(byte[] valBytes){
        try (ByteArrayInputStream in = new ByteArrayInputStream(valBytes);
             ObjectInputStream objIn = new ObjectInputStream(in)){
                 return (T) objIn.readObject();
             }
    }
}
