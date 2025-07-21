/**
 * Name: Manasvi Bhatnagar
 *
 **/
import java.util.PriorityQueue;

/**
 * The CourseManager class manages the courses, enrollments, and waitlists.
 * It supports adding courses, enrolling and
 * removing students,and managing waitlists.
 */
public class CourseManager {
    private int MAX_COURSES = 10;
    // Maximum number of courses that can be added.
    private CourseContainer[] courseArray;
    // Array holding course data.
    private int courseCount;
    // The number of courses currently added.

    /**
     * Constructor to initialize the CourseManager.
     * Initializes the course array and course count.
     */
    public CourseManager() {
        courseArray = new CourseContainer[MAX_COURSES];
        courseCount = 0;
    }

    /**
     * Adds a new course to the system.
     * If the course code already exists, throws an exception.
     * If the array is full, resizes the array.
     *
     * @param course The Course object to be added.
     * @throws Exception If the course already exists
     * or the maximum number of courses is reached.
     */
    public void addCourse(Course course) throws Exception {
        if (courseCount == courseArray.length) {
            resizeArray();
        }
        for (int i = 0; i < courseCount; i++) {
            if (courseArray[i].course.getCourseCode().equals(course.getCourseCode())) {
                throw new Exception("Course with this code already exists.");
            }
        }
        if (courseCount >= courseArray.length) {
            throw new Exception("Cannot add more courses. Maximum reached.");
        }
        courseArray[courseCount++] = new CourseContainer(course,
                new PriorityQueue<>(), new PriorityQueue<>());
    }

    /**
     * Resizes the array to accommodate more courses when the current array is full.
     * Doubles the size of the array.
     */
    private void resizeArray() {
        int newCapacity = courseArray.length * 2;
        CourseContainer[] newArray = new CourseContainer[newCapacity];
        System.arraycopy(courseArray, 0, newArray, 0, courseArray.length);
        courseArray = newArray;
        System.out.println("Array resized to capacity: " + newCapacity);
    }

    /**
     * Enrolls a student in a course. If the course is full
     * ,the student will be placed on the waitlist.
     * If both the course and waitlist are full, an exception is thrown.
     *
     * @param student The student to be enrolled.
     * @param courseCode The course code in which the student should be enrolled.
     * @throws Exception If the course is not found,
     *                   the student is already enrolled,
     *                   or both the course and waitlist are full.
     */
    public void addStudentToCourse(StudentInfo student, String courseCode)
            throws Exception {
        CourseContainer container = findCourse(courseCode);

        if (container == null) {
            throw new Exception("Course not found.");
        }

        if (isStudentInCourse(container, student)) {
            throw new Exception("Student already enrolled or waitlisted.");
        }

        if (container.enrolledStudents.size()
                < container.course.getMaxCapacity()) {
            container.enrolledStudents.offer(student);
            System.out.println("Student successfully enrolled.");
        } else if (container.course.isHasWaitlist()) {
            if (container.waitlist.size() <
                    container.course.getWaitlistCapacity()) {
                container.waitlist.offer(student);
                System.out.println
                        ("Course is full. Student has been waitlisted.");
            } else {
                throw new Exception
                        ("Waitlist is full. Enrollment is not available.");
            }
        } else {
            throw new Exception
                    ("Course is full and no waitlist is available.");
        }
    }

    /**
     * Checks if a student is already enrolled or waitlisted for a given course.
     *
     * @param container The course container.
     * @param student The student to be checked.
     * @return True if the student is enrolled or waitlisted, false otherwise.
     */
    private boolean isStudentInCourse(CourseContainer container,
                                      StudentInfo student) {
        return container.enrolledStudents.contains(student) ||
                container.waitlist.contains(student);
    }

    /**
     * Removes a student from a course. If there are students on the waitlist,
     * the next student from the waitlist will be enrolled.
     *
     * @param courseCode The course code.
     * @return The student who was removed from the course.
     * @throws Exception If no students are enrolled in the course.
     */
    public StudentInfo removeStudentFromCourse(String courseCode)
            throws Exception {
        CourseContainer container = findCourse(courseCode);
        if (container.enrolledStudents.isEmpty()) {
            throw new Exception("No students to remove.");
        }
        StudentInfo removed = container.enrolledStudents.poll();
        if (!container.waitlist.isEmpty()) {
            container.enrolledStudents.offer(container.waitlist.poll());
        }
        return removed;
    }

    /**
     * Returns the number of courses managed by the CourseManager.
     *
     * @return The number of courses.
     */
    public int getCourseCount() {
        return courseCount;
    }

    /**
     * Removes a course from the system by course code.
     *
     * @param courseCode The course code of the course to be removed.
     * @throws Exception If the course is not found.
     */
    public void removeCourse(String courseCode) throws Exception {
        int index = findCourseIndex(courseCode);
        if (index == -1) {
            throw new Exception("Course not found.");
        }
        courseArray[index] = courseArray[--courseCount];
        courseArray[courseCount] = null;
        System.out.println("Course " + courseCode + " removed successfully.");
    }

    /**
     * Displays all course enrollments (both enrolled and waitlist).
     */
    public void displayCourseEnrollments() {
        for (int i = 0; i < courseCount; i++) {
            System.out.println("Enrollments for Course Code: "
                    + courseArray[i].course.getCourseCode());
            if (courseArray[i].enrolledStudents.isEmpty() &&
                    courseArray[i].waitlist.isEmpty()) {
                System.out.println("No enrollments for this course.");
            } else {
                if (!courseArray[i].enrolledStudents.isEmpty()) {
                    System.out.println("Enrolled Students:");
                    for (StudentInfo student : courseArray[i].enrolledStudents)
                    {
                        System.out.println(student);
                    }
                }
                if (!courseArray[i].waitlist.isEmpty()) {
                    System.out.println("Waitlist:");
                    for (StudentInfo student : courseArray[i].waitlist) {
                        System.out.println(student);
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Displays the enrollments (both enrolled and waitlist)
     * for a specific course.
     *
     * @param courseCode The course code of the course to display.
     */
    public void displayCourseEnrollment(String courseCode) {
        try {
            CourseContainer container = findCourse(courseCode);
            System.out.println("Enrollments for Course: "
                    + container.course.getTitle() + " (" + courseCode + ")");
            if (container.enrolledStudents.isEmpty()) {
                System.out.println("No students enrolled.");
            } else {
                System.out.println("Enrolled Students:");
                for (StudentInfo student : container.enrolledStudents) {
                    System.out.println(student);
                }
            }
            if (!container.waitlist.isEmpty()) {
                System.out.println("\nWaitlist:");
                for (StudentInfo student : container.waitlist) {
                    System.out.println(student);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Finds the index of a course by its course code.
     *
     * @param courseCode The course code.
     * @return The index of the course in the course array, or -1 if not found.
     */
    private int findCourseIndex(String courseCode) {
        for (int i = 0; i < courseCount; i++) {
            if (courseArray[i].course.getCourseCode().equals(courseCode)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the course by its course code.
     *
     * @param courseCode The course code.
     * @return The CourseContainer for the course.
     * @throws Exception If the course is not found.
     */
    private CourseContainer findCourse(String courseCode) throws Exception {
        int index = findCourseIndex(courseCode);
        if (index == -1) {
            throw new Exception("Course not found.");
        }
        return courseArray[index];
    }

    /**
     * Inner class to store course info and enrolled students and waitlist.
     */
    private class CourseContainer {
        Course course;  // The course.
        PriorityQueue<StudentInfo> enrolledStudents;
        // Priority queue for enrolled students.
        PriorityQueue<StudentInfo> waitlist;
        // Priority queue for waitlisted students.

        CourseContainer(Course course,PriorityQueue<StudentInfo> enrolledStudents
                ,PriorityQueue<StudentInfo> waitlist) {
            this.course = course;
            this.enrolledStudents = enrolledStudents;
            this.waitlist = waitlist;
        }
    }

    /**
     * Peeks the first student in the enrolled students queue of a course.
     *
     * @param courseCode The course code.
     * @return The first enrolled student.
     * @throws Exception If the course is not found.
     */
    public StudentInfo peekFrontStudent(String courseCode)
            throws Exception {
        CourseContainer container = findCourse(courseCode);
        return container.enrolledStudents.peek();
    }
}
