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
    static List<FoodItem> menu = new ArrayList<>();
    static List<OrderItem> cart = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Customer customer = null;
    static String selectedLocation = "";
    static String hotelName = "";
    static int deliveryTime = 0;
    public static void main(String[] args) {
        System.out.println("              Welcome to EpicEra's Online Food Delivery \n");
        System.out.println("Locations we deliver to:\n--> Trichy\n--> Thanjavor\n--> Viralimalai\n--> Manapparai");
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
    static void askLocation() {
        System.out.print("Enter your location: ");
        String location = sc.nextLine().trim();
        if (location.equalsIgnoreCase("Trichy")) {
            selectedLocation = "Trichy";
            hotelName = "The Royal Food";
            deliveryTime = 20;
        } else if (location.equalsIgnoreCase("Thanjavor")) {
            selectedLocation = "Thanjavor";
            hotelName = "Spicy Treats";
            deliveryTime = 15;
        } else if (location.equalsIgnoreCase("Viralimalai")) {
            selectedLocation = "Viralimalai";
            hotelName = "Biryani Palace";
            deliveryTime = 25;
        } else if (location.equalsIgnoreCase("Manapparai")) {
            selectedLocation = "Manapparai";
            hotelName = "Noodle Corner";
            deliveryTime = 18;
        } else {
            System.out.println("Sorry, we only serve Trichy, Thanjavor, Viralimalai, and Manapparai.");
            askLocation();
            return;
        }
        System.out.println("Hotel Available: " + hotelName + " (Delivery Time: " + deliveryTime + " mins)");
    }
    static void orderFood() {
        menu.clear();
        if (selectedLocation.equals("Trichy")) {
            menu.add(new FoodItem(1, "Pizza", 250));
            menu.add(new FoodItem(2, "Coke", 20));
            menu.add(new FoodItem(3, "Grill Chicken", 400));
            menu.add(new FoodItem(4, "Shawarma", 90));
            menu.add(new FoodItem(5, "Burger", 150));
        } else if (selectedLocation.equals("Thanjavor")) {
            menu.add(new FoodItem(1, "Dosa", 20));
            menu.add(new FoodItem(2, "Parota", 15));
            menu.add(new FoodItem(3, "Idly", 5));
            menu.add(new FoodItem(4, "Chicken Biryani", 180));
            menu.add(new FoodItem(5, "Plain Briyani", 90));
        } else if (selectedLocation.equals("Viralimalai")) {
            menu.add(new FoodItem(1, "Parotta", 15));
            menu.add(new FoodItem(2, "Chicken Curry", 120));
            menu.add(new FoodItem(3, "Full Meals", 300));
            menu.add(new FoodItem(4, "Paneer Tikka", 150));
            menu.add(new FoodItem(5, "Masala Dosa", 40));
        } else if (selectedLocation.equals("Manapparai")) {
            menu.add(new FoodItem(1, "Chicken Noodles", 120));
            menu.add(new FoodItem(2, "Fish Fry", 120));
            menu.add(new FoodItem(3, "Andhara Meals", 250));
            menu.add(new FoodItem(4, "Tandoori Chicken", 200));
            menu.add(new FoodItem(5, "Fried Rice", 110));
        }
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
                System.out.println(" Invalid Food ID!");
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
            if (name.matches("[A-Za-z ]+")) {
                break;
            } else {
                System.out.println(" Name should contain only letters and spaces. Try again.");
            }
        }
        String phone;
        while (true) {
            System.out.print("Phone (10 digits): ");
            phone = sc.nextLine().trim();
            if (phone.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("Phone should contain exactly 10 digits. Try again.");
            }
        }
        String address;
        while (true) {
            System.out.print("Address (Must be " + selectedLocation + "): ");
            address = sc.nextLine().trim();
            if (address.equalsIgnoreCase(selectedLocation)) {
                break;
            } else {
                System.out.println(" Address must match your location.");
            }
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