import java.util.*;
import java.io.*;
import java.util.function.Predicate;

// CO4
class Vehicle {
    String name;
    String number;
    long phone;
    int hours;
    int pin;
    int type;

    Vehicle(String name, String number, long phone,
            int hours, int pin, int type) {
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.hours = hours;
        this.pin = pin;
        this.type = type;
    }

    String getName() {
        return name;
    }

    String getNumber() {
        return number;
    }

    long getPhone() {
        return phone;
    }

    int getHours() {
        return hours;
    }

    int getPin() {
        return pin;
    }

    int getType() {
        return type;
    }
}

// CO4
class ParkingSlot {
    private int level;
    private int number;
    private Vehicle vehicle;

    ParkingSlot(int level, int number) {
        this.level = level;
        this.number = number;
    }

    boolean isOccupied() {
        return vehicle != null;
    }

    Vehicle getVehicle() {
        return vehicle;
    }

    void parkVehicle(Vehicle v) {
        vehicle = v;
    }

    void removeVehicle() {
        vehicle = null;
    }

    int getLevel() {
        return level;
    }

    int getNumber() {
        return number;
    }
}

public class MultiLevelParkingLotManager {

    static Scanner hi = new Scanner(System.in);

    // CO4
    static ParkingSlot[][] slots = new ParkingSlot[2][3];

    // CO6
    static ArrayList<Vehicle> vehicleList = new ArrayList<>();

    static int totalRevenue = 0;

    // CO4
    static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(hi.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // CO4
    static long readLong(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Long.parseLong(hi.next());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid phone number.");
            }
        }
    }

    static int calculateAmount(int type, int hours) {
        if (type == 1)
            return hours * 20;
        else if (type == 2)
            return hours * 50;
        else if (type == 3)
            return hours * 100;
        else
            return 0;
    }

    static void initializeSlots() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                slots[i][j] = new ParkingSlot(i + 1, j + 1);
            }
        }
    }

    static void displaySlots() {
        System.out.println("\n----- PARKING SLOTS -----");

        for (int i = 0; i < 2; i++) {
            System.out.print("Level " + (i + 1) + ": ");

            for (int j = 0; j < 3; j++) {
                if (slots[i][j].isOccupied())
                    System.out.print((j + 1) + "-Occupied   ");
                else
                    System.out.print((j + 1) + "-Empty   ");
            }

            System.out.println();
        }
    }

    static void customerMenu() {
        int choice;

        do {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Back");

            choice = readInt("Enter Choice: ");

            switch (choice) {
                case 1:
                    parkVehicle();
                    break;
                case 2:
                    removeVehicle();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 3);
    }

    static void parkVehicle() {
        int type, level, slot, hours, pin;

        System.out.println("\n1. Bike");
        System.out.println("2. Car");
        System.out.println("3. Heavy Vehicle");
        System.out.println("4. Emergency Vehicle");

        type = readInt("Choose Vehicle Type: ");

        if (type < 1 || type > 4) {
            System.out.println("Invalid Vehicle Type");
            return;
        }

        displaySlots();

        level = readInt("Enter Level (1-2): ");
        slot = readInt("Enter Slot (1-3): ");

        if (level < 1 || level > 2 || slot < 1 || slot > 3) {
            System.out.println("Invalid Level or Slot");
            return;
        }

        level--;
        slot--;

        if (slots[level][slot].isOccupied()) {
            System.out.println("Slot already occupied.");
            return;
        }

        System.out.print("Customer Name: ");
        String name = hi.nextLine();

        System.out.print("Vehicle Number: ");
        String number = hi.nextLine().trim();

        if (number.isEmpty()) {
            System.out.println("Vehicle number cannot be empty.");
            return;
        }

        // CO5
        for (Vehicle v : vehicleList) {
            if (v.getNumber().equalsIgnoreCase(number)) {
                System.out.println("This vehicle is already parked.");
                return;
            }
        }

        long phone = readLong("Phone Number: ");
        hours = readInt("Parking Hours: ");

        if (hours <= 0) {
            System.out.println("Hours must be positive.");
            return;
        }

        pin = readInt("Create PIN: ");

        // CO4
        Vehicle v = new Vehicle(name, number, phone,
                hours, pin, type);

        slots[level][slot].parkVehicle(v);

        // CO6
        vehicleList.add(v);

        int amount = calculateAmount(type, hours);
        totalRevenue += amount;

        // CO5
        StringBuilder receipt = new StringBuilder();

        receipt.append("\n===== PARKING RECEIPT =====\n");
        receipt.append("Customer Name: ").append(name).append("\n");
        receipt.append("Vehicle Number: ").append(number).append("\n");
        receipt.append("Slot: L").append(level + 1)
                .append("S").append(slot + 1).append("\n");
        receipt.append("Hours: ").append(hours).append("\n");
        receipt.append("Final Amount: Rs.").append(amount).append("\n");

        System.out.println(receipt);

        saveRecords();
    }

    static void removeVehicle() {
        System.out.print("Enter Vehicle Number: ");
        String search = hi.nextLine().trim();

        // CO6
        Predicate<Vehicle> check =
                v -> v.getNumber().equalsIgnoreCase(search);

        Vehicle found = null;
        int level = -1, slot = -1;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                Vehicle v = slots[i][j].getVehicle();

                if (v != null && check.test(v)) {
                    found = v;
                    level = i;
                    slot = j;
                }
            }
        }

        if (found == null) {
            System.out.println("Vehicle Not Found.");
            return;
        }

        System.out.println("Customer: " + found.getName());
        System.out.println("Slot: L" + (level + 1) + "S" + (slot + 1));

        int enteredPin = readInt("Enter PIN: ");

        if (enteredPin == found.getPin()) {
            System.out.println("Bill Paid Successfully.");
            System.out.println("Vehicle Removed.");

            slots[level][slot].removeVehicle();
            vehicleList.remove(found);

            saveRecords();
        } else {
            System.out.println("Incorrect PIN.");
            System.out.println("1. Try Again");
            System.out.println("2. Forgot PIN");

            int choice = readInt("Enter Choice: ");

            if (choice == 1) {
                int retryPin = readInt("Enter PIN Again: ");

                if (retryPin == found.getPin()) {
                    slots[level][slot].removeVehicle();
                    vehicleList.remove(found);
                    System.out.println("Vehicle Removed.");
                    saveRecords();
                } else {
                    System.out.println("Incorrect PIN.");
                }
            } else if (choice == 2) {
                long phone = readLong("Enter Phone Number: ");

                if (phone == found.getPhone()) {
                    System.out.println("PIN (Demo): " + found.getPin());
                } else {
                    System.out.println("Contact Parking Authority.");
                }
            }
        }
    }

    // CO5
    static void searchVehicle() {
        System.out.print("Enter Vehicle Number: ");
        String search = hi.nextLine().trim();

        boolean found = false;

        for (Vehicle v : vehicleList) {
            if (v.getNumber().equalsIgnoreCase(search)) {
                System.out.println("Customer: " + v.getName());
                System.out.println("Vehicle: " + v.getNumber());
                found = true;
            }
        }

        if (!found)
            System.out.println("Vehicle Not Found.");
    }

    // CO5
    static void sortVehicles() {
        ArrayList<String> numbers = new ArrayList<>();

        for (Vehicle v : vehicleList) {
            numbers.add(v.getNumber());
        }

        Collections.sort(numbers, String.CASE_INSENSITIVE_ORDER);

        System.out.println("\nSorted Vehicle Numbers:");

        for (String number : numbers) {
            System.out.println(number);
        }
    }

    // CO6
    static void filterVehicles() {
        int type = readInt(
                "Enter Type (1 Bike, 2 Car, 3 Heavy, 4 Emergency): ");

        long count = vehicleList.stream()
                .filter(v -> v.getType() == type)
                .count();

        System.out.println("Matching Vehicles: " + count);

        vehicleList.stream()
                .filter(v -> v.getType() == type)
                .forEach(v -> System.out.println(v.getNumber()));
    }

    // CO6
    static void countVehicles() {
        System.out.println("Occupied Vehicles: "
                + vehicleList.stream().count());

        vehicleList.stream()
                .forEach(v -> System.out.println(v.getNumber()));
    }

    static void adminMenu() {
        int choice;

        int adminPin = readInt("Enter Admin PIN: ");

        if (adminPin != 9090) {
            System.out.println("Invalid Admin PIN.");
            return;
        }

        do {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Slot Status");
            System.out.println("2. Total Revenue");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Sort Vehicle Numbers");
            System.out.println("5. Filter by Vehicle Type");
            System.out.println("6. Count Vehicles");
            System.out.println("7. Save Records");
            System.out.println("8. Back");

            choice = readInt("Enter Choice: ");

            switch (choice) {
                case 1:
                    displaySlots();

                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 3; j++) {
                            Vehicle v = slots[i][j].getVehicle();

                            if (v != null) {
                                System.out.println(
                                        "L" + (i + 1) + "S" + (j + 1)
                                                + " - " + v.getName()
                                                + " (" + v.getNumber() + ")");
                            }
                        }
                    }
                    break;

                case 2:
                    System.out.println("Total Revenue: Rs." + totalRevenue);
                    break;

                case 3:
                    searchVehicle();
                    break;

                case 4:
                    sortVehicles();
                    break;

                case 5:
                    filterVehicles();
                    break;

                case 6:
                    countVehicles();
                    break;

                case 7:
                    saveRecords();
                    break;

                case 8:
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 8);
    }

    // CO5
    static void saveRecords() {
        try {
            FileWriter fw = new FileWriter("parking.txt");

            fw.write("REVENUE|" + totalRevenue + "\n");

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    Vehicle v = slots[i][j].getVehicle();

                    if (v != null) {
                        fw.write((i + 1) + "|" + (j + 1) + "|"
                                + v.getName() + "|" + v.getNumber() + "|"
                                + v.getPhone() + "|" + v.getHours() + "|"
                                + v.getPin() + "|" + v.getType() + "\n");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving records.");
        }
    }

    // CO5
    static void loadRecords() {
        File file = new File("parking.txt");

        if (!file.exists())
            return;

        try {
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split("\\|");

                if (data[0].equals("REVENUE")) {
                    totalRevenue = Integer.parseInt(data[1]);
                } else if (data.length == 8) {
                    int level = Integer.parseInt(data[0]) - 1;
                    int slot = Integer.parseInt(data[1]) - 1;

                    String name = data[2];
                    String number = data[3];
                    long phone = Long.parseLong(data[4]);
                    int hours = Integer.parseInt(data[5]);
                    int pin = Integer.parseInt(data[6]);
                    int type = Integer.parseInt(data[7]);

                    Vehicle v = new Vehicle(name, number, phone,
                            hours, pin, type);

                    slots[level][slot].parkVehicle(v);
                    vehicleList.add(v);
                }
            }
            System.out.println("Previous records loaded.");

        } catch (Exception e) {
            System.out.println("Could not load saved records.");
        }
    }

    static void thankYou() {
        System.out.println("\n==================================");
        System.out.println("Thank You for using 67 PARKING LOT.");
        System.out.println("Developed by:");
        System.out.println("Vinay");
        System.out.println("Praneeth");
        System.out.println("Harry");
        System.out.println("©2026 vinaygoat");
        System.out.println("Copyright 2026 nomoney Corporation. All Rights Reserved.");
        System.out.println("==================================");
    }
}
class Main {
    public static void main(String[] args) {
        MultiLevelParkingLotManager.initializeSlots();
        MultiLevelParkingLotManager.loadRecords();

        int choice;

        do {
            System.out.println("\n===== 67 PARKING LOT =====");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");

            choice = MultiLevelParkingLotManager.readInt(
                    "Enter Choice: ");

            switch (choice) {
                case 1:
                    MultiLevelParkingLotManager.customerMenu();
                    break;

                case 2:
                    MultiLevelParkingLotManager.adminMenu();
                    break;

                case 3:
                    MultiLevelParkingLotManager.saveRecords();
                    MultiLevelParkingLotManager.thankYou();
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 3);
    }
}