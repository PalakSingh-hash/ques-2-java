
package com.student.student.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.student.student.model.Student;
import com.student.student.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return service.getStudentById(id);
    }

    @GetMapping("/search/email")
    public List<Student> searchEmail(@RequestParam String email) {
        return service.searchByEmail(email);
    }

    @GetMapping("/search/course")
    public List<Student> searchCourse(@RequestParam String course) {
        return service.searchByCourse(course);
    }

    @GetMapping("/{id}/average")
    public double getAverage(@PathVariable int id) {
        return service.getAverage(id);
    }

    @GetMapping("/{id}/performance")
    public String getPerformance(@PathVariable int id) {
        return service.getPerformanceCategory(id);
    }

    @GetMapping("/{id}/stronger-subject")
    public String getStrongerSubject(@PathVariable int id) {
        return service.getStrongerSubject(id);
    }

    @GetMapping("/merit-list")
    public List<Student> meritList() {
        return service.getMeritList();
    }

    @GetMapping("/shared-skills")
    public List<Student> sharedSkills(@RequestParam String skill) {
        return service.findStudentsWithSameSkill(skill);
    }


    @GetMapping("/needs-improvement")
    public List<Student> needsImprovement() {
        return service.getStudentsNeedingImprovement();
    }

    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable int id,
            @RequestBody Student student) {

        return service.updateStudent(id, student);
    }

   
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {

        service.deleteStudent(id);

        return "Student deleted successfully";
    }
}
