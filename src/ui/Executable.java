package ui;

import model.*;
import exceptions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Executable {

    private List<Professor> professorList;
    private List<Project> projectList;
    private List<Course> courseList;

    private static final Scanner scanner = new Scanner(System.in);
    private static final SystemManagement system = new SystemManagement();

    public static void main(String[] args) throws CourseNotFoundException {
        System.out.println("Management System ");

        System.out.println("Select data source:");
        System.out.println("1. Load demo data");
        System.out.println("2. Load data from file");
        System.out.print("Enter option (1 or 2): ");
        String input = scanner.nextLine().trim();

        try {
            if (input.equals("2")) {
                System.out.print("Enter path to courses file (e.j.: src/data/courses.txt): ");
                String coursesPath = scanner.nextLine();

                System.out.print("Enter path to professors file (e.j.: src/data/professors.txt): ");
                String professorsPath = scanner.nextLine();

                System.out.print("Enter path to projects file (e.j.: src/data/projects.txt): ");
                String projectsPath = scanner.nextLine();

                system.initializeCustomData(coursesPath, professorsPath, projectsPath);
            } else {
                system.initializeCustomData(
                        "src\\data\\courses.txt",
                        "src\\data\\professors.txt",
                        "src\\data\\projects.txt");
            }
        } catch (Exception e) {
            System.out.println("Error initializing system: " + e.getMessage());
        }

        int option;
        do {
            showMenu();
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    registerCourse();
                    break;
                case 2:
                    registerProfessor();
                    break;
                case 3:
                    registerProject();
                    break;
                case 4:
                    searchProjects();
                    break;
                case 5:
                    consultProjectById();
                    break;
                case 6:
                    editProject();
                    break;
                case 7:
                    registerResultAndDeliverables();
                    break;
                case 8:
                    viewProjectsWithoutResults();
                    break;
                case 9:
                    searchBySemesterCourseProject();
                    break;
                case 10:
                    viewAllCourses();
                    break;
                case 11:
                    viewAllProfessors();
                    break;
                case 12:
                    viewAllProjects();
                    break;
                case 0:
                    System.out.println("Exiting system");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 0);
    }

    private static void showMenu() {
        System.out.println("""
                \nMenu:
                1. Register course
                2. Register professor
                3. Register project
                4. Search projects (by course or keyword)
                5. Consult project (with results and deliverables)
                6. Edit project
                7. Register result and deliverables
                8. View projects without results (by rofessor id)
                9. Search project by semester
                0. Exit
                """);
        System.out.print("Select an option: ");
    }

    private static void registerCourse() {
        try {
            System.out.print("Course code: ");
            String code = scanner.nextLine();
            System.out.print("Course title: ");
            String title = scanner.nextLine();
            System.out.print("Course description: ");
            String description = scanner.nextLine();
            System.out.print("Credits: ");
            int credits = Integer.parseInt(scanner.nextLine());

            System.out.print("Professor id (in charge of this course): ");
            String professorId = scanner.nextLine();

            Course course = new Course(code, title, description, credits, professorId);
            course.setProfessorId(professorId);

            system.addCourse(course);
            System.out.println("Course registered successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerProfessor() {
        try {
            System.out.print("Professor id: ");
            String id = scanner.nextLine();
            System.out.print("id type: ");
            String idType = scanner.nextLine();
            System.out.print("Full name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Professor professor = new Professor(id, idType, name, email);
            system.addProfessor(professor);
            System.out.println("Professor registered successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerProject() {
        try {
            System.out.print("Project id: ");
            String id = scanner.nextLine();
            System.out.print("Project name: ");
            String name = scanner.nextLine();
            System.out.print("Project type (INTEGRATOR_TASK, COURSE_PROJECT, FINAL_PROJECT): ");
            ProjectType type = ProjectType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Semester (e.j.: 2024_1): ");
            String semester = scanner.nextLine();
            System.out.print("Short description: ");
            String description = scanner.nextLine();
            System.out.print("Keywords (comma separated): ");
            List<String> keywords = Arrays.asList(scanner.nextLine().split(","));
            System.out.print("Statement document url: ");
            String docUrl = scanner.nextLine();
            System.out.print("Course code: ");
            String courseCode = scanner.nextLine();
            System.out.print("Beneficiary companies (comma separated): ");
            List<String> companies = Arrays.asList(scanner.nextLine().split(","));

            Project project = new Project(id, name, type.toString(), semester, description, keywords, docUrl,
                    companies);
            project.setCourseCode(courseCode);

            system.getAllProjects().add(project);
            System.out.println("Project registered successfully.");
        } catch (Exception e) {
            System.out.println("Error registering project: " + e.getMessage());
        }
    }

    private static void searchProjects() {
        System.out.print("Enter course name or keyword: ");
        String look = scanner.nextLine();
        List<Project> results = system.searchProjectsByCourseNameOrKeyword(look);
        if (results.isEmpty()) {
            System.out.println("No projects found.");
        } else {
            for (Project project : results) {
                System.out.println(project);
            }
        }
    }

    private static void consultProjectById() {
        System.out.print("Enter project ID: ");
        String id = scanner.nextLine();

        Project project = system.getAllProjects().stream()
                .filter(p -> p.getIdentifier().equals(id))
                .findFirst().orElse(null);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        System.out.println("\nProject Info");
        System.out.println("id: " + project.getIdentifier());
        System.out.println("Name: " + project.getName());
        System.out.println("Type: " + project.getType());
        System.out.println("Semester: " + project.getSemester());
        System.out.println("Description: " + project.getShortDescription());
        System.out.println("Keywords: " + String.join(", ", project.getKeywords()));
        System.out.println("Document: " + project.getStatementUrl());
        System.out.println("Course Code: " + project.getCourseCode());
        System.out.println("Beneficiaries: " + String.join(", ", project.getBeneficiaryCompanies()));
        System.out.println(
                "Related Project: " + (project.getRelatedProjectId() == null ? "None" : project.getRelatedProjectId()));

        List<Result> results = project.getResults();
        if (results.isEmpty()) {
            System.out.println("\nNo results registered.");
        } else {
            System.out.println(" Results");
            for (Result result : results) {
                System.out.println("Result id: " + result.getIdentifier());
                System.out.println("Name: " + result.getName());
                System.out.println("Description: " + result.getDescription());
                System.out.println("Group: " + result.getGroupCode());
                System.out.println("Date: " + result.getSubmissionDate());

                List<Deliverable> deliverables = result.getDeliverables();
                if (deliverables.isEmpty()) {
                    System.out.println("No deliverables.");
                } else {
                    System.out.println("Deliverables:");
                    for (Deliverable d : deliverables) {
                        if (!d.isDeleted()) {
                            System.out.println("    - " + d.toString());
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    private static void editProject() {
        try {
            System.out.print("Enter project id to edit: ");
            String id = scanner.nextLine();
            System.out.print("New name: ");
            String name = scanner.nextLine();
            System.out.print("New type: ");
            ProjectType type = ProjectType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("New semester: ");
            String semester = scanner.nextLine();
            System.out.print("New short description: ");
            String description = scanner.nextLine();
            System.out.print("New keywords (comma separated): ");
            String keywords = scanner.nextLine();
            System.out.print("New statement URL: ");
            String docUrl = scanner.nextLine();
            System.out.print("New beneficiary companies (comma separated): ");
            List<String> companies = Arrays.asList(scanner.nextLine().split(","));
            System.out.print("Related project ID (or leave empty): ");
            String relatedId = scanner.nextLine();

            Project project = system.getAllProjects().stream()
                    .filter(p -> p.getIdentifier().equals(id))
                    .findFirst().orElse(null);

            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            system.updateProjectInfo(id, project.getCourseCode(), companies, semester, name, type, keywords,
                    description, docUrl, relatedId);

            System.out.println("Project updated.");
        } catch (Exception e) {
            System.out.println("Error editing project: " + e.getMessage());
        }
    }

    private static void registerResultAndDeliverables() {
        try {
            System.out.print("Enter project id: ");
            String projectId = scanner.nextLine();

            Project project = system.getAllProjects().stream()
                    .filter(p -> p.getIdentifier().equals(projectId))
                    .findFirst().orElse(null);

            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            System.out.print("Result number (1 to 3): ");
            int resultIndex = Integer.parseInt(scanner.nextLine());

            System.out.print("Group code (e.j.: G3): ");
            String groupCode = scanner.nextLine();

            System.out.print("Result name: ");
            String resultName = scanner.nextLine();

            System.out.print("Result description: ");
            String resultDescription = scanner.nextLine();

            LocalDate date = LocalDate.now();

            Result result = new Result(projectId, resultIndex, groupCode, resultName, resultDescription, date);

            System.out.print("How many deliverables? ");
            int n = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < n; i++) {
                System.out.print("Deliverable type (DOCUMENT, REPOSITORY, ARTIFACT): ");
                String type = scanner.nextLine().toUpperCase();
                System.out.print("Title: ");
                String title = scanner.nextLine();
                System.out.print("Phase (REQUIREMENTS_ANALYSIS, DESIGN, CONSTRUCTION, TESTING, DEPLOYMENT): ");
                DevelopmentPhase phase = DevelopmentPhase.valueOf(scanner.nextLine().toUpperCase());

                switch (type) {
                    case "DOCUMENT" -> {
                        System.out.print("Document url: ");
                        String url = scanner.nextLine();
                        System.out.print("Cloud service: ");
                        String service = scanner.nextLine();
                        Document doc = new Document(title, phase, url, service);
                        result.addDeliverable(doc);
                    }
                    case "REPOSITORY" -> {
                        System.out.print("GitHub url: ");
                        String url = scanner.nextLine();
                        System.out.print("Files in the rpeository: ");
                        int fileCount = Integer.parseInt(scanner.nextLine());
                        Repository repo = new Repository(title, phase, url, fileCount);
                        result.addDeliverable(repo);
                    }
                    case "ARTIFACT" -> {
                        System.out.print("Artifact type (e.j.: Class Diagram, etc): ");
                        String artType = scanner.nextLine();
                        Artifact artifact = new Artifact(title, phase, artType);
                        result.addDeliverable(artifact);
                    }
                    default -> System.out.println("Unknown deliverable type.");
                }
            }

            project.addResult(result);
            System.out.println("Result registered successfully with its deliverables.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewProjectsWithoutResults() throws CourseNotFoundException {
        System.out.print("Enter professor id: ");
        String professorId = scanner.nextLine();

        List<Project> allProjects = system.getAllProjects();
        List<Project> noResultProjects = new ArrayList<>();

        for (Project project : allProjects) {
            Course course = system.getCourse(project.getCourseCode());
            if (course != null && course.getProfessorId().equals(professorId)) {
                if (project.getResults().isEmpty()) {
                    noResultProjects.add(project);
                }
            }
        }

        if (noResultProjects.isEmpty()) {
            System.out.println("No projects found without results for that professor.");
        } else {
            System.out.println("Projects without results");
            for (Project p : noResultProjects) {
                System.out.println("id: " + p.getIdentifier() + " - name: " + p.getName());
            }
        }
    }

    private static void searchBySemesterCourseProject() {
        System.out.print("Enter semester (e.j.: 2024_1): ");
        String semester = scanner.nextLine();

        List<Project> allProjects = system.getAllProjects();

        List<String> courseCodesWithProjects = new ArrayList<>();
        for (Project project : allProjects) {
            if (semester.equals(project.getSemester())) {
                courseCodesWithProjects.add(project.getCourseCode());
            }
        }

        if (courseCodesWithProjects.isEmpty()) {
            System.out.println("No projects found for that semester.");
            return;
        }

        System.out.println("Courses with projects in " + semester + ":");
        Map<String, String> courseCodeToName = new HashMap<>();
        for (String code : courseCodesWithProjects) {
            Course course = null;
            try {
                course = system.getCourse(code);
            } catch (CourseNotFoundException e) {
                e.printStackTrace();
            }
            if (course != null) {
                System.out.println("- " + code + " : " + course.getName());
                courseCodeToName.put(code, course.getName());
            }
        }

        System.out.print("Enter course code: ");
        String selectedCode = scanner.nextLine();

        List<Project> filtered = new ArrayList<>();
        for (Project p : allProjects) {
            if (p.getSemester().equals(semester) && p.getCourseCode().equals(selectedCode)) {
                filtered.add(p);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No projects found for that course in the selected semester.");
            return;
        }

        System.out.println("Projects:");
        for (Project p : filtered) {
            System.out.println("- " + p.getIdentifier() + " : " + p.getName());
        }

        System.out.print("Enter project id to view details: ");
        String projectId = scanner.nextLine();

        Project selected = filtered.stream()
                .filter(p -> p.getIdentifier().equals(projectId))
                .findFirst().orElse(null);

        if (selected != null) {
            System.out.println("Project Details");
            System.out.println(selected);
        } else {
            System.out.println("Project id not found.");
        }
    }

    private static void viewAllCourses() {
        List<Course> courses = system.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        System.out.println("Courses");
        for (Course c : courses) {
            System.out.println("code: " + c.getCourseCode() + " -  title: " + c.getName() +" - professor id: " + c.getProfessorId());
        }
    }

    private static void viewAllProfessors() {
        List<Professor> professors = system.getProfessorList();
        if (professors.isEmpty()) {
            System.out.println("No professors found.");
            return;
        }

        System.out.println("Professors");
        for (Professor p : professors) {
            System.out.println("id: " + p.getId() +" - name: " + p.getFullName() + " - email: " + p.getEmail());
        }
    }

    private static void viewAllProjects() {
        List<Project> projects = system.getAllProjects();
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }

        System.out.println("Projects");
        for (Project p : projects) {
            System.out.println("id: " + p.getIdentifier() + " - name: " + p.getName() +" - course: " + p.getCourseCode());
        }
    }


    public void loadCoursesFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Error: " + line);
                    lineNumber++;
                    continue;
                }
                try {
                    String code = parts[0].trim();
                    String title = parts[1].trim();
                    String description = parts[2].trim();
                    int credits = Integer.parseInt(parts[3].trim());
                    Course course = new Course(code, title, description, credits, description);
                    courseList.add(course);
                    System.out.println("Loaded course: " + code + " - " + title);
                } catch (Exception e) {
                    System.out.println("Error loading course at line " + lineNumber + ": " + e.getMessage());
                }
                lineNumber++;
            }
        }
    }

    public void loadProfessorsFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4)
                    continue;
                String id = parts[0].trim();
                String idType = parts[1].trim();
                String fullName = parts[2].trim();
                String email = parts[3].trim();
                Professor professor = new Professor(id, idType, fullName, email);
                professorList.add(professor);
            }
        }
    }

    public void loadProjectsFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 9)
                    continue;

                String id = parts[0].trim();
                List<String> companies = Arrays.asList(parts[1].split(";"));
                String semester = parts[2].trim();
                String name = parts[3].trim();
                ProjectType type = ProjectType.valueOf(parts[4].trim());
                List<String> keywords = Arrays.asList(parts[5].split(";"));
                String description = parts[6].trim();
                String docUrl = parts[7].trim();
                String courseCode = parts[8].trim();

                Project project = new Project(id, name, type.toString(), semester, description, keywords, docUrl,companies);
                project.setCourseCode(courseCode);
                projectList.add(project);
            }
        }
    }

}