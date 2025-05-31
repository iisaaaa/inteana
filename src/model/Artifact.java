package model;

public class Artifact extends Deliverable {
    private String artifactType;

    public Artifact(String title, DevelopmentPhase phase, String artifactType) {
        super(title, phase);
        this.artifactType = artifactType;
    }

    @Override
    protected String generateId() {
        return "ART-" + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return super.toString() + "[Artifact Type: " + artifactType + "]";
    }

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

}
