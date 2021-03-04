package com.mastrHyperion98.struct;

import com.mastrHyperion98.Encoder.AES;
import javafx.collections.ObservableList;

import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Passlock implements Serializable {
    private List<SerializableData> data;

    public Passlock(List<SerializableData> list){
        data = new LinkedList(list);
    }

    public void write(String password, File file) throws IOException {
        // Write serializable object
        byte[] decodeKey = Base64.getDecoder().decode(password);
        SealedObject object = AES.encryptObject(this, new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES"));
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(object);
        out.flush();
        out.close();
    }

    public static Passlock read(String password, File file) throws IOException, ClassNotFoundException {
        byte[] decodeKey = Base64.getDecoder().decode(password);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        SealedObject encryptedObject = (SealedObject) in.readObject();
        Passlock decryptedObject = (Passlock) AES.decryptObject(encryptedObject, new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES"));
        in.close();
        return decryptedObject;
    }

    public List<SerializableData> getPasswords(){
        return data;
    }
}
