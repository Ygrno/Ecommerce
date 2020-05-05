package NetworkLayer.passiveObjects;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class connectionHandler {

    private Socket socket;
    private MessagingProtocol protocol;


    public connectionHandler(Socket s){
        this.socket = s;
        this.protocol = new MessagingProtocol(this);

        try {
            this.handshake();
        }catch (Exception e){

        }
    }

    private void handshake() throws Exception {

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        Scanner s = new Scanner(in, "UTF-8");

        String data = s.useDelimiter("\\r\\n\\r\\n").next();
        Matcher get = Pattern.compile("^GET").matcher(data);

        if (get.find()) {
            Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
            match.find();
            byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                    + "Connection: Upgrade\r\n"
                    + "Upgrade: websocket\r\n"
                    + "Sec-WebSocket-Accept: "
                    + Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")))
                    + "\r\n\r\n").getBytes("UTF-8");
            out.write(response, 0, response.length);
        }
    }

    public void send(String msg){
        try {
            OutputStream out = socket.getOutputStream();

            out.write(msg.getBytes());
            out.flush();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private byte[] encode(byte[] encoded, byte[] key){
        byte[] decoded = new byte[encoded.length];
        for (int i = 0; i < encoded.length; i++) {
            decoded[i] = (byte) (encoded[i] ^ key[i & 0x3]);
        }
        return decoded;
    }

    private String readMessage(DataInputStream in) throws Exception{
        int start_byte = in.read();
        if(start_byte != 129){
            throw new Exception("first byte not 129 ");
        }

        int size_byte = in.read() - 128;
        byte[] key = new byte[] { (byte) in.read(), (byte) in.read(), (byte) in.read(), (byte) in.read() };
        byte[] encoded = new byte[size_byte];

        int c = 0;

        while (size_byte != 0){
            encoded[c] = (byte)in.read();
            c++;
            size_byte--;
        }

        return new String(encode(encoded, key));

    }


    public void serve() throws Exception{
        while (true){
            DataInputStream in = new DataInputStream(socket.getInputStream());
            System.out.println(readMessage(in));
        }
    }

}
