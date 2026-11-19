
package com.student.student.service;

import java.util.List;
import java.util.Comparator;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.student.student.model.Student;
import com.student.student.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    private void validateStudent(Student s) {

        if (s.getStudentId() <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid Student ID"
            );
        }

        if (s.getProgrammingScore() < 0 ||
            s.getProgrammingScore() > 100) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Programming score must be between 0 and 100"
            );
        }

        if (s.getMathematicsScore() < 0 ||
            s.getMathematicsScore() > 100) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Mathematics score must be between 0 and 100"
            );
        }
    }

    public Student addStudent(Student s) {

        validateStudent(s);

        if (repository.existsById(s.getStudentId())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Student ID already exists"
            );
        }

        return repository.save(s);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(int id) {

        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Student not found"
            ));
    }

    public List<Student> searchByEmail(String email) {
        return repository.findByEmailContainingIgnoreCase(email);
    }


    public List<Student> searchByCourse(String course) {
        return repository.findByCourseNameContainingIgnoreCase(course);
    }

    public double getAverage(int id) {
        Student s = getStudentById(id);
        return s.calculateAverage();
    }

    public String getPerformanceCategory(int id) {

        double avg = getAverage(id);

        if (avg >= 85)
            return "Excellent";
        else if (avg >= 70)
            return "Good";
        else if (avg >= 50)
            return "Satisfactory";
        else
            return "Needs Improvement";
    }

    public String getStrongerSubject(int id) {

        Student s = getStudentById(id);

        if (s.getProgrammingScore() >
            s.getMathematicsScore()) {
            return "Programming";
        }
        else if (s.getMathematicsScore() >
                 s.getProgrammingScore()) {
            return "Mathematics";
        }
        else {
            return "Both are Equal";
        }
    }

    public List<Student> getMeritList() {

        return repository.findAll()
            .stream()
            .sorted(
                Comparator.comparingDouble(Student::calculateAverage)
                    .reversed()
            )
            .toList();
    }

    public List<Student> findStudentsWithSameSkill(String skill) {

        return repository.findStudentsBySkill(skill);
    }

    public List<Student> getStudentsNeedingImprovement() {

        return repository.findAll()
            .stream()
            .filter(s -> s.getProgrammingScore() < 50)
            .filter(s -> s.getMathematicsScore() >= 75)
            .toList();
    }

    public Student updateStudent(int id, Student updated) {

        Student existing = getStudentById(id);

        updated.setStudentId(id);

        validateStudent(updated);

        existing.setStudentName(updated.getStudentName());
        existing.setEmail(updated.getEmail());
        existing.setCourseName(updated.getCourseName());
        existing.setProgrammingScore(updated.getProgrammingScore());
        existing.setMathematicsScore(updated.getMathematicsScore());
        existing.setTechnicalSkills(updated.getTechnicalSkills());

        return repository.save(existing);
    }

    public void deleteStudent(int id) {

        Student student = getStudentById(id);

        repository.delete(student);
    }
}
