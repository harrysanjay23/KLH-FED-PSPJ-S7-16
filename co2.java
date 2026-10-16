import java.util.*;
class co2
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int choice;

        do{
            System.out.println("\nWELCOME TO 67 PARKING LOT!");
            System.out.println("1. Park Vehicle.");
            System.out.println("2. Remove Vehicle.");
            System.out.println("3. Exit.");
            System.out.print("Enter Choice: ");
            choice=sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Vehicle parked successfully.");
                    break;
                case 2:
                    int pin=1234;
                    System.out.print("Enter PIN: ");
                    int entered=sc.nextInt();
                    if(pin==entered)System.out.println("Vehicle Removed.");
                    else System.out.println("Incorrect PIN.");
                    break;
                case 3:
                    System.out.println("Thank You for using 67 Parking Lot!");
                    break;
                default:
                    System.out.println("Invalid Choice.");
                    System.out.println("Please enter a valid choice!");
            }
        }while(choice!=3);
    }
}