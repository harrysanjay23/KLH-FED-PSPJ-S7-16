import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class co5
{
    public static void main(String[] args)
    {
        Path file = Path.of("parking.txt");
        try (Scanner sc = new Scanner(System.in))
        {
            List<String[]> list = new ArrayList<>();
            if (Files.exists(file))
            {
                List<String> lines = Files.readAllLines(file);
                for (int i = 0; i < lines.size(); i++)
                {
                    String[] r = parse(lines.get(i));
                    if (r == null) System.out.println("Skipping malformed line " + (i + 1) + ": " + lines.get(i));
                    else list.add(r);
                }
            }
            else System.out.println("No data file yet - starting fresh.");
            System.out.println("Loaded " + list.size() + " existing record(s).");

            System.out.print("How many vehicles to add? ");
            int n;
            while (true)
            {
                try
                {
                    n = Integer.parseInt(sc.nextLine().trim()); if (n > 0) break;
                }
                catch (NumberFormatException ignored) { }
                System.out.print("Enter a positive whole number: ");
            }
            for (int i = 1; i <= n; i++)
            {
                System.out.print("Enter vehicle " + i + " (format: NUMBER,OWNER): ");
                String[] r = parse(sc.nextLine());
                if (r == null) { System.out.println("  Invalid format. Use NUMBER,OWNER (e.g. TS09AB1234,Ravi)."); i--; }
                else if (list.stream().anyMatch(x -> x[0].equals(r[0]))) { System.out.println("  Vehicle already exists, skipped."); i--; }
                else list.add(r);
            }
            list.sort(Comparator.comparing(r -> r[0]));

            System.out.print("\nSearch vehicle number: ");
            String key = sc.nextLine().trim().toUpperCase();
            int pos = Arrays.binarySearch(list.stream().map(r -> r[0]).toArray(String[]::new), key);
            System.out.println(pos >= 0 ? "Vehicle found - Owner: " + list.get(pos)[1] : "Vehicle not found.");

            StringBuilder sb = new StringBuilder("Parking Records: ").append(list.size()).append("\n");
            list.forEach(r -> sb.append(String.join(" | ", r)).append("\n"));
            System.out.println("\n" + sb);

            try (BufferedWriter bw = Files.newBufferedWriter(file))
            {
                for (String[] r : list) { bw.write(String.join(",", r)); bw.newLine(); }
            }
            Files.writeString(Path.of("parking_report.txt"), sb);
            System.out.println("Records saved to parking.txt, report written to parking_report.txt.");

        }
        catch (IOException e)
        {
            System.out.println("File error: " + e.getMessage());
        }
    }

    static String[] parse(String line)
    {
        String[] p = line.split(",");
        if (p.length != 2) return null;
        String num = p[0].trim().toUpperCase(), owner = p[1].trim();
        return num.matches("[A-Z0-9]{4,12}") && !owner.isEmpty() ? new String[] { num, owner } : null;
    }
}