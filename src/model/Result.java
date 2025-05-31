package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Result {
    private String identifier;
    private String name;
    private String description;
    private String groupCode;
    private LocalDate submissionDate;
    private List<Deliverable> deliverables;

    public Result(String projectId, int resultIndex, String groupCode, String name, String description, LocalDate date) {
        this.identifier = generateResultId(projectId, resultIndex, groupCode);
        this.groupCode = groupCode;
        this.name = name;
        this.description = description;
        this.submissionDate = date;
        this.deliverables = new ArrayList<>();
    }

    private String generateResultId(String projectId, int resultIndex, String groupCode) {
        return projectId + "-R" + resultIndex + "-" + groupCode;
    }

    @Override
    public String toString() {
        return "Result[" + "identifier='" + identifier + "- name='" + name + "- description='" + description + "- groupCode='" + groupCode + "- submissionDate=" + submissionDate + ']';
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public List<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    public void addDeliverable(Deliverable deliverable) {
        this.deliverables.add(deliverable);
    }

    public void removeDeliverableById(String deliverableId) {
        for (Deliverable d : deliverables) {
            if (d.getId().equals(deliverableId)) {
                d.setDeleted(true);
                break;
            }
        }
    }

    public List<Deliverable> getActiveDeliverables() {
        List<Deliverable> active = new ArrayList<>();
        for (Deliverable d : deliverables) {
            if (!d.isDeleted()) {
                active.add(d);
            }
        }
        return active;
    }
}