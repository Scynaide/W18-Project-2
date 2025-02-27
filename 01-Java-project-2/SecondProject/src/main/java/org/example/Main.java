package org.example;


import java.util.List;
import java.util.Scanner;


public class Main {
    private static User loggedInUser = null;
    private static final Scanner scanner;
    private static OrderManagement orderManagement;
    private static ProductManagement productManagement;
    private static UserManagement userManagement;
    private static SearchAndFilter searchAndFilter;
    private static ShoppingCart shoppingCart;
    private static PaymentGateway paymentGateway;
    private static OrderTracking orderTracking;

            static{
                scanner = new Scanner(System.in);
                orderManagement = new OrderManagement();
                productManagement = new ProductManagement();
                userManagement = new UserManagement();
                searchAndFilter = new SearchAndFilter();
                shoppingCart = new ShoppingCart();
                paymentGateway = new PaymentGateway();
                orderTracking = new OrderTracking();
            }

            public Main(){

            }

            public static void main(String[] args) {
                showAllProducts();

                while(true) {
                    while(loggedInUser == null) {
                        showGuestMenu();
                    }
                    if (loggedInUser.isAdmin(loggedInUser)) {
                        showAdminMenu();
                    } else {
                        showCustomerMenu();
                    }
                    return;
                }

            }

            private static void showAllProducts() {
                List<Product> allProducts = productManagement.getProducts();
                System.out.println("All products: ");

                for(Product product : allProducts) {
                    System.out.println(product.getCategory());
                }
            }

            private static void showAdminMenu() {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add Product");
                System.out.println("2. Edit Product");
                System.out.println("3. Delete Product");
                System.out.println("4. View All Orders");
                System.out.println("5. Update Order Status");
                System.out.println("6. Logout");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1 -> addProduct();
                        case 2 -> editProduct();
                        case 3 -> deleteProduct();
                        case 4 -> displayAllOrders();
                        case 5 -> updateOrderStatus();
                        case 6 -> logout();
                        case 7 -> exit();
                        default -> System.out.println("Invalid choice. Please select a valid option.");
                    }
                } catch (NumberFormatException var1) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                }

            }

            private static void addProduct() {
                try {
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter product category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter product quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    Product newProduct = new Product(productManagement.generateProductId(), name, description, price, category, quantity);
                    productManagement.addProduct(newProduct);
                    System.out.println("Product added successfully!");
                } catch (NumberFormatException var7) {
                    System.out.println("Error: Invalid input. Please enter valid data.");
                } catch (Exception var8) {
                    System.out.println("Error adding product. Please try again.");
                }

            }

            private static void editProduct() {
                try {
                    System.out.print("Enter product ID to edit: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    Product existingProduct = productManagement.getProductById(productId);
                    if (existingProduct != null) {
                        System.out.print("Enter new product name : ");
                        String name = scanner.nextLine();
                        existingProduct.setName(name);
                        System.out.print("Enter new product description : ");
                        String description = scanner.nextLine();
                        existingProduct.setDescription(description);
                        System.out.print("Enter new product price : ");
                        double price = Double.parseDouble(scanner.nextLine());
                        existingProduct.setPrice(price);
                        System.out.print("Enter new product category : ");
                        String category = scanner.nextLine();
                        existingProduct.setCategory(category);
                        System.out.print("Enter new product quantity : ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        existingProduct.setQuantity(quantity);
                        productManagement.editProduct(existingProduct);
                        System.out.println("Product updated successfully!");
                    } else {
                        System.out.println("Product not found.");
                    }
                } catch (NumberFormatException var8) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                } catch (Exception var9) {
                    System.out.println("Error editing product. Please try again.");
                }

            }

            private static void deleteProduct() {
                try {
                    System.out.print("Enter product ID to delete: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    if (productManagement.deleteProduct(productId)) {
                        System.out.println("Product deleted successfully!");
                    } else {
                        System.out.println("Product not found.");
                    }
                } catch (NumberFormatException var1) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                } catch (Exception var2) {
                    System.out.println("Error deleting product. Please try again.");
                }

            }

            private static void showCustomerMenu() {
                System.out.println("\nCustomer Menu:");
                System.out.println("1. Place Order");
                System.out.println("2. View Order History");
                System.out.println("3. Cancel Order");
                System.out.println("4. Search Products");
                System.out.println("5. Filter Products");
                System.out.println("6. Add Product to Cart");
                System.out.println("7. Update Cart");
                System.out.println("8. Remove Product from Cart");
                System.out.println("9. Make Payment");
                System.out.println("10. Track Order");
                System.out.println("11. Logout");
                System.out.println("12. Exit");
                System.out.print("Enter your choice: ");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1 -> placeOrder();
                        case 2 -> viewOrderHistory();
                        case 3 -> cancelOrder();
                        case 4 -> searchProducts();
                        case 5 -> filterProducts();
                        case 6 -> addProductToCart();
                        case 7 -> updateCart();
                        case 8 -> removeProductFromCart();
                        case 9 -> makePayment();
                        case 10 -> trackOrder();
                        case 11 -> logout();
                        case 12 -> exit();
                        default -> System.out.println("Invalid choice. Please select a valid option.");
                    }
                } catch (NumberFormatException var1) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                }

            }

            private static void addProductToCart() {
                try {
                    System.out.print("Enter product ID to add to cart: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    Product product = productManagement.getProductById(productId);
                    if (product != null && product.getQuantity() >= quantity) {
                        shoppingCart.addProductToCart(loggedInUser.getId(), product, quantity);
                        System.out.println("Product added to cart successfully!");
                    } else {
                        System.out.println("Insufficient stock or product not found.");
                    }
                } catch (Exception var3) {
                    System.out.println("Error adding product to cart. Please try again.");
                }

            }

            private static void updateCart() {
                try {
                    System.out.print("Enter product ID to update quantity in cart: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    if (shoppingCart.updateProductQuantity(loggedInUser.getId(), productId, quantity)) {
                        System.out.println("Cart updated successfully!");
                    } else {
                        System.out.println("Product not found in cart or invalid quantity.");
                    }
                } catch (Exception var2) {
                    System.out.println("Error updating cart. Please try again.");
                }

            }

            private static void removeProductFromCart() {
                try {
                    System.out.print("Enter product ID to remove from cart: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    if (shoppingCart.removeProductFromCart(productId)) {
                        System.out.println("Product removed from cart successfully!");
                    } else {
                        System.out.println("Product not found in cart.");
                    }
                } catch (Exception var1) {
                    System.out.println("Error removing product from cart. Please try again.");
                }

            }

            private static void viewOrderHistory() {
                try {
                    int userId = loggedInUser.getId();
                    List<Order> userOrders = orderManagement.getOrdersByUserId(userId);
                    System.out.println("Order History:");

                    for(Order order : userOrders) {
                        System.out.println(order);
                    }
                } catch (Exception var4) {
                    System.out.println("Error viewing order history. Please try again.");
                }

            }

            private static void placeOrder() {
                try {
                    System.out.print("Enter product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter delivery address: ");
                    String address = scanner.nextLine();
                    Product product = productManagement.getProductById(productId);
                    if (product != null && product.getQuantity() >= quantity) {
                        Order newOrder = new Order(orderManagement.generateOrderId(), loggedInUser.getId(), productId, quantity, product.getPrice() * (double)quantity, "Pending", address);
                        orderManagement.placeOrder(newOrder);
                        System.out.println("Order placed successfully!");
                    } else {
                        System.out.println("Insufficient stock or product not found.");
                    }
                } catch (Exception var5) {
                    System.out.println("Error placing order. Please try again.");
                }

            }

            private static void cancelOrder() {
                try {
                    System.out.print("Enter order ID to cancel: ");
                    int orderId = Integer.parseInt(scanner.nextLine());
                    if (orderManagement.cancelOrder(orderId, loggedInUser.getId())) {
                        System.out.println("Order cancelled successfully!");
                    } else {
                        System.out.println("Order not found or cannot be cancelled.");
                    }
                } catch (Exception var1) {
                    System.out.println("Error cancelling order. Please try again.");
                }

            }

            private static void makePayment() {
                try {
                    System.out.print("Enter order ID to make payment: ");
                    int orderId = Integer.parseInt(scanner.nextLine());
                    Order order = orderManagement.getOrderByOrderId(orderId);
                    if (order != null && order.getStatus().equals("Pending")) {
                        System.out.print("Enter payment amount: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        Payment payment = new Payment(paymentGateway.generatePaymentId(), orderId, amount, "Success");
                        paymentGateway.processPayment(payment);
                        order.setStatus("Paid");
                        orderManagement.updateOrder(order);
                        System.out.println("Payment successful!");
                    } else {
                        System.out.println("Order not found or already paid.");
                    }
                } catch (Exception var5) {
                    System.out.println("Error making payment. Please try again.");
                }

            }

            private static void trackOrder() {
                try {
                    System.out.print("Enter order ID to track: ");
                    int orderId = Integer.parseInt(scanner.nextLine());
                    Order order = orderManagement.getOrderByOrderId(orderId);
                    if (order != null) {
                        String status = orderTracking.getOrderStatus(orderId);
                        System.out.println("Order status: " + status);
                    } else {
                        System.out.println("Order not found.");
                    }
                } catch (Exception var3) {
                    System.out.println("Error tracking order. Please try again.");
                }

            }

            private static void searchProducts() {
                System.out.print("Enter search keyword: ");
                String keyword = scanner.nextLine();
                List<Product> results = searchAndFilter.searchProducts(keyword);
                System.out.println("Search Results:");

                for(Product product : results) {
                    System.out.println(product);
                }

            }

            private static void filterProducts() {
                System.out.print("Enter min amount : ");
                double min = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter max amount : ");
                double max = Double.parseDouble(scanner.nextLine());
                List<Product> results = searchAndFilter.filterProducts(min, max);
                System.out.println("Filtered Results:");

                for(Product product : results) {
                    System.out.println(product);
                }

            }

            private static void updateOrderStatus() {
                try {
                    System.out.print("Enter order ID to update: ");
                    int orderId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new status: ");
                    String status = scanner.nextLine();
                    Order order = orderManagement.getOrderByOrderId(orderId);
                    if (order != null) {
                        order.setStatus(status);
                        orderManagement.updateOrder(order);
                        System.out.println("Order status updated successfully!");
                    } else {
                        System.out.println("Order not found.");
                    }
                } catch (Exception var3) {
                    System.out.println("Error updating order status. Please try again.");
                }

            }

            private static void displayAllOrders() {
                List<Order> allOrders = orderManagement.getAllOrders();
                System.out.println("All Orders:");

                for(Order order : allOrders) {
                    System.out.println(order);
                }

            }

            private static void logout() {
                loggedInUser = null;
                System.out.println("Logged out successfully!");
            }

            private static void exit() {
                System.out.println("Exiting....");
                System.exit(0);
            }

            private static void showGuestMenu() {
                System.out.println("\nGuest Menu:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> registerUser();
                    case 2 -> loginUser();
                    case 3 -> exit();
                    default -> System.out.println("Invalid choice. Please select a valid option.");
                }

            }

            private static void registerUser() {
                try {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    User newUser = new User(userManagement.generateUserId(), username, password, email);
                    if (userManagement.registerUser(newUser)) {
                        System.out.println("User registered successfully!");
                    } else {
                        System.out.println("Registration failed. Username or email may already be in use.");
                    }
                } catch (Exception var4) {
                    System.out.println("Error registering user. Please try again.");
                }

            }

            private static void loginUser() {
                try {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    loggedInUser = userManagement.login(username, password);
                    if (loggedInUser != null) {
                        System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
                    } else {
                        System.out.println("Login failed. Invalid username or password.");
                    }
                } catch (Exception var2) {
                    System.out.println("Error logging in. Please try again.");
                }
            }






        }