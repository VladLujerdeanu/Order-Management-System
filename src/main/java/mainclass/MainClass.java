package mainclass;

import business.ClientLogic;
import business.OrderItemsLogic;
import business.OrderLogic;
import business.ProductLogic;
import database.query.dbOrder;
import model.Client;
import model.Ordert;
import model.Product;
import presentation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main class
 */
public class MainClass {
    public static ClientLogic clientLogic;
    public static OrderItemsLogic itemsLogic;
    public static OrderLogic orderLogic;
    public static ProductLogic productLogic;

    /**
     *
     * @param args The path of the file with the commands.
     */
    public static void main(String[] args) {
        clientLogic = new ClientLogic();
        itemsLogic = new OrderItemsLogic();
        orderLogic = new OrderLogic();
        productLogic = new ProductLogic();

        String file = null;
        if (args.length >= 1) {
            file = args[0];
        }
        processData(file);
    }

    /**
     * The method processes all the commands from the file given parameter.
     * @param path The path of the file with the commands.
     */
    public static void processData(String path){
        Scanner in = null;
        if (path != null) {
            try {
                File file = new File(path);
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            in = new Scanner(System.in);
        }
        System.out.print("> ");
        if (in != null) {
            while (in.hasNextLine()) {
                System.out.print("> ");
                String action = in.next();
                /// CASES FOR INSERT SYSTEM
                if (action.equalsIgnoreCase("insert")) {
                    String model = in.next().replace(":", "");

                    /// INSERT CLIENT
                    if (model.equalsIgnoreCase("client")) {
                        String firstName = in.next();
                        String lastName = in.next().replace(",", "");
                        String address = in.next();

                        if(clientLogic.insertClient(firstName, lastName, address) != -1){
                            System.out.print("Client inserted successfully!\n> ");
                        } else {
                            System.out.print("Client is already in the database\n> ");
                        }
                    }

                    /// INSERT PRODUCT
                    if (model.equalsIgnoreCase("product")) {
                        String name = in.next().replace(",", "");
                        int quantity = Integer.parseInt(in.next().replace(",", ""));
                        float price = in.nextFloat();

                        if(productLogic.insertProduct(name, quantity, price) > 0){
                            System.out.print("Product inserted successfully!\n> ");
                        } else {
                            System.out.print("Product is already in the database. Quantity updated.\n> ");
                        }
                    }

                    /// INVALID COMMAND
                    if (!model.equalsIgnoreCase("client") && !model.equalsIgnoreCase("product")) {
                        System.out.println("Invalid command!");
                        break;
                    }
                }

                /// CASES FOR DELETE SYSTEM
                if (action.equalsIgnoreCase("delete")) {
                    String model = in.next().replace(":", "");

                    /// DELETE CLIENT
                    if (model.equalsIgnoreCase("client")) {
                        String firstName = in.next();
                        String lastName = in.next().replace(",", "");
                        String address = in.next();

                        Client c = clientLogic.findByName(firstName, lastName);
                        if (c != null) {
                            clientLogic.delete(c.getId());
                            System.out.print("Client deleted successfully!\n> ");
                        } else {
                            System.out.print("No such client in the database.\n> ");
                        }
                    }

                    /// DELETE PRODUCT
                    if (model.equalsIgnoreCase("product")) {
                        String name = in.next();

                        Product p = productLogic.findByName(name);
                        if (p != null) {
                            productLogic.delete(p.getId());
                            System.out.print("Product deleted successfully!\n> ");
                        } else {
                            System.out.print("No such product in the database.\n> ");
                        }
                    }

                    /// INVALID COMMAND
                    if (!model.equalsIgnoreCase("client") && !model.equalsIgnoreCase("product")) {
                        System.out.println("Invalid command!");
                        break;
                    }
                }

                /// IMPLEMENTATION FOR ORDER SYSTEM
                action = action.replace(":", "");
                if (action.equalsIgnoreCase("order")) {
                    String firstName = in.next();
                    String lastName = in.next().replace(",", "");
                    String product = in.next().replace(",", "");
                    int quantity = in.nextInt();

                    Client c = clientLogic.findByName(firstName, lastName);
                    if(c == null){
                        System.out.print("No such client in the database.\n> ");
                    } else {
                        Product p = productLogic.findByName(product);
                        if(p == null) {
                            System.out.print("No such product in the database.\n> ");
                        } else {
                            if(p.getQuantity() >= quantity) {
                                int orderID = orderLogic.insertOrder(new Ordert(c.getId(), p.getPrice() * quantity));
                                itemsLogic.insertItems(orderID, p.getId(), quantity);
                                productLogic.update(p, new Product(p.getId(), p.getName(), p.getQuantity() - quantity, p.getPrice()));
                                Ordert o = (new dbOrder()).findById(orderID);
                                if(o != null) {
                                    Receipt receipt = new Receipt(o);
                                }
                            } else {
                                System.out.print("Not enough products in stock.\n> ");
                                OutOfStockReport rep = new OutOfStockReport(p);
                            }
                        }
                    }
                }

                /// CASES FOR REPORT SYSTEM
                if (action.equalsIgnoreCase("report")) {
                    String model = in.next();
                    if (model.equalsIgnoreCase("client")) {
                        ReportClient rc = new ReportClient();
                        System.out.print("Report created successfully!\n> ");
                    }
                    if (model.equalsIgnoreCase("order")) {
                        ReportOrder ro = new ReportOrder();
                        System.out.print("Report created successfully!\n> ");
                    }
                    if (model.equalsIgnoreCase("product")) {
                        ReportProduct rp = new ReportProduct();
                        System.out.print("Report created successfully!\n> ");
                    }
                    if (!model.equalsIgnoreCase("client") && !model.equalsIgnoreCase("order") && !model.equalsIgnoreCase("product")) {
                        System.out.println("Invalid command!");
                        break;
                    }
                }

                /// EXIT COMMAND
                if(action.equalsIgnoreCase("exit")){
                    System.out.println("Exit successful!");
                    break;
                }

                /// INVALID COMMANDS
                if (!action.equalsIgnoreCase("insert") && !action.equalsIgnoreCase("delete") &&
                        !action.equalsIgnoreCase("order") && !action.equalsIgnoreCase("report")) {
                    System.out.println("Invalid command!");
                    break;
                }
            }
            in.close();
        } else {
            System.out.println("Invalid Scanner!");
        }
    }
}
