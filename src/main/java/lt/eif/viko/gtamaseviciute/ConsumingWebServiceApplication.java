package lt.eif.viko.gtamaseviciute;

import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.Student;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.Subject;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsByGroupResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.SetStudentActiveStatusResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsAverageGradeResponse;

import java.util.List;
import java.util.Scanner;

import static lt.eif.viko.gtamaseviciute.FileGeneratorService.*;

/**
 * <p>Pagrindinė Spring Boot aplikacijos klasė, kuri leidžia naudotojui sąveikauti su SOAP žiniatinklio paslauga per konsolę.</p>
 * Leidžia gauti studentų duomenis, keisti aktyvumo būseną, gauti pažymių vidurkį bei generuoti XML -> PDF / HTML failus.
 */
@SpringBootApplication
public class ConsumingWebServiceApplication {

    /**
     * Aplikacijos pradžios taškas.
     * @param args komandinės eilutės argumentai
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsumingWebServiceApplication.class, args);
    }

    /**
     * Komandinės eilutės meniu, kuri leidžia vartotojui pasirinkti veiksmus,
     * tokius kaip studentų gavimas, vidurkio gavimas, aktyvumo keitimas.
     *
     * @param studentClient klientas, naudojamas SOAP užklausoms siųsti
     * @return {@link CommandLineRunner} paleidimo logika
     */
    @Bean
    CommandLineRunner menu(StudentClient studentClient) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while(!exit){
                System.out.println("""
                        
                        --->> Student Web Service Menu <<---
                        1. Get student by name
                        2. Get all students
                        3. Get students by group
                        4. Set active status to student
                        5. Get average grade of all students
                        0. Exit
                        """);

                System.out.print("Choose option: ");
                String input = scanner.nextLine();

                switch (input) {
                    case "1": {
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        GetStudentResponse response = studentClient.getStudent(name);
                        String xmlFileName = name + ".xml";
                        saveXmlFromResponse(response, GetStudentResponse.class, xmlFileName);
                        Student student = response.getStudent();
                        printStudent(student);
                        subMenu(studentClient, student, xmlFileName);
                        break;
                    }
                    case "2": {
                        GetStudentsResponse response = studentClient.getAllStudents();
                        String xmlFileName ="students.xml";
                        saveXmlFromResponse(response, GetStudentsResponse.class, "students.xml");
                        List<Student> students = response.getStudents();
                        students.forEach(ConsumingWebServiceApplication::printStudent);
                        subMenu(studentClient, students, xmlFileName);
                        break;
                    }
                    case "3": {
                        System.out.print("Enter group name: ");
                        String group = scanner.nextLine();
                        GetStudentsByGroupResponse response = studentClient.getStudentsByGroup(group);
                        String xmlFileName = group + "_students.xml";
                        saveXmlFromResponse(response, GetStudentsByGroupResponse.class, group + "_students.xml");
                        List<Student> students = response.getStudents();
                        students.forEach(ConsumingWebServiceApplication::printStudent);
                        subMenu(studentClient, students, xmlFileName);
                        break;
                    }
                    case "4": {
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student active status: ");
                        String isActive = scanner.nextLine();
                        boolean active = Boolean.parseBoolean(isActive);
                        SetStudentActiveStatusResponse response = studentClient.setStudentStatus(name, active);
                        if (response.isActive() == active) {
                            System.out.println("Student '" + name + "' status was successfully updated to: " + active);
                        } else {
                            System.out.println("Failed to update status. Student might not exist.");
                        }
                        break;
                    }
                    case "5": {
                        GetStudentsAverageGradeResponse response = studentClient.getAverageStudentsGrade();
                        System.out.print("Average students grade: " + response.getAverageGrade());
                    }
                    break;
                    case "0": {
                        exit = true;
                        break;
                    }
                    default: System.out.println("Invalid option.");
                }
            }
            System.out.println("Bye!");
        };

    }

    /**
     * Atspausdina studento informaciją į konsolę.
     * @param student studentas, kurio duomenys atvaizduojami
     */
    private static void printStudent(Student student) {
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Group: " + student.getGroup());
        System.out.println("Subjects:");
        List<Subject> subjects = student.getSubjects();
        for (Subject subject : subjects) {
            System.out.println(" - " + subject.getTitle() + ", grade: " + subject.getGrade());
        }

        System.out.println();
    }

    /**
     * Meniu, leidžiantis sugeneruoti PDF arba HTML failus vienam studentui.
     *
     * @param client       studentų klientas
     * @param student      studentas
     * @param xmlFileName  XML failo pavadinimas (failas sugeneruotas anksčiau)
     */
    private static void subMenu(StudentClient client, Student student, String xmlFileName) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("""
                        
                        --->> Generate files: <<---
                        1. Generate PDF
                        2. Generate HTML
                        0. Back to menu
                        """);
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    System.out.print("Enter PDF file name: ");
                    String name = scanner.nextLine();
                    convertToPDF(name,xmlFileName);
                    break;
                }
                case "2": {
                    System.out.print("Enter HTML file name: ");
                    String name = scanner.nextLine();
                    convertToHTML(name, xmlFileName);
                    break;
                }
                case "0": return;
                default: {
                    System.out.println("Invalid option.");
                }
            }
        }

    }

    /**
     * Meniu, leidžiantis sugeneruoti PDF arba HTML failus studentų sąrašui.
     *
     * @param client       studentų klientas
     * @param students     studentų sąrašas
     * @param xmlFileName  XML failo pavadinimas (failas sugeneruotas anksčiau)
     */
    private static void subMenu(StudentClient client, List<Student> students,String xmlFileName) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("""
                        --->> Generate files: <<---
                        1. Generate PDF
                        2. Generate HTML
                        0. Back to menu
                        """);
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    System.out.print("Enter PDF file name: ");
                    String name = scanner.nextLine();
                    convertToPDF(name, xmlFileName);
                    break;
                }
                case "2": {
                    System.out.print("Enter HTML file name: ");
                    String name = scanner.nextLine();
                    convertToHTML(name, xmlFileName);
                    break;
                }
                case "0": return;
                default: {
                    System.out.println("Invalid option.");
                }
            }
        }

    }


}
