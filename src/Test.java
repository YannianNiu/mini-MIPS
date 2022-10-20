import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Vector;

public class Test {


    public static void main(String[] args) throws IOException {
        String tempString = "00000000000000000000100000100000";
        FileReader fileReader = new FileReader("sample.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder(tempString);
        stringBuilder.insert(6,' ');
        stringBuilder.insert(12,' ');
        stringBuilder.insert(18,' ');
        stringBuilder.insert(24,' ');
        stringBuilder.insert(30,' ');
        tempString = stringBuilder.toString();

        System.out.println(tempString);
    }
}
