
package encryptdecrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Main {

    static String mode = "enc";
    static int key = 0;
    static String data = "";
    static String intFileName = "";
    static String outFileName = "";
    static String alg = "shift";

    public static void main(String[] args) throws IOException {
        // search the arguments for mode, key and data,
        // check if args have the specified string,
        // if yes, assign the next  value to the corresponding static field.
        for (int i = 0; i < args.length; i+= 2) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-out":
                    outFileName = args[i + 1];
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    intFileName = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
            }
        }
        // perform different operations on data for different target
        putout();
    }


    static void putout() throws IOException {

        if (Objects.equals(data, "") && !Objects.equals(intFileName, "")) {
            data = Files.readString(Path.of(intFileName));
        }

        PrintWriter PrintWriter = Objects.equals(outFileName, "") ? new PrintWriter(System.out) : new  PrintWriter(outFileName);

        StringBuilder stringBuilder = new StringBuilder();

        if (alg.equals("shift")) {
            shift(PrintWriter, stringBuilder);
        } else {
            Unicode(PrintWriter, stringBuilder);
        }
    }

    static void shift(PrintWriter PrintWriter, StringBuilder stringBuilder) {

        if (Objects.equals(mode, "dec")) {
            key = 26 - (key % 26);
        }

        for (int ch : data.toCharArray()) {
            if (ch >= 97 && ch <= 122) {
                stringBuilder.append((char) ((ch - 97 + key) % 26 + 97));
            } else if (ch >= 65 && ch <= 90) {
                stringBuilder.append((char) ((ch - 65 + key) % 26 + 65));
            } else {
                stringBuilder.append((char) ch);
            }
        }

        PrintWriter.print(stringBuilder.toString());
        PrintWriter.close();

    }

    static void Unicode(PrintWriter PrintWriter, StringBuilder stringBuilder) {

        if (Objects.equals(mode, "dec")) {
            key = -key;
        }

        for (int c : data.toCharArray()) {
            stringBuilder.append((char) (c + key));
        }

        PrintWriter.print(stringBuilder.toString());
        PrintWriter.close();
    }
}
