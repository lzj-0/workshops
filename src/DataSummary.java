import java.io.*;
import java.util.*;

public class DataSummary {


    public static void main(String[] args) throws IOException {
        String filename = args[0];
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File does not exist");
            System.exit(-1);
        }

        FileReader fr = new FileReader(file);
        LineNumberReader lnr = new LineNumberReader(fr);
        String line = "";
        ArrayList<PhoneApp> apps = new ArrayList<>();
        lnr.readLine();
        int i = 0;
        while((line = lnr.readLine()) != null) {
            //System.out.println(line);
            line = line.replace(", ", "");
            String[] row = line.split(",");
            try {
                if (i == 4311) {
                    throw new NumberFormatException();
                }
                apps.add(new PhoneApp(row[0], row[1], Float.parseFloat(row[2])));
            } catch (NumberFormatException e) {
                try {
                    String appname = line.split("\"")[1];
                    String category = line.split("\"")[2].split(",")[1];
                    Float rating = Float.parseFloat(line.split("\"")[2].split(",")[2]);
                    apps.add(new PhoneApp(appname, category, rating));
                } catch (ArrayIndexOutOfBoundsException err) {
                    String appname = "Women'" + line.split("\"")[3];
                    String category = line.split("\"")[4].split(",")[1];
                    Float rating = Float.parseFloat(line.split("\"")[4].split(",")[2]);
                    apps.add(new PhoneApp(appname, category, rating));
                }
            }
            i++;
        }
        lnr.close();
        fr.close();
        apps.remove(10472);
        List<PhoneApp> appsNoNaN = apps.stream().filter(app -> !app.getRating().isNaN()).toList();

        // List<PhoneApp> morethan45 = apps.stream().filter(app -> app.getRating() > 4.5).toList();
        // for (PhoneApp app: morethan45) {
        //     System.out.println(app);
        // }

        HashSet<String> categoriesSet = new HashSet<String>(appsNoNaN.stream().map(app -> app.getCategory()).toList());
        for (String category: categoriesSet) {
            List<PhoneApp> categoryApps = appsNoNaN.stream().filter(app -> app.getCategory().equals(category)).toList();
            Optional<PhoneApp> minApp = categoryApps.stream().min((app1, app2) -> Float.compare(app1.getRating(), app2.getRating()));
            Optional<PhoneApp> maxApp = categoryApps.stream().max((app1, app2) -> Float.compare(app1.getRating(), app2.getRating()));
            Float averageRating = categoryApps.stream().map(app -> app.getRating()).reduce(0.0f, (a, b) -> a + b) / categoryApps.stream().count();
            System.out.printf("Category: %s\n", category);
            System.out.printf("Best App: %s (%f)\n", maxApp.get().getApp(), maxApp.get().getRating());
            System.out.printf("Worst App: %s (%f)\n", minApp.get().getApp(), minApp.get().getRating());
            System.out.printf("Average Rating: %f\n\n", averageRating);
        }

    }
}
