package model;

import exceptions.InvalidUrlException;

public class Document extends Deliverable {
    private String url;
    private String cloudService;

    public Document(String title, DevelopmentPhase phase, String url, String cloudService) throws InvalidUrlException {
        super(title, phase);
        if (!ValidateUrl.isValidCloudUrl(url)) {
            throw new InvalidUrlException("Invalid cloud document URL: " + url);
        }
        this.url = url;
        this.cloudService = cloudService;
    }

    @Override
    protected String generateId() {
        return "DOC-" + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return super.toString() + "[Document URL: " + url + "]";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCloudService() {
        return cloudService;
    }

    public void setCloudService(String cloudService) {
        this.cloudService = cloudService;
    }  
}