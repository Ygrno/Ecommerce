package NetworkLayer.passiveObjects;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class connectionHandler {

    private Socket socket;
    private MessagingProtocol protocol;
    private DataInputStream in;
    private OutputStream out;


    public connectionHandler(Socket s){

        this.socket = s;
        this.protocol = new MessagingProtocol(this);

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = socket.getOutputStream();

            this.handshake();
        }catch (Exception e){

        }
    }

    private void handshake() throws Exception {

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
        send(msg.getBytes());
    }

    private byte[] intToByte(int n){
        BigInteger bi = BigInteger.valueOf(n);
        return bi.toByteArray();
    }

    private void send(byte[] msg){

        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byteStream.write(0x81);

            byte[] length_bytes = intToByte(msg.length);
            if(length_bytes.length > 1){
                byteStream.write(126);
            }

            byteStream.write(length_bytes);
            byteStream.write(msg);

            byte[] res = byteStream.toByteArray();

            out.write(res, 0, res.length);
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
        while (start_byte != 129){
            start_byte = in.read();
        }


        if(start_byte != 129){
            throw new Exception("first byte not 129 ");
        }

        //[0x81=129, 126, l, l, msg]...

        int size_byte = in.read() - 128;
        if(size_byte == 126){
            byte[] arr = { (byte)in.read(), (byte)in.read()};
            ByteBuffer wrapped = ByteBuffer.wrap(arr); // big-endian by default
            short num = wrapped.getShort(); // 1
            size_byte = num;
        }
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
            this.protocol.proccess(readMessage(in));
        }
    }

}

