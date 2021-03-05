/*
    Created by: Steven Smith (mastrHyperion98)
    Created for: Passlock project

    Passlock is a serializable class that contains information needed to encrypt an object contaiing all data
    stored in the local database. The class contains a static read function and and ability to write itself to a
    file.

    Project under the GPL3 license.
 */

package com.mastrHyperion98.struct;

import com.mastrHyperion98.Encoder.AES;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class Passlock implements Serializable {
    private List<SerializableData> data;
    private static final String encodeKey = "";
    public Passlock(List<SerializableData> list){
        data = new LinkedList(list);
    }

    public void write(File file) throws IOException {
        // Write serializable object

        byte[] decodeKey = encodeKey.getBytes(StandardCharsets.UTF_8);
        SealedObject object = AES.encryptObject(this, new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES"));
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(object);
        out.flush();
        out.close();
    }

    public static Passlock read(File file) throws IOException, ClassNotFoundException {
        byte[] decodeKey = encodeKey.getBytes(StandardCharsets.UTF_8);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        SealedObject encryptedObject = (SealedObject) in.readObject();
        Passlock decryptedObject = (Passlock) AES.decryptObject(encryptedObject, new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES"));
        in.close();
        return decryptedObject;
    }

    public List<SerializableData> getPasswords(){
        return data;
    }

    public int getSize(){
        return data.size();
    }
}
