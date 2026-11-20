import java.util.ArrayList;

public class VehicleManager {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<JobCard> jobCards = new ArrayList<>();

    public boolean registerVehicle(String vehicleNumber, String ownerName, String model) {
        if (vehicleNumber == null || ownerName == null || model == null || vehicleNumber.trim().isEmpty()) {
            System.out.println("Error: Invalid vehicle details.");
            return false;
        }
        if (searchVehicle(vehicleNumber) != null) {
            System.out.println("Error: Vehicle " + vehicleNumber + " already registered.");
            return false;
        }
        vehicles.add(new Vehicle(vehicleNumber, ownerName, model));
        System.out.println("Registered: " + vehicleNumber);
        return true;
    }

    public Vehicle searchVehicle(String vehicleNumber) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleNumber().equalsIgnoreCase(vehicleNumber))
                return v;
        }
        return null;
    }

    public JobCard createJobCard(String vehicleNumber, String issueDescription) {
        if (searchVehicle(vehicleNumber) == null) {
            System.out.println("Error: Vehicle " + vehicleNumber + " not registered.");
            return null;
        }
        if (issueDescription == null || issueDescription.trim().isEmpty()) {
            System.out.println("Error: Issue cannot be empty.");
            return null;
        }
        JobCard jc = new JobCard(vehicleNumber, issueDescription);
        jobCards.add(jc);
        System.out.println("Job Card created! ID: " + jc.getJobId());
        return jc;
    }

    public void displayAllJobCards() {
        for (JobCard jc : jobCards)
            jc.displayJobCard();
    }
}
