package fc;

import java.io.*;
import java.util.ArrayList;

public class Cookie {

    public ArrayList<String> read(String file) throws IOException {
        File f = new File(file);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String input = "";
        ArrayList<String> cookies = new ArrayList<>();
        while (null != (input = br.readLine())) {
            cookies.add(input);
        }
        br.close();
        fr.close();
        return cookies;
    }

    public String getRandomCookie(ArrayList<String> cookies) {
        int ind = (int) (Math.random() * cookies.size());
        return cookies.get(ind);
    }
}
