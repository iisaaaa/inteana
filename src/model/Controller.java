package model;

import exceptions.*;
import java.io.*;
import java.util.*;

public class Controller implements Serializable {
    private List<Course> courseList;
    private List<Professor> professorList;
    private List<Project> projectList;

    public Controller() {
        this.courseList = new ArrayList<>();
        this.professorList = new ArrayList<>();
        this.projectList = new ArrayList<>();
    }

    public void loadDemoData() throws InvalidFileException, InvalidIdException, DuplicateProfessorException {
        try {
            loadCoursesFromFile(new File("src/data/courses.txt"));
            loadProfessorsFromFile(new File("src/data/professors.txt"));
            loadProjectsFromFile(new File("src/data/projects.txt"));
        } catch (FileNotFoundException e) {
            throw new InvalidFileException("Demo data files not found");
        } catch (IOException e) {
            throw new InvalidFileException("Error reading demo data files");
        }
    }

    public void loadDataFromFile(String filePath) throws InvalidFileException, InvalidIdException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split("-");
            }
        } catch (IOException e) {
            throw new InvalidFileException("Unable to load file: " + filePath);
        }
    }

    public void addCourse(Course course) throws InvalidIdException {
        for (Course c : courseList) {
            if (c.getCourseCode().equals(course.getCourseCode())) {
                throw new InvalidIdException("Duplicate course id");
            }
        }
        courseList.add(course);
    }

    public Course getCourseById(String code) {
        for (Course course : courseList) {
            if (course.getCourseCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    public List<Project> getAllProjects() {
        return projectList;
    }

    public void updateProjectInfo(String identifier, String courseCode, List<String> companies, String semester,
            String name, ProjectType type, String keywords, String description, String docLink,
            String derivedId) throws ProjectNotFoundException {
        Project project = findProjectById(identifier);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found with id: " + identifier);
        }
        project.setCourseCode(courseCode);
        project.setBeneficiaryCompanies(companies);
        project.setSemester(semester);
        project.setName(name);
        project.setType(type.toString());
        project.setKeywords(Arrays.asList(keywords.split(",")));
        project.setShortDescription(description);
        project.setStatementUrl(docLink);
        project.setRelatedProjectId(derivedId);
    }

    public Project findProjectById(String identifier) {
        for (Project p : projectList) {
            if (p.getIdentifier().equals(identifier)) {
                return p;
            }
        }
        return null;
    }

    public boolean isValidGitHubUrl(String url) {
        return ValidateUrl.isValidGitHubUrl(url);
    }

    public boolean isValidDocumentUrl(String url) {
        return ValidateUrl.isValidCloudUrl(url);
    }

    public void addProfessor(Professor professor) throws DuplicateProfessorException {
        for (Professor p : professorList) {
            if (p.getId().equals(professor.getId())) {
                throw new DuplicateProfessorException("Duplicate professor id");
            }
        }
        professorList.add(professor);
    }

    void loadCoursesFromFile(File file) throws IOException, InvalidIdException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length != 4)
                    continue;
                String code = parts[0].trim();
                String name = parts[1].trim();
                String description = parts[2].trim();
                int credits = Integer.parseInt(parts[3].trim());
                Course course = new Course(code, name, description, credits, description);
                addCourse(course);
            }
        }
    }

    void loadProfessorsFromFile(File file) throws IOException, InvalidIdException, DuplicateProfessorException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length != 4)
                    continue;
                String id = parts[0].trim();
                String idType = parts[1].trim();
                String name = parts[2].trim();
                String email = parts[3].trim();
                Professor professor = new Professor(id, idType, name, email);
                addProfessor(professor);
            }
        }
    }

    public void loadProjectsFromFile(File file) throws IOException, InvalidIdException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length < 9)
                    continue;

                String id = parts[0].trim();
                List<String> companies = Arrays.asList(parts[1].trim().split(";"));
                String semester = parts[2].trim();
                String name = parts[3].trim();
                ProjectType type = ProjectType.valueOf(parts[4].trim());
                List<String> keywords = Arrays.asList(parts[5].trim().split(";"));
                String description = parts[6].trim();
                String docUrl = parts[7].trim();
                String courseCode = parts[8].trim();

                Project project = new Project(id, name, type.toString(), semester, description, keywords, docUrl,
                        companies);
                project.setCourseCode(courseCode);
                projectList.add(project);

                System.out.println("Project loaded: " + id + " - " + name);
            }
        }
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public List<Professor> getProfessorList() {
        return professorList;
    }
}
