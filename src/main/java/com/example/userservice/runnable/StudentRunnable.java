package com.example.userservice.runnable;

import com.example.userservice.model.Student;
import com.example.userservice.service.StudentService;
import org.springframework.stereotype.Component;

@Component
public class StudentRunnable implements Runnable {
    private Student student;
    private StudentService studentService;

    public StudentRunnable(){

    }
    public StudentRunnable(Student student) {
        this.student = student;
        this.studentService=new StudentService();
    }

    @Override
    public void run() {
        studentService.saveStudent(student);
    }
}
