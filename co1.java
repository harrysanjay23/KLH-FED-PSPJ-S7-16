import java.util.*;
class co1
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);

        String name;
        String numberplate;
        long numberphone;
        int vehicle;
        int time;
        int price;
        int total;

        System.out.println("WELCOME TO 67 PARKING LOT!");
        System.out.print("Enter Full Name: ");
        name=sc.nextLine();
        System.out.print("Enter Vehicle Number: ");
        numberplate=sc.nextLine();
        System.out.print("Enter Phone Number: ");
        numberphone=sc.nextLong();

        System.out.println("\nChoose Vehicle Type");
        System.out.println("1. Bike.");
        System.out.println("2. Car.");
        System.out.println("3. Heavy Vehicle.");
        System.out.println("4. Emergency Vehicle.");

        System.out.print("\nChoose Vehicle Type: ");
        vehicle=sc.nextInt();
        System.out.print("\nParking Hours: ");
        time=sc.nextInt();

        if(vehicle==1)price=20;
        else if(vehicle==2)price=50;
        else if(vehicle==3)price=100;
        else price=0;

        total=price*time;

        System.out.println("\n--------------RECEIPT--------------");
        System.out.println("Customer Name : " +name);
        System.out.println("Vehicle Number: " +numberplate);
        System.out.println("Phone NUmber  : " +numberphone);
        System.out.println("   Time       : " +time);
        System.out.println("  Amount      : $" +total);
        System.out.println("THANK YOU FOR USING 67 PARKING LOT!");
        System.out.println("-----------------------------------");
    }
}