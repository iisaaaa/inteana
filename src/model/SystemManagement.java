package model;

import exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SystemManagement {
    private Controller controller;

    public SystemManagement() {
        this.controller = new Controller();
    }

    public void initializeSystem(String path) throws InvalidFileException, InvalidIdException, DuplicateProfessorException {
        if (path.equalsIgnoreCase("demo")) {
            try {
                controller.loadCoursesFromFile(new File("src\\data\\courses.txt"));
                controller.loadProfessorsFromFile(new File("src\\data\\professors.txt"));
                controller.loadProjectsFromFile(new File("src\\data\\projects.txt"));
            } catch (IOException e) {
                throw new InvalidFileException("Failed to load demo files");
            }
        } else {
            controller.loadDataFromFile(path);
        }
    }

    public void addCourse(Course course) throws InvalidIdException {
        controller.addCourse(course);
    }

    public Course getCourse(String code) throws CourseNotFoundException {
        Course course = controller.getCourseById(code);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with code: " + code);
        }
        return course;
    }

    public void addProfessor(Professor professor) throws DuplicateProfessorException {
        controller.addProfessor(professor);
    }

    public void updateProjectInfo(String projectId, String courseCode, List<String> companies, String semester,
            String name, ProjectType type, String keywords, String description,
            String documentLink, String derivedProjectId) throws ProjectNotFoundException {
        controller.updateProjectInfo(projectId, courseCode, companies, semester, name, type, keywords, description,
                documentLink, derivedProjectId);
    }

    public List<Project> searchProjectsByCourseNameOrKeyword(String query) {
        List<Project> matches = new ArrayList<>();
        for (Project project : controller.getAllProjects()) {
            Course course = controller.getCourseById(project.getCourseCode());
            boolean matchInCourse = course != null && course.getName().toLowerCase().contains(query.toLowerCase());
            boolean matchInKeywords = project.getKeywords().stream()
                    .anyMatch(k -> k.toLowerCase().contains(query.toLowerCase()));
            if (matchInCourse || matchInKeywords) {
                matches.add(project);
            }
        }
        return matches;
    }

    public void initializeCustomData(String coursesPath, String professorsPath, String projectsPath)
            throws InvalidFileException, InvalidIdException, DuplicateProfessorException, IOException {
        controller.loadCoursesFromFile(new File(coursesPath));
        controller.loadProfessorsFromFile(new File(professorsPath));
        controller.loadProjectsFromFile(new File(projectsPath));
    }

    public boolean isValidDocumentUrl(String url) {
        return controller.isValidDocumentUrl(url);
    }

    public boolean isValidGitHubUrl(String url) {
        return controller.isValidGitHubUrl(url);
    }

    public List<Project> getAllProjects() {
        return controller.getProjectList();
    }

    public List<Course> getAllCourses() {
        return controller.getCourseList();
    }

    public List<Professor> getProfessorList() {
        return controller.getProfessorList();
    }

}
