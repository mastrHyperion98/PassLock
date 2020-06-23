package Encoder;

public class sample {
    public static void main(String[] args){
        String message = "Jello is good!";
        String encrypted = AES.encrypt(message);
        String decrypted = AES.decrypt(encrypted);

        System.out.println(message);
        System.out.println(encrypted);
        System.out.println(decrypted);

    }
}
