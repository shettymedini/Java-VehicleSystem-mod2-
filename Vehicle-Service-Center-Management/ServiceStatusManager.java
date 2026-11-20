import java.util.HashMap;
import java.util.Map;

/** Stores service records by Job ID for quick lookup. */
public class ServiceStatusManager {
    private final Map<String, Service> services = new HashMap<>();

    public void addService(Service service) throws InvalidServiceException {
        if (service == null) throw new InvalidServiceException("Service record is required.");
        String jobId = service.getJobId().trim().toUpperCase();
        if (services.containsKey(jobId)) throw new InvalidServiceException("Duplicate Job ID: " + jobId);
        services.put(jobId, service);
    }

    public Service getService(String jobId) throws InvalidServiceException {
        if (jobId == null || jobId.trim().isEmpty()) throw new InvalidServiceException("Job ID cannot be empty.");
        Service service = services.get(jobId.trim().toUpperCase());
        if (service == null) throw new InvalidServiceException("Job ID not found: " + jobId);
        return service;
    }

    public void updateStatus(String jobId, String statusText) throws InvalidServiceException {
        Service service = getService(jobId);
        service.updateStatus(ServiceStatus.fromInput(statusText));
    }

    public void displayStatus(String jobId) throws InvalidServiceException {
        Service service = getService(jobId);
        System.out.println("Job ID: " + service.getJobId());
        System.out.println("Service: " + service.getDescription());
        System.out.println("Type: " + service.getServiceType());
        System.out.println("Status: " + service.getStatus());
    }
}
