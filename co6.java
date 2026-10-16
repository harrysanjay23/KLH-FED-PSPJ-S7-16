import java.util.*;
import java.util.function.Predicate;

public class co6
{
    public static void main(String[] args)
    {
        try (Scanner sc = new Scanner(System.in))
        {
            List<String> vehicles = new ArrayList<>();
            for (int i = 0; i < 3; i++)
            {
                System.out.print("Enter Vehicle Number: ");
                vehicles.add(sc.nextLine());
            }

            System.out.println("\nParked Vehicles:");
            vehicles.stream().sorted().forEach(System.out::println);

            System.out.print("\nSearch Vehicle: ");
            String search = sc.nextLine();
            Predicate<String> match = v -> v.equalsIgnoreCase(search);

            Optional<String> hit = vehicles.stream().filter(match).findFirst();
            System.out.println(hit.isPresent() ? "Vehicle Found" : "Vehicle Not Found");

            System.out.println("Total Vehicles: " + vehicles.stream().count());
        }
    }
}