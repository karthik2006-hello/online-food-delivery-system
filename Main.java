import java.util.*;

class FoodItem {
    int id;
    String name;
    double price;
    FoodItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class OrderItem {
    FoodItem item;
    int quantity;
    OrderItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    double getTotalPrice() {
        return item.price * quantity;
    }
}

class Customer {
    String name;
    String phone;
    String address;
    Customer(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}

public class Main {
    static Map<String, List<String>> hotelsByLocation = new HashMap<>();
    static Map<String, Integer> deliveryTimes = new HashMap<>();
    static Map<String, List<FoodItem>> menuByHotel = new HashMap<>();

    static List<FoodItem> menu = new ArrayList<>();
    static List<OrderItem> cart = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Customer customer = null;
    static String selectedLocation = "";
    static String hotelName = "";
    static int deliveryTime = 0;

    public static void main(String[] args) {
        setupHotels();
        setupMenus();

        System.out.println("       Welcome to EpicEra's Online Food Delivery \n");
        System.out.println("Locations we deliver to: " + hotelsByLocation.keySet());

        askLocation();

        while (true) {
            System.out.println("\n============== User Menu ==============");
            System.out.println("1. Order Food");
            System.out.println("2. Cancel Order");
            System.out.println("3. View Bill & Pay");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    orderFood();
                    break;
                case 2:
                    cancelOrder();
                    break;
                case 3:
                    takeCustomerDetails();
                    viewBill();
                    break;
                case 4:
                    System.out.println("Thank you! Visit again.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void setupHotels() {
        hotelsByLocation.put("Trichy", Arrays.asList("The Royal Food", "Urban Spice", "Classic Bites"));
        hotelsByLocation.put("Thanjavor", Arrays.asList("Spicy Treats", "Curry Leaf", "Rice & Spice"));
        hotelsByLocation.put("Viralimalai", Arrays.asList("Biryani Palace", "Hot & Tandoor", "Village Feast"));
        hotelsByLocation.put("Manapparai", Arrays.asList("Noodle Corner", "Street Flavors", "Chicken Hub"));

        deliveryTimes.put("The Royal Food", 20);
        deliveryTimes.put("Urban Spice", 25);
        deliveryTimes.put("Classic Bites", 22);
        deliveryTimes.put("Spicy Treats", 15);
        deliveryTimes.put("Curry Leaf", 18);
        deliveryTimes.put("Rice & Spice", 20);
        deliveryTimes.put("Biryani Palace", 25);
        deliveryTimes.put("Hot & Tandoor", 28);
        deliveryTimes.put("Village Feast", 26);
        deliveryTimes.put("Noodle Corner", 18);
        deliveryTimes.put("Street Flavors", 20);
        deliveryTimes.put("Chicken Hub", 22);
    }

    static void setupMenus() {
        menuByHotel.put("The Royal Food", Arrays.asList(
                new FoodItem(1, "Pizza", 250),
                new FoodItem(2, "Coke", 20),
                new FoodItem(3, "Grill Chicken", 400)
        ));
        menuByHotel.put("Urban Spice", Arrays.asList(
                new FoodItem(1, "Paneer Butter Masala", 200),
                new FoodItem(2, "Garlic Naan", 40),
                new FoodItem(3, "Veg Pulao", 150)
        ));
        menuByHotel.put("Classic Bites", Arrays.asList(
                new FoodItem(1, "Veg Burger", 120),
                new FoodItem(2, "French Fries", 60),
                new FoodItem(3, "Cold Coffee", 90)
        ));
        menuByHotel.put("Spicy Treats", Arrays.asList(
                new FoodItem(1, "Dosa", 20),
                new FoodItem(2, "Parota", 15),
                new FoodItem(3, "Chicken Biryani", 180)
        ));
        menuByHotel.put("Curry Leaf", Arrays.asList(
                new FoodItem(1, "Idly", 5),
                new FoodItem(2, "Vada", 10),
                new FoodItem(3, "Pongal", 25)
        ));
        menuByHotel.put("Rice & Spice", Arrays.asList(
                new FoodItem(1, "Veg Biryani", 100),
                new FoodItem(2, "Mutton Curry", 220),
                new FoodItem(3, "Fish Fry", 150)
        ));
        menuByHotel.put("Biryani Palace", Arrays.asList(
                new FoodItem(1, "Chicken Curry", 120),
                new FoodItem(2, "Full Meals", 300),
                new FoodItem(3, "Masala Dosa", 40)
        ));
        menuByHotel.put("Hot & Tandoor", Arrays.asList(
                new FoodItem(1, "Tandoori Chicken", 200),
                new FoodItem(2, "Paneer Tikka", 150),
                new FoodItem(3, "Naan", 30)
        ));
        menuByHotel.put("Village Feast", Arrays.asList(
                new FoodItem(1, "Veg Meals", 120),
                new FoodItem(2, "Chicken 65", 150),
                new FoodItem(3, "Roti", 20)
        ));
        menuByHotel.put("Noodle Corner", Arrays.asList(
                new FoodItem(1, "Chicken Noodles", 120),
                new FoodItem(2, "Fried Rice", 110),
                new FoodItem(3, "Egg Noodles", 100)
        ));
        menuByHotel.put("Street Flavors", Arrays.asList(
                new FoodItem(1, "Kothu Parotta", 90),
                new FoodItem(2, "Gravy Chicken", 140),
                new FoodItem(3, "Omelette", 15)
        ));
        menuByHotel.put("Chicken Hub", Arrays.asList(
                new FoodItem(1, "Grill Chicken", 400),
                new FoodItem(2, "Chicken Wings", 250),
                new FoodItem(3, "Shawarma", 90)
        ));
    }

    static void askLocation() {
        System.out.print("Enter your location: ");
        String locationInput = sc.nextLine().trim();

        // Find match ignoring case
        String matchedLocation = null;
        for (String loc : hotelsByLocation.keySet()) {
            if (loc.equalsIgnoreCase(locationInput)) {
                matchedLocation = loc;
                break;
            }
        }

        if (matchedLocation == null) {
            System.out.println("Sorry, we only serve: " + hotelsByLocation.keySet());
            askLocation();
            return;
        }

        selectedLocation = matchedLocation;
        List<String> hotels = hotelsByLocation.get(selectedLocation);

        System.out.println("\nAvailable Hotels in " + selectedLocation + ":");
        for (int i = 0; i < hotels.size(); i++) {
            System.out.println((i + 1) + ". " + hotels.get(i) +
                    " (Delivery: " + deliveryTimes.get(hotels.get(i)) + " mins)");
        }
        System.out.print("Select hotel number: ");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice < 1 || choice > hotels.size()) {
            System.out.println("Invalid choice!");
            askLocation();
            return;
        }
        hotelName = hotels.get(choice - 1);
        deliveryTime = deliveryTimes.get(hotelName);
        System.out.println("Selected Hotel: " + hotelName + " (Delivery Time: " + deliveryTime + " mins)");
    }

    static void orderFood() {
        menu = menuByHotel.get(hotelName);
        while (true) {
            System.out.println("\n========== MENU - " + hotelName + " ==========");
            for (FoodItem item : menu) {
                System.out.printf("%d. %s - ₹%.2f\n", item.id, item.name, item.price);
            }
            System.out.println("0. Done ordering");
            System.out.print("Enter Food ID to order: ");
            int id = sc.nextInt();
            if (id == 0) break;
            FoodItem selected = null;
            for (FoodItem item : menu) {
                if (item.id == id) {
                    selected = item;
                    break;
                }
            }
            if (selected == null) {
                System.out.println("Invalid Food ID!");
                continue;
            }
            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();
            cart.add(new OrderItem(selected, qty));
            System.out.println(qty + " x " + selected.name + " added to cart.");
        }
    }

    static void cancelOrder() {
        if (cart.isEmpty()) {
            System.out.println("No orders to cancel!");
            return;
        }
        cart.clear();
        customer = null;
        System.out.println("Order cancelled successfully.");
    }

    static void takeCustomerDetails() {
        if (cart.isEmpty()) {
            System.out.println("No items in cart! Please order first.");
            return;
        }
        System.out.println("\nEnter Customer Details:");
        String name;
        while (true) {
            System.out.print("Name: ");
            name = sc.nextLine().trim();
            if (name.matches("[A-Za-z ]+")) break;
            else System.out.println("Name should contain only letters and spaces.");
        }
        String phone;
        while (true) {
            System.out.print("Phone (10 digits): ");
            phone = sc.nextLine().trim();
            if (phone.matches("\\d{10}")) break;
            else System.out.println("Phone should contain exactly 10 digits.");
        }
        String address;
        while (true) {
            System.out.print("Address (Must be " + selectedLocation + "): ");
            address = sc.nextLine().trim();
            if (address.equalsIgnoreCase(selectedLocation)) break;
            else System.out.println("Address must match your location.");
        }
        customer = new Customer(name, phone, address);
    }

    static void viewBill() {
        if (cart.isEmpty() || customer == null) {
            System.out.println("Order or customer details missing!");
            return;
        }
        double total = 0;
        System.out.println("\n==================== BILL ====================");
        for (OrderItem order : cart) {
            System.out.printf("%s x %d = ₹%.2f\n",
                    order.item.name, order.quantity, order.getTotalPrice());
            total += order.getTotalPrice();
        }
        System.out.printf("Total: ₹%.2f\n", total);
        System.out.println("10% Discount for online payment!");
        System.out.print("Payment method (1. Cash  2. Online): ");
        int payMethod = sc.nextInt();
        if (payMethod == 2) {
            double discount = total * 0.10;
            total -= discount;
            System.out.printf("Online Payment Discount: -₹%.2f\n", discount);
        }
        System.out.printf("Final Amount: ₹%.2f\n", total);
        System.out.println("\n============== Delivery Details ==============");
        System.out.println("Hotel: " + hotelName);
        System.out.println("Delivery Time: " + deliveryTime + " mins");
        System.out.println("Name: " + customer.name);
        System.out.println("Phone: " + customer.phone);
        System.out.println("Address: " + customer.address);
        System.out.println("Your order will be delivered in " + deliveryTime + " mins!");
        cart.clear();
        customer = null;
    }
}