/** Status values supported by a service job. */
public enum ServiceStatus {
    PENDING("Pending"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed");

    private String displayName;
    ServiceStatus(String displayName) { this.displayName = displayName; }
    @Override public String toString() { return displayName; }

    public static ServiceStatus fromInput(String input) throws InvalidServiceException {
        if (input == null || input.trim().isEmpty()) throw new InvalidServiceException("Status cannot be empty.");
        try {
            return valueOf(input.trim().replace(' ', '_').toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidServiceException("Choose PENDING, IN_PROGRESS, or COMPLETED.");
        }
    }
}
