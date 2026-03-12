import java.util.*;

// Main class
public class StudentPortal {

    static Scanner sc = new Scanner(System.in);

    // CO4: Using HashMap for credentials storage
    static HashMap<String, String> studentCredentials = new HashMap<>();
    static HashMap<String, Student> students = new HashMap<>();
    static String adminID = "admin1981";
    static String adminPassword = "klh@1981";

    // CO3: Stack for menu navigation history
    static Stack<String> menuHistory = new Stack<>();

    // CO3: Queue for attendance calculation requests
    static Queue<String> attendanceQueue = new LinkedList<>();

    public static void main(String[] args) {

        // Initialize student data
        initStudents();

        System.out.println("===== STUDENT PERSONAL PROFILE PORTAL =====");
        System.out.print("Login as (Student/Admin): ");
        String user = sc.nextLine();

        if (user.equalsIgnoreCase("Student")) {
            studentLogin();
        } else if (user.equalsIgnoreCase("Admin")) {
            adminLogin();
        } else {
            System.out.println("Invalid login type");
        }
    }

    // Initialize students and credentials (CO2 + CO4)
    static void initStudents() {
        // Student 1
        Student s1 = new Student(
                "2520030432", "anusri@klh", "K", "ANUSRI", "Female",
                "KOTA RAJESH", "KANTHARANI KOTA", "KARRI KANTHARANI", "Single",
                "TELUGU", "OC", "Mole on right wrist", "PITHAPURAM", "Hindu",
                "Indian", "Bachupally", "Regular", "DayScholar",
                "B.Tech", "CSE", "1st", "KLH",
                "9876543210", "kanusri@klh",
                "1999", "BHEL CYBER COLONY", "Hyderabad", "Ranga Reddy", "Telangana", "502300",
                "Bharatiya Vidya Bhavan School - 80% - 2023", "Sri Chaitanya Jr Kalasala - 90%",
                "KOTA RAJESH - EMPLOYEE - 8765432109", "KANTHARANI KOTA - EMPLOYEE - 7654321098"
        );

        // Student 2
        Student s2 = new Student(
                "2520040088", "advaith@klh", "ADVAITH", "KOTHAPETA", "Male",
                "VENU MADHAVA RAO KOTHAPETA", "KAMALA POORNIMA", "AVUNOORI", "Single",
                "TELUGU", "OC", "Mole on forehead", "HYDERABAD", "Hindu",
                "Indian", "Bachupally", "Regular", "DayScholar",
                "B.Tech", "CSE", "1st", "KLH",
                "7876543210", "advaithkothapeta@klh",
                "81", "DOCTOR'S COLONY SOUTH", "Hyderabad", "Ranga Reddy", "Telangana", "500035",
                "Sri Chaitanya Techno School - 9.8 - 2023", "Narayana Junior College - 92% - 2025",
                "VENU MADHAVA RAO KOTHAPETA - EMPLOYEE - 7965432109", "KAMALA POORNIMA - EMPLOYEE - 9654321098"
        );

        // CO4: Store in HashMap for fast lookup
        students.put(s1.rollNumber, s1);
        students.put(s2.rollNumber, s2);

        studentCredentials.put(s1.rollNumber, s1.password);
        studentCredentials.put(s2.rollNumber, s2.password);
    }

    // Student login
    static void studentLogin() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        if (studentCredentials.containsKey(id) && studentCredentials.get(id).equals(pass)) {
            studentMenu(id);
        } else {
            System.out.println("Invalid credentials");
        }
    }

    // Admin login
    static void adminLogin() {
        System.out.print("Enter Admin ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        if (id.equals(adminID) && pass.equals(adminPassword)) {
            System.out.print("Enter Student Roll Number: ");
            String roll = sc.nextLine();
            if (students.containsKey(roll)) {
                showAdminDetails(roll);
            } else {
                System.out.println("Student not found");
            }
        } else {
            System.out.println("Invalid admin credentials");
        }
    }

    // Student menu (CO3: Stack for navigation history)
    static void studentMenu(String roll) {
        int choice;
        do {
            menuHistory.push("Student Dashboard");
            System.out.println("\n===== STUDENT DASHBOARD =====");
            System.out.println("1 Personal Information");
            System.out.println("2 Academic Details");
            System.out.println("3 Contact Details");
            System.out.println("4 Address Details");
            System.out.println("5 Qualification Details");
            System.out.println("6 Dependent Details");
            System.out.println("7 Time Table");
            System.out.println("8 Attendance Calculator");
            System.out.println("9 Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> students.get(roll).printPersonalInfo();
                case 2 -> students.get(roll).printAcademicDetails();
                case 3 -> students.get(roll).printContactDetails();
                case 4 -> students.get(roll).printAddressDetails();
                case 5 -> students.get(roll).printQualificationDetails();
                case 6 -> students.get(roll).printDependentDetails();
                case 7 -> printTimetable();
                case 8 -> attendanceCalculator(roll);
                case 9 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice");
            }

        } while (choice != 9);
    }

    // Admin view
    static void showAdminDetails(String roll) {
        Student s = students.get(roll);
        s.printPersonalInfo();
        s.printAcademicDetails();
        s.printContactDetails();
        s.printAddressDetails();
        s.printQualificationDetails();
        s.printDependentDetails();
        printTimetable();
    }

    // Timetable
    static void printTimetable() {
        System.out.println("\n--- TIME TABLE ---");
        System.out.println("Monday : DSA FWD MAI JAVA LIB");
        System.out.println("Tuesday : OS DBMS DTI AI FWD");
        System.out.println("Wednesday : MAI DSA JAVA FWD BEEC");
        System.out.println("Thursday : DTI OS OOPS DBMS OOPS");
        System.out.println("Friday : DDCA BEEC DMS JAVA LIB");
        System.out.println("Saturday : DTI SPORTS DDCA BEEC FWD");
    }

    // Attendance Calculator (CO3: Queue for requests)
    static void attendanceCalculator(String roll) {
        attendanceQueue.add(roll); // enqueue request
        System.out.println("\n--- ATTENDANCE CALCULATOR ---");
        System.out.print("Classes Conducted: ");
        int conducted = sc.nextInt();
        System.out.print("Classes Attended: ");
        int attended = sc.nextInt();
        double percent = (double) attended / conducted * 100;
        System.out.println("Attendance Percentage: " + percent + "%");
        System.out.println(percent >= 75 ? "Status: Eligible" : "Status: Not Eligible");
        attendanceQueue.remove(); // dequeue after calculation
    }
}
// CO2: Student class as ADT
class Student {
    String rollNumber, password;
    String firstName, lastName, gender, fatherName, motherName, motherMaidenName, maritalStatus;
    String motherTongue, castCategory, identification, placeOfBirth, religion, nationality, campus, admissionType, hostelStatus;
    String course, branch, year, college;
    String phone, email;
    String houseNo, area, city, district, state, pincode;
    String tenth, intermediate;
    String fatherDetails, motherDetails;

    Student(String rollNumber, String password,
            String firstName, String lastName, String gender,
            String fatherName, String motherName, String motherMaidenName, String maritalStatus,
            String motherTongue, String castCategory, String identification, String placeOfBirth, String religion,
            String nationality, String campus, String admissionType, String hostelStatus,
            String course, String branch, String year, String college,
            String phone, String email,
            String houseNo, String area, String city, String district, String state, String pincode,
            String tenth, String intermediate,
            String fatherDetails, String motherDetails) {
        this.rollNumber = rollNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.motherMaidenName = motherMaidenName;
        this.maritalStatus = maritalStatus;
        this.motherTongue = motherTongue;
        this.castCategory = castCategory;
        this.identification = identification;
        this.placeOfBirth = placeOfBirth;
        this.religion = religion;
        this.nationality = nationality;
        this.campus = campus;
        this.admissionType = admissionType;
        this.hostelStatus = hostelStatus;
        this.course = course;
        this.branch = branch;
        this.year = year;
        this.college = college;
        this.phone = phone;
        this.email = email;
        this.houseNo = houseNo;
        this.area = area;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.tenth = tenth;
        this.intermediate = intermediate;
        this.fatherDetails = fatherDetails;
        this.motherDetails = motherDetails;
    }

    void printPersonalInfo() {
        System.out.println("\n--- PERSONAL INFORMATION ---");
        System.out.println("First Name : " + firstName);
        System.out.println("Last Name : " + lastName);
        System.out.println("Gender : " + gender);
        System.out.println("Father Name : " + fatherName);
        System.out.println("Mother Name : " + motherName);
        System.out.println("Mother Maiden Name : " + motherMaidenName);
        System.out.println("Marital Status : " + maritalStatus);
        System.out.println("Mother Tongue : " + motherTongue);
        System.out.println("Cast Category : " + castCategory);
        System.out.println("Identification : " + identification);
        System.out.println("Place of Birth : " + placeOfBirth);
        System.out.println("Religion : " + religion);
        System.out.println("Nationality : " + nationality);
        System.out.println("Campus : " + campus);
        System.out.println("Admission Type : " + admissionType);
        System.out.println("Hostel Status : " + hostelStatus);
    }

    void printAcademicDetails() {
        System.out.println("\n--- ACADEMIC DETAILS ---");
        System.out.println("Roll Number : " + rollNumber);
        System.out.println("Course : " + course);
        System.out.println("Branch : " + branch);
        System.out.println("Year : " + year);
        System.out.println("College : " + college);
    }

    void printContactDetails() {
        System.out.println("\n--- CONTACT DETAILS ---");
        System.out.println("Phone : " + phone);
        System.out.println("Email : " + email);
    }

    void printAddressDetails() {
        System.out.println("\n--- ADDRESS DETAILS ---");
        System.out.println("House No : " + houseNo);
        System.out.println("Area : " + area);
        System.out.println("City : " + city);
        System.out.println("District : " + district);
        System.out.println("State : " + state);
        System.out.println("Pincode : " + pincode);
    }

    void printQualificationDetails() {
        System.out.println("\n--- QUALIFICATION DETAILS ---");
        System.out.println("10th : " + tenth);
        System.out.println("Intermediate : " + intermediate);
    }

    void printDependentDetails() {
        System.out.println("\n--- DEPENDENT DETAILS ---");
        System.out.println("Father : " + fatherDetails);
        System.out.println("Mother : " + motherDetails);
    }
}