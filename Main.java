import java.util.Scanner;

// Main Class
public class Main
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardManager cardManager = new CardManager();

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. Grant Access");
            System.out.println("4. Show Access Log");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("👤 Enter User Name:");
                String user = scanner.nextLine();

                System.out.println("🔑 Enter Card Type (Basic, MultiFloor, Temporary):");
                String cardType = scanner.nextLine();

                System.out.println("🏢 Enter Floor Number:");
                String floor = scanner.nextLine();

                System.out.println("🚪 Enter Room Number:");
                String room = scanner.nextLine();

                System.out.println("🎚️ Enter Access Level (Low, Mid, High):");
                String accessLevel = scanner.nextLine();

                cardManager.addCard(user, cardType, floor, room, accessLevel);

            } else if (choice == 2) {
                System.out.println("👤 Enter User Name to Remove:");
                String user = scanner.nextLine();
                cardManager.removeCard(user);

            } else if (choice == 3) {
                System.out.println("👤 Enter User Name:");
                String user = scanner.nextLine();

                if (!cardManager.userExists(user)) {
                    System.out.println("⚠ User not found. Please add user first.");
                    continue;
                }

                String floor = cardManager.getUserFloor(user);
                String room = cardManager.getUserRoom(user);
                String accessLevel = cardManager.getUserAccessLevel(user);

                AccessControl employeeAccess = new EmployeeAccess(new PINVerification());
                AccessControl managerAccess = new ManagerAccess(new BiometricVerification());

                employeeAccess.grantAccess(user, floor, room, accessLevel);
                managerAccess.grantAccess(user, floor, room, accessLevel);


            } else if (choice == 4) {
                AccessLogger.getInstance().showLogs();

            } else if (choice == 5) {
                System.out.println("🚪 Exiting system...");
                break;
            } else {
                System.out.println("⚠ Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}