public class TestModule1 {
    public static void main(String[] args) {
        VehicleManager manager = new VehicleManager();

        manager.registerVehicle("KA01AB1234", "Rahul Sharma", "Swift");
        manager.registerVehicle("MH02CD5678", "Priya Patel", "City");

        // Reject unregistered vehicle
        manager.createJobCard("DL03EF9999", "Brake service");

        // Valid job cards
        manager.createJobCard("KA01AB1234", "Oil Change");
        manager.createJobCard("MH02CD5678", "Wheel Alignment");

        manager.displayAllJobCards();
    }
}
