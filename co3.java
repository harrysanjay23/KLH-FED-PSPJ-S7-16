import java.util.*;
class co3
{
    void types() {
        System.out.println("\nChoose Vehicle Type");
        System.out.println("1. Bike.");
        System.out.println("2. Car.");
        System.out.println("3. Heavy Vehicle.");
    }
    static String[][] parking={
            {"Vacant","Vacant","Vacant"},
            {"Vacant","Vacant","Vacant"}
    };

    static int revenue=0;

    static int calculation(int type, int hours)
    {
        if(type==1) return hours*20;
        else if(type==2) return hours*50;
        else if(type==3) return hours*100;
        else return 0;
    }

    static void slots()
    {
        System.out.println("\nParking Slots--");
        for(int i = 0; i< parking.length; i++)
        {
            for(int j = 0; j< parking[i].length; j++)System.out.print(parking[i][j] +" ");
            System.out.println();
        }
    }

    static int recursiveTotal(int n)
    {
        if(n==0) return 0;
        return n+recursiveTotal(n-1);
    }

    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("WELCOME TO 67 PARKING LOT!");
        slots();

        co3 obj=new co3();
        obj.types();

        System.out.print("\nVehicle Type: ");
        int type=sc.nextInt();

        System.out.print("Hours: ");
        int hours=sc.nextInt();

        int amount=calculation(type,hours);

        revenue=revenue+amount;

        parking[0][0]="Occupied";

        slots();

        System.out.println("\nAmount to be paid: $" +amount);
    }
}