package model;

public abstract class Deliverable {
    private String id;
    private String title;
    private DevelopmentPhase phase;
    private boolean deleted;

    public Deliverable(String title, DevelopmentPhase phase) {
        this.title = title;
        this.phase = phase;
        this.id = generateId();
        this.deleted = false;
    }

    protected abstract String generateId();

    @Override
    public String toString() {
        return "Deliverable[ " + "id='" + id + ", title='" + title + ", phase=" + phase + ", deleted=" + deleted + "] ";
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DevelopmentPhase getPhase() {
        return phase;
    }

    public void setPhase(DevelopmentPhase phase) {
        this.phase = phase;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}