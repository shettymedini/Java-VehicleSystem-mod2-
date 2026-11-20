/** Runs status checks used to verify the Member 2 module. */
public class ServiceStatusDemo {
    public static void main(String[] args) {
        ServiceStatusManager manager = new ServiceStatusManager();
        try {
            manager.addService(new GeneralService("JOB-1001", "Oil and filter change"));
            if (manager.getService("job-1001").getStatus() != ServiceStatus.PENDING) {
                throw new IllegalStateException("New services should start as pending.");
            }
            manager.updateStatus("JOB-1001", "IN_PROGRESS");
            if (manager.getService("JOB-1001").getStatus() != ServiceStatus.IN_PROGRESS) {
                throw new IllegalStateException("In-progress update failed.");
            }
            manager.updateStatus("JOB-1001", "COMPLETED");
            try {
                manager.updateStatus("JOB-1001", "BROKEN_STATUS");
                throw new IllegalStateException("Invalid status should be rejected.");
            } catch (InvalidServiceException expected) {
                if (manager.getService("JOB-1001").getStatus() != ServiceStatus.COMPLETED) {
                    throw new IllegalStateException("Invalid update changed the saved status.");
                }
            }
            manager.displayStatus("JOB-1001");
        } catch (InvalidServiceException exception) {
            System.out.println("Service error: " + exception.getMessage());
        }
    }
}
