/**
 * Name: Manasvi Bhatnagar
 * The Course class represents a course offering in an academic program.
 * It includes the course code, title, maximum capacity, and information
 * about whether the course has a waitlist and its capacity.
 */
public class Course {
    private String courseCode;
    // The code of the course (e.g., CSE 101).
    private String title;
    // The title or name of the course.
    private int maxCapacity;
    // The maximum number of students allowed in the course.
    private boolean hasWaitlist;
    // Flag indicating if the course has a waitlist.
    private int waitlistCapacity;
    // The capacity of the waitlist (if the course has one).

    /**
     * Constructor to create a Course object with the given details.
     *
     * @param courseCode The course code (e.g., CS101).
     * @param title The title of the course.
     * @param maxCapacity The maximum number of students allowed in the course.
     * @param hasWaitlist Boolean flag indicating if the course has a waitlist.
     * @param waitlistCapacity The capacity of the waitlist (if applicable).
     * @throws IllegalArgumentException If maxCapacity is non-positive or
     *                                  if waitlistCapacity is non-positive
     *                                  when a waitlist is enabled.
     */
    public Course(String courseCode, String title, int maxCapacity,
                  boolean hasWaitlist, int waitlistCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException
                    ("Max capacity must be a positive integer.");
        }
        if (hasWaitlist && waitlistCapacity <= 0) {
            throw new IllegalArgumentException
                    ("Waitlist capacity must be positive.");
        }
        this.courseCode = courseCode;
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.hasWaitlist = hasWaitlist;
        this.waitlistCapacity = hasWaitlist ? waitlistCapacity : 0;
    }

    /**
     * Gets the course code.
     *
     * @return The code of the course.
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Gets the title of the course.
     *
     * @return The title of the course.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the maximum capacity of the course.
     *
     * @return The maximum number of students allowed in the course.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Checks if the course has a waitlist.
     *
     * @return True if the course has a waitlist, false otherwise.
     */
    public boolean isHasWaitlist() {
        return hasWaitlist;
    }

    /**
     * Gets the capacity of the waitlist (if applicable).
     *
     * @return The capacity of the waitlist. If no waitlist, returns 0.
     */
    public int getWaitlistCapacity() {
        return waitlistCapacity;
    }
}
