/**
 * Name: Manasvi Bhatnagar
 * The CourseSystem Class acts as the
 * driver for the entire course management system.
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class CourseSystem {
    /**
     * Helper method to get a boolean input from the user.
     * The input must be either
     * T/t/True for true or F/F/False for false.
     *
     * @param scanner The scanner object to read user input.
     * @param prompt  The prompt message to display to the user.
     * @return The boolean value based on the user's input.
     */
    private static boolean getBooleanInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            // If input is empty or invalid, prompt the user again
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter T/F or True/False.");
                continue;
            }

            if (input.startsWith("t")) return true; // True input
            if (input.startsWith("f")) return false; // False input

            System.out.println("Invalid input. Please enter T/F or True/False.");
        }
    }

    /**
     * Main method that drives the Course Management System.
     * Provides an interactive menu for users to manage courses and students.
     */
    public static void main(String[] args) {
        // Instantiate a CourseManager to manage courses and students
        CourseManager courseManager = new CourseManager();
        Scanner scanner = new Scanner(System.in);

        // Continuously display the menu until the user chooses to exit
        while (true) {
            try {
                // Display the main menu
                System.out.println("\nWelcome to the Course Selection System");
                System.out.println("1. Add Course");
                System.out.println("2. Add Student to Course");
                System.out.println("3. Remove Student from Course");
                System.out.println("4. Display All Enrollments");
                System.out.println("5. Peek at Front Student in a Course");
                System.out.println("6. Remove a Course");
                System.out.println("7. View Enrollment for a Specific Course");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");

                // Read the user's choice
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Perform the selected action
                switch (choice) {
                    case 1: // Add a new course
                        System.out.print("Enter course code: ");
                        String courseCode = scanner.nextLine();
                        System.out.print("Enter course title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter maximum capacity: ");
                        int maxCapacity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        boolean hasWaitlist = getBooleanInput(scanner, "Should the course " +
                                "have a waitlist? (T/F/True/False): ");

                        int waitlistCapacity = 0;
                        if (hasWaitlist) {
                            System.out.print("Enter waitlist capacity: ");
                            waitlistCapacity = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                        }

                        // Create and add the course
                        Course course = new Course(courseCode, title,
                                maxCapacity, hasWaitlist, waitlistCapacity);
                        courseManager.addCourse(course);
                        System.out.println("Course added successfully.");
                        break;

                    case 2: // Add student to a course
                        try {
                            System.out.print("Enter student name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter student year" +
                                    " (1 = Freshman, 2 = Sophomore, 3 = Junior, 4 = Senior): ");
                            int year = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            boolean isHonors = getBooleanInput(scanner,
                                    "Is the student honors? (true/false/T/F): ");
                            System.out.print("Enter student ID: ");
                            String studentID = scanner.nextLine();
                            System.out.print("Enter course code: ");
                            courseCode = scanner.nextLine();

                            // Create and add the student to the course
                            StudentInfo student = new StudentInfo(name, year, isHonors, studentID);
                            courseManager.addStudentToCourse(student, courseCode);
                        } catch (InputMismatchException ex) {
                            System.out.println("Invalid input type. Please try again.");
                            scanner.nextLine(); // Clear the invalid input
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;

                    case 3: // Remove student from a course
                        System.out.print("Enter course code: ");
                        courseCode = scanner.nextLine();
                        try {
                            StudentInfo removedStudent = courseManager.removeStudentFromCourse(courseCode);
                            System.out.println("Removed student: " + removedStudent.getName());
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;

                    case 4: // Display all course enrollments
                        if(courseManager.getCourseCount() == 0) {
                            System.out.println("There are no courses to display.");
                        } else {
                            courseManager.displayCourseEnrollments();
                        }
                        break;

                    case 5: // Peek at the front student in a course
                        System.out.print("Enter course code to peek at front student: ");
                        courseCode = scanner.nextLine();
                        try {
                            StudentInfo frontStudent = courseManager.peekFrontStudent(courseCode);
                            if (frontStudent != null) {
                                System.out.println("Front student: " + frontStudent);
                            } else {
                                System.out.println("No students enrolled in this course.");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;

                    case 6: // Remove a course
                        System.out.print("Enter course code to remove: ");
                        courseCode = scanner.nextLine();
                        try {
                            courseManager.removeCourse(courseCode);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;

                    case 7: // View enrollments for a specific course
                        System.out.print
                                ("Enter course code to view enrollment: ");
                        courseCode = scanner.nextLine();
                        courseManager.displayCourseEnrollment(courseCode);
                        break;

                    case 8: // Exit the system
                        System.out.println("Exiting the system.");
                        return;

                    default: // Invalid choice
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println
                        ("Invalid input type. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception ex) {
                System.out.println("An error occurred: " + ex.getMessage());
            }
        }
    }
}
