/** Base type for service status records. */
public abstract class Service {
    private final String jobId;
    private final String description;
    private ServiceStatus status = ServiceStatus.PENDING;

    protected Service(String jobId, String description) {
        if (jobId == null || jobId.trim().isEmpty()) throw new IllegalArgumentException("Job ID cannot be empty.");
        if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Description cannot be empty.");
        this.jobId = jobId.trim().toUpperCase();
        this.description = description.trim();
    }

    public String getJobId() { return jobId; }
    public String getDescription() { return description; }
    public ServiceStatus getStatus() { return status; }
    public void updateStatus(ServiceStatus status) throws InvalidServiceException {
        if (status == null) throw new InvalidServiceException("Status is required.");
        this.status = status;
    }

    public abstract String getServiceType();
}
