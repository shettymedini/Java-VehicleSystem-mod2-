/** Concrete example of a general vehicle service. */
public class GeneralService extends Service {
    public GeneralService(String jobId, String description) {
        super(jobId, description);
    }

    @Override
    public String getServiceType() { return "General service"; }
}
