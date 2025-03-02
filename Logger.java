import java.util.*;

class AccessLogger {
    private static AccessLogger instance;
    private List<String> logs = new ArrayList<>();

    private AccessLogger() {}

    public static AccessLogger getInstance() {
        if (instance == null) {
            instance = new AccessLogger();
        }
        return instance;
    }

    public void log(String message) {
        logs.add(message);
        System.out.println("📝 LOG: " + message);
    }

    public void showLogs() {
        System.out.println("\n📜 Access Log History:");
        if (logs.isEmpty()) {
            System.out.println("No logs recorded.");
        } else {
            for (String log : logs) {
                System.out.println(log);
            }
        }
    }
}

// Card Management System
class CardManager {
    private Map<String, String> cards = new HashMap<>(); // user -> card type
    private Map<String, String> floors = new HashMap<>(); // user -> floor
    private Map<String, String> rooms = new HashMap<>(); // user -> room
    private Map<String, String> accessLevels = new HashMap<>(); // user -> access level

    public void addCard(String user, String cardType, String floor, String room, String accessLevel) {
        cards.put(user, cardType);
        floors.put(user, floor);
        rooms.put(user, room);
        accessLevels.put(user, accessLevel);
        AccessLogger.getInstance().log("Card added: " + user + " | Type: " + cardType + " | Floor: " + floor + " | Room: " + room + " | Level: " + accessLevel);
        showAllCards();
    }

    public void removeCard(String user) {
        if (cards.containsKey(user)) {
            cards.remove(user);
            floors.remove(user);
            rooms.remove(user);
            accessLevels.remove(user);
            AccessLogger.getInstance().log("Card removed for " + user);
        } else {
            System.out.println("⚠ User not found.");
        }
        showAllCards();
    }

    public boolean userExists(String user) {
        return cards.containsKey(user);
    }

    public void showAllCards() {
        System.out.println("\n🎫 Active Cards List:");
        if (cards.isEmpty()) {
            System.out.println("No users have access cards.");
        } else {
            for (String user : cards.keySet()) {
                System.out.println("👤 " + user + " | Type: " + cards.get(user) + " | Floor: " + floors.get(user) + " | Room: " + rooms.get(user) + " | Level: " + accessLevels.get(user));
            }
        }
    }

    public String getUserFloor(String user) {
        return floors.getOrDefault(user, "Unknown");
    }

    public String getUserRoom(String user) {
        return rooms.getOrDefault(user, "Unknown");
    }

    public String getUserAccessLevel(String user) {
        return accessLevels.getOrDefault(user, "Low");
    }
}

// Verification Strategy Pattern
interface VerificationMethod {
    void verify(String user);
}

class PINVerification implements VerificationMethod {
    @Override
    public void verify(String user) {
        System.out.println(user + " verified using 🔢 PIN.");
    }
}

class BiometricVerification implements VerificationMethod {
    @Override
    public void verify(String user) {
        System.out.println(user + " verified using 🏷️ Biometric.");
    }
}

// Abstraction: Access Control
abstract class AccessControl {
    protected VerificationMethod method;

    protected AccessControl(VerificationMethod method) {
        this.method = method;
    }
    abstract void grantAccess(String user, String floor, String room, String accessLevel);
}

// Implementing Access Control
class EmployeeAccess extends AccessControl {
    public EmployeeAccess(VerificationMethod method) {
        super(method);
    }
    @Override
    void grantAccess(String user, String floor, String room, String accessLevel) {
        if (canAccess(floor, accessLevel)) {
            System.out.print("👷 Employee Access: ");
            method.verify(user);
            AccessLogger.getInstance().log(user + " granted Employee Access | Floor: " + floor + " | Room: " + room);
        } else {
            System.out.println("❌ Access Denied: " + user + " does not have permission to enter Floor " + floor);
        }
    }

    private boolean canAccess(String floor, String accessLevel) {
        int floorNum = Integer.parseInt(floor);
        return (accessLevel.equalsIgnoreCase("High")) ||
                (accessLevel.equalsIgnoreCase("Mid") && floorNum <= 5) ||
                (accessLevel.equalsIgnoreCase("Low") && floorNum <= 2);
    }
}

class ManagerAccess extends AccessControl {
    public ManagerAccess(VerificationMethod method) {
        super(method);
    }
    @Override
    void grantAccess(String user, String floor, String room, String accessLevel) {
        if (canAccess(floor, accessLevel)) {
            System.out.print("👨‍💼 Manager Access: ");
            method.verify(user);
            AccessLogger.getInstance().log(user + " granted Manager Access | Floor: " + floor + " | Room: " + room);
        } else {
            System.out.println("❌ Access Denied: " + user + " does not have permission to enter Floor " + floor);
        }
    }

    private boolean canAccess(String floor, String accessLevel) {
        int floorNum = Integer.parseInt(floor);
        return (accessLevel.equalsIgnoreCase("High")) ||
                (accessLevel.equalsIgnoreCase("Mid") && floorNum <= 5) ||
                (accessLevel.equalsIgnoreCase("Low") && floorNum <= 2);
    }
}

