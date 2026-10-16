import java.util.Scanner;
import java.nio.file.*;
import java.io.*;

class Product {
    String name;
    int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

public class VendingMachineController {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        
        int choice, amount, change, continueChoice = 1;

       Product[] p = {
            new Product("Water", 20),
            new Product("Chips", 30),
            new Product("Juice", 40),
            new Product("Chocolate", 50)
        };

        while (continueChoice == 1) {

            System.out.println("\n===== VENDING MACHINE =====");
            for (int i = 0; i < p.length; i++) {
                System.out.println((i + 1) + ". " + p[i].name
                        + " - Rs." + p[i].price);
            }
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            try { 
                choice = Integer.parseInt(sc.nextLine());

                if (choice == 5) {
                    System.out.println("Thank you! Visit again.");
                    break;
                }

                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice.");
                    continue;
                }

                Product selected = p[choice - 1];
                System.out.println("You selected " + selected.name);
                System.out.println("Price = Rs." + selected.price);

                System.out.print("Enter amount: ");
                amount = Integer.parseInt(sc.nextLine());

                if (amount >= selected.price) {
                    change = amount - selected.price;
                    System.out.println("Payment successful!");
                    System.out.println("Product dispensed.");
                    System.out.println("Change = Rs." + change);

                    
                    Files.writeString(
                        Path.of("transactions.txt"),
                        selected.name + ", Paid: Rs." + amount
                        + ", Change: Rs." + change + "\n",
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                    );

                } else {
                    System.out.println("Insufficient amount.");
                    System.out.println("You need Rs."
                            + (selected.price - amount) + " more.");
                }

            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input or file error.");
                continue;
            }

            
            continueChoice = askAgain(sc);
        }

        System.out.println("\n===== MACHINE CLOSED =====");
        sc.close();
    }

    
    static int askAgain(Scanner sc) {
        System.out.println("\nPurchase another item?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter your choice: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return 2;
        }
    }
}