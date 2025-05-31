package model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String identifier;
    private String name;
    private String type;
    private String semester;
    private String shortDescription;
    private List<String> keywords;
    private String statementUrl;
    private String relatedProjectId;
    private List<String> beneficiaryCompanies;
    private List<Result> results;

    public Project(String identifier, String name, String type, String semester, String shortDescription,
            List<String> keywords, String statementUrl, List<String> beneficiaryCompanies) {
        this.identifier = identifier;
        this.name = name;
        this.type = type;
        this.semester = semester;
        this.shortDescription = shortDescription;
        this.keywords = keywords;
        this.statementUrl = statementUrl;
        this.beneficiaryCompanies = beneficiaryCompanies;
        this.results = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Project[" + "identifier='" + identifier + "- name='" + name + "- type='" + type + "- semester='"+ semester + "- resultsCount=" + results.size() + ']';
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getStatementUrl() {
        return statementUrl;
    }

    public void setStatementUrl(String statementUrl) {
        this.statementUrl = statementUrl;
    }

    public String getRelatedProjectId() {
        return relatedProjectId;
    }

    public void setRelatedProjectId(String relatedProjectId) {
        this.relatedProjectId = relatedProjectId;
    }

    public List<String> getBeneficiaryCompanies() {
        return beneficiaryCompanies;
    }

    public void setBeneficiaryCompanies(List<String> beneficiaryCompanies) {
        this.beneficiaryCompanies = beneficiaryCompanies;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void addResult(Result result) {
        this.results.add(result);
    }

    private String courseCode;

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

}
