
package com.student.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.student.model.Student;

public interface StudentRepository
        extends JpaRepository<Student, Integer> {


    List<Student> findByEmailContainingIgnoreCase(String email);

    List<Student> findByCourseNameContainingIgnoreCase(String course);

    @Query("""
        SELECT DISTINCT s
        FROM Student s
        JOIN s.technicalSkills skill
        WHERE LOWER(skill) = LOWER(:skill)
    """)
    List<Student> findStudentsBySkill(
        @Param("skill") String skill
    );
}
