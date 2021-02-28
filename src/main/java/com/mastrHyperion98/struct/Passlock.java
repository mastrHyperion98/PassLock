package com.mastrHyperion98.struct;

import com.mastrHyperion98.Encoder.AES;
import javafx.collections.ObservableList;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

public class Passlock implements Serializable {
    private List<Password> passwords;

    public Passlock(ObservableList<Password> list){
        long size = list.size();
        Iterator<Password> it = list.iterator();
        while(it.hasNext()){
            passwords.add(it.next());
        }
    }

    public void write(String password, String filepath) throws IOException {
        // Write serializable object
        byte[] decodeKey = Base64.getDecoder().decode(password);
        SealedObject object = AES.encryptObject(this, new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES"));
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath));
        out.writeObject(this);
    }

    public static Passlock read(String password, String filepath) throws IOException, ClassNotFoundException {
        byte[] decodeKey = Base64.getDecoder().decode(password);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath));
        SealedObject encryptedObject = (SealedObject) in.readObject();
        Passlock decryptedObject = (Passlock) AES.decryptObject(encryptedObject, new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES"));
        return decryptedObject;
    }
}
