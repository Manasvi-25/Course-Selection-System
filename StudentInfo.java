/**
 * Name: Manasvi Bhatnagar
 **/
public class StudentInfo implements Comparable<StudentInfo> {
    private String name;
    // a string holding the name of the student (e.g., "Manasvi").
    private int year;
    // The year the student is in, where:
    // 1 = Freshman, 2 = Sophomore, 3 = Junior, 4 = Senior
    private boolean isHonors;
    // A boolean flag indicating if the student is part of the honors program.
    // true = honors student, false = regular student
    private String studentID;
    // A unique ID for the student, which must be different for each student.
    // This is a string format of student IDs (e.g., "S12").

    /**
     * Constructor to initialize a StudentInfo object.
     *
     * @param name The name of the student.
     * @param year The year of study (1 for Freshman, 2 for Sophomore, etc.).
     * @param isHonors Boolean flag showing if the student is in honors.
     * @param studentID Unique identifier for the student.
     * @throws IllegalArgumentException If year is not between 1 and 4.
     */
    public StudentInfo(String name, int year, boolean isHonors,
                       String studentID){
        if (year < 1 || year > 4) {
            // Throw an exception if the year is invalid (not between 1 and 4).
            throw new IllegalArgumentException("Year must be between " +
                    "1 (Freshman) and 4 (Senior).");
        }
        this.name = name;  // Initialize name
        this.year = year;  // Initialize
        this.isHonors = isHonors;  // Initialize honors status
        this.studentID = studentID;  // Initialize student ID
    }

    /**
     * Get the student's name.
     *
     * @return The name of the student.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the student's year of study.
     *
     * @return The year of the student (1-4).
     */
    public int getYear() {
        return year;
    }

    /**
     * Check if the student is part of the honors program.
     *
     * @return true if the student is in honors, false otherwise.
     */
    public boolean isHonors() {
        return isHonors;
    }

    /**
     * Get the unique student ID.
     *
     * @return The student's ID.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Calculate the priority of the student for course enrollment.
     *
     * Higher priority is given to senior students and honors students.
     * Priority is calculated by:
     * - Lower year values get higher priority (senior = 0, freshman = 3)
     * - Honors students get an additional priority boost.
     *
     * @return A priority value used to determine student enrollment priority.
     */
    public double getPriority() {
        double priority = 4 - year;
        // Seniors (year 4) have the highest priority (priority 0)
        if (isHonors) {
            priority -= 0.5;
            // Honors students get an extra priority boost
        }
        return priority;
    }

    /**
     * Compare this student with another student based on priority for enrollment.
     *
     * Students with higher priority (senior, honors)
     * will come before students with lower priority.
     *
     * @param other The other student to compare this student with.
     * @return A negative integer, zero, or a positive integer as this student is
     *         less than, equal to, or greater than the specified student.
     */
    @Override
    public int compareTo(StudentInfo other) {
        return Double.compare(getPriority(), other.getPriority());
    }

    /**
     * Check if two StudentInfo objects are equal.
     *
     * Two students are considered equal if they have the same student ID.
     *
     * @param obj The object to compare this student with.
     * @return true if the student IDs are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // If both objects are the same, return true
        if (obj == null || getClass() != obj.getClass()) return false;
        // If obj is not a StudentInfo, return false
        StudentInfo that = (StudentInfo) obj;
        // Cast the object to StudentInfo
        return studentID.equals(that.studentID);
        // Compare student IDs for equality
    }

    /**
     * Generate a hash code for the student based on their student ID.
     *
     * @return A hash code representing the student, based on their student ID.
     */
    @Override
    public int hashCode() {
        return studentID.hashCode();
        // Use the student ID to generate the hash code
    }
    /**
     * Convert the student information to a string representation.
     *
     * @return A string describing the student in the format:
     *         "Name (Year: <year>, Honors, ID: <studentID>)"
     */
    @Override
    public String toString() {
        return name + " (Year: " + year + (isHonors ? ", Honors" : "") +
                ", ID: " + studentID + ")";
    }
}