import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

// Custom exception for invalid job cards
class InvalidJobCardException extends Exception {
    public InvalidJobCardException(String message) {
        super(message);
    }
}

// Custom exception for invalid spare parts
class InvalidPartException extends Exception {
    public InvalidPartException(String message) {
        super(message);
    }
}

// Abstract class demonstrating abstraction
abstract class Service {
    private double labourCost;

    public Service(double labourCost) {
        this.labourCost = labourCost;
    }

    public double getLabourCost() {
        return labourCost;
    }

    public abstract String getServiceType();
}

// General service class demonstrating inheritance
class GeneralService extends Service {
    public GeneralService(double labourCost) {
        super(labourCost);
    }

    @Override
    public String getServiceType() {
        return "General Service";
    }
}

// Repair service class demonstrating inheritance
class RepairService extends Service {
    public RepairService(double labourCost) {
        super(labourCost);
    }

    @Override
    public String getServiceType() {
        return "Repair Service";
    }
}

// Job card class
class ServiceJob {
    private int jobId;
    private String customerName;
    private String vehicleNumber;
    private String serviceType;
    private String status;
    private double partsCost;
    private double labourCost;

    public ServiceJob(int jobId, String customerName,
                     String vehicleNumber, String serviceType,
                     double labourCost) {
        this.jobId = jobId;
        this.customerName = customerName;
        this.vehicleNumber = vehicleNumber;
        this.serviceType = serviceType;
        this.status = "WAITING";
        this.partsCost = 0;
        this.labourCost = labourCost;
    }

    public int getJobId() {
        return jobId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStatus() {
        return status;
    }

    public double getPartsCost() {
        return partsCost;
    }

    public double getLabourCost() {
        return labourCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addPartsCost(double cost) {
        partsCost += cost;
    }

    public double getTotalBill() {
        return partsCost + labourCost;
    }

    public void displayJob() {
        System.out.println("-----------------------------------");
        System.out.println("Job ID       : " + jobId);
        System.out.println("Customer     : " + customerName);
        System.out.println("Vehicle      : " + vehicleNumber);
        System.out.println("Service Type : " + serviceType);
        System.out.println("Status       : " + status);
        System.out.println("Parts Cost   : Rs. " + partsCost);
        System.out.println("Labour Cost  : Rs. " + labourCost);
        System.out.println("Total Bill   : Rs. " + getTotalBill());
        System.out.println("-----------------------------------");
    }
}

// Spare part class
class SparePart {
    private String name;
    private int quantity;
    private double price;

    public SparePart(String name, int quantity, double price)
            throws InvalidPartException {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidPartException("Spare part name cannot be empty.");
        }

        if (quantity <= 0) {
            throw new InvalidPartException("Quantity must be greater than zero.");
        }

        if (price <= 0) {
            throw new InvalidPartException("Price must be greater than zero.");
        }

        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public double getTotalCost() {
        return quantity * price;
    }

    public void displayPart() {
        System.out.println("Part: " + name);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price per unit: Rs. " + price);
        System.out.println("Total: Rs. " + getTotalCost());
    }
}

// Custom linked-list queue for waiting jobs
class JobQueue {

    // Node of the linked list
    private class Node {
        ServiceJob job;
        Node next;

        Node(ServiceJob job) {
            this.job = job;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;

    // Adds a job to the end of the queue
    public void enqueue(ServiceJob job) {
        Node newNode = new Node(job);

        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    // Removes and returns the first job in the queue
    public ServiceJob dequeue() {
        if (front == null) {
            return null;
        }

        ServiceJob job = front.job;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return job;
    }

    // Removes a particular job from the queue
    public void removeJob(int jobId) {
        Node current = front;
        Node previous = null;

        while (current != null) {
            if (current.job.getJobId() == jobId) {

                if (previous == null) {
                    front = current.next;
                } else {
                    previous.next = current.next;
                }

                if (current == rear) {
                    rear = previous;
                }

                if (front == null) {
                    rear = null;
                }

                return;
            }

            previous = current;
            current = current.next;
        }
    }

    // Displays all jobs currently waiting
    public void displayQueue() {
        if (front == null) {
            System.out.println("Waiting queue is empty.");
            return;
        }

        Node current = front;

        System.out.println("\n===== WAITING QUEUE =====");

        while (current != null) {
            System.out.println(
                "Job ID: " + current.job.getJobId()
                + " | Vehicle: " + current.job.getVehicleNumber()
            );

            current = current.next;
        }
    }
}

// Main program
public class VehicleServiceCenter {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<ServiceJob> jobs = new ArrayList<>();
    static HashMap<Integer, ServiceJob> jobMap = new HashMap<>();
    static HashSet<String> vehicleNumbers = new HashSet<>();

    static JobQueue waitingQueue = new JobQueue();

    static int nextId = 1;

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\nVEHICLE SERVICE CENTER");
            System.out.println("1. Create Job Card");
            System.out.println("2. View All Job Cards");
            System.out.println("3. Update Service Status");
            System.out.println("4. Add Spare Parts");
            System.out.println("5. Generate Bill");
            System.out.println("6. View Service History");
            System.out.println("7. View Waiting Queue");
            System.out.println("8. Process Next Waiting Job");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    createJob();
                    break;

                case 2:
                    viewAllJobs();
                    break;

                case 3:
                    updateStatus();
                    break;

                case 4:
                    addSpareParts();
                    break;

                case 5:
                    generateBill();
                    break;

                case 6:
                    serviceHistory();
                    break;

                case 7:
                    waitingQueue.displayQueue();
                    break;

                case 8:
                    processNextJob();
                    break;

                case 0:
                    System.out.println("Thank you for using our Vehicle Service!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        sc.close();
    }

    // Creates a new service job
    static void createJob() {

        System.out.println("\n===== CREATE JOB CARD =====");

        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        System.out.print("Enter vehicle number: ");
        String vehicleNumber = sc.nextLine().toUpperCase();

        System.out.println("Select service type:");
        System.out.println("1. General Service");
        System.out.println("2. Repair Service");
        System.out.print("Enter choice: ");

        int serviceChoice = readInt();

        Service service;

        if (serviceChoice == 1) {
            service = new GeneralService(500);
        } else if (serviceChoice == 2) {
            service = new RepairService(1000);
        } else {
            System.out.println("Invalid service type.");
            return;
        }

        ServiceJob job = new ServiceJob(
            nextId,
            customerName,
            vehicleNumber,
            service.getServiceType(),
            service.getLabourCost()
        );

        jobs.add(job);
        jobMap.put(nextId, job);
        vehicleNumbers.add(vehicleNumber);

        // Every new job enters the waiting queue
        waitingQueue.enqueue(job);

        System.out.println("\nJob card created successfully!");
        System.out.println("Job ID: " + nextId);
        System.out.println("Vehicle: " + vehicleNumber);
        System.out.println("Status: WAITING");

        nextId++;
    }

    // Displays all job cards
    static void viewAllJobs() {

        if (jobs.isEmpty()) {
            System.out.println("No job cards available.");
            return;
        }

        System.out.println("\n===== ALL JOB CARDS =====");

        for (ServiceJob job : jobs) {
            job.displayJob();
        }
    }

    // Updates the status of a job
    static void updateStatus() {

        try {
            System.out.print("Enter Job ID: ");
            int id = readInt();

            ServiceJob job = findJob(id);

            System.out.println("Current Status: " + job.getStatus());
            System.out.println("1. WAITING");
            System.out.println("2. IN_PROGRESS");
            System.out.println("3. COMPLETED");
            System.out.print("Select new status: ");

            int choice = readInt();

            String newStatus;

            // Validate the selection before changing the queue
            switch (choice) {
                case 1:
                    newStatus = "WAITING";
                    break;

                case 2:
                    newStatus = "IN_PROGRESS";
                    break;

                case 3:
                    newStatus = "COMPLETED";
                    break;

                default:
                    System.out.println("Invalid status choice.");
                    return;
            }

            // Remove job from queue before changing its status
            waitingQueue.removeJob(id);

            job.setStatus(newStatus);

            // Only WAITING jobs should remain in the waiting queue
            if (newStatus.equals("WAITING")) {
                waitingQueue.enqueue(job);
            }

            System.out.println("Status updated successfully to " + newStatus);

        } catch (InvalidJobCardException e) {
            System.out.println(e.getMessage());
        }
    }

    // Processes the first job in the waiting queue
    static void processNextJob() {

        ServiceJob job = waitingQueue.dequeue();

        if (job == null) {
            System.out.println("No jobs are currently waiting.");
            return;
        }

        // Dequeue removes the job from the waiting queue
        job.setStatus("IN_PROGRESS");

        System.out.println("\n===== JOB PROCESSED =====");
        System.out.println("Job ID: " + job.getJobId());
        System.out.println("Vehicle: " + job.getVehicleNumber());
        System.out.println("Customer: " + job.getCustomerName());
        System.out.println("Status changed to IN_PROGRESS.");
    }

    // Adds spare parts to an existing job
    static void addSpareParts() {

        try {
            System.out.print("Enter Job ID: ");
            int id = readInt();

            ServiceJob job = findJob(id);

            System.out.print("Enter spare part name: ");
            String partName = sc.nextLine();

            System.out.print("Enter quantity: ");
            int quantity = readInt();

            System.out.print("Enter price per unit: ");
            double price = readDouble();

            SparePart part = new SparePart(partName, quantity, price);

            part.displayPart();

            job.addPartsCost(part.getTotalCost());

            System.out.println("Spare parts added to job successfully!");

        } catch (InvalidJobCardException | InvalidPartException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Generates the bill for a job
    static void generateBill() {

        try {
            System.out.print("Enter Job ID: ");
            int id = readInt();

            ServiceJob job = findJob(id);

            System.out.println("\n===== SERVICE BILL =====");
            System.out.println("Job ID: " + job.getJobId());
            System.out.println("Customer: " + job.getCustomerName());
            System.out.println("Vehicle: " + job.getVehicleNumber());
            System.out.println("Service: " + job.getServiceType());
            System.out.println("Parts Cost: Rs. " + job.getPartsCost());
            System.out.println("Labour Cost: Rs. " + job.getLabourCost());
            System.out.println("-----------------------------");
            System.out.println("TOTAL BILL: Rs. " + job.getTotalBill());
            System.out.println("-----------------------------");

        } catch (InvalidJobCardException e) {
            System.out.println(e.getMessage());
        }
    }

    // Displays service history for a vehicle
    static void serviceHistory() {

        System.out.print("Enter vehicle number: ");
        String vehicleNumber = sc.nextLine().toUpperCase();

        boolean found = false;

        System.out.println("\n===== SERVICE HISTORY =====");

        for (ServiceJob job : jobs) {

            if (job.getVehicleNumber().equalsIgnoreCase(vehicleNumber)) {
                job.displayJob();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No service history found for this vehicle.");
        }
    }

    // Searches for a job using its job ID
    static ServiceJob findJob(int id) throws InvalidJobCardException {

        ServiceJob job = jobMap.get(id);

        if (job == null) {
            throw new InvalidJobCardException(
                "Job card with ID " + id + " does not exist."
            );
        }

        return job;
    }

    // Reads an integer safely
    static int readInt() {

        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }

        int value = sc.nextInt();
        sc.nextLine();

        return value;
    }

    // Reads a decimal value safely
    static double readDouble() {

        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid amount: ");
            sc.next();
        }

        double value = sc.nextDouble();
        sc.nextLine();

        return value;
    }
}