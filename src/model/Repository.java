package model;

import exceptions.InvalidUrlException;

public class Repository extends Deliverable {
    private String url;
    private int fileCount;

    public Repository(String title, DevelopmentPhase phase, String url, int fileCount) throws InvalidUrlException {
        super(title, phase);
        if (!ValidateUrl.isValidGitHubUrl(url)) {
            throw new InvalidUrlException("Invalid GitHub repository url: " + url);
        }
        this.url = url;
        this.fileCount = fileCount;
    }

    @Override
    protected String generateId() {
        return "REPO-" + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return super.toString() + "[Repository url: " + url + "- files: " + fileCount + "]";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }
}
