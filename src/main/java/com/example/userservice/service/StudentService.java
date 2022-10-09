package com.example.userservice.service;

import com.example.userservice.model.Student;
import com.example.userservice.runnable.StudentRunnable;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StudentService {
    // @Autowired
    //private RestTemplate restTemplate;

    public void saveStudent(Student student) {
        RestTemplate restTemplate= new RestTemplate();
        HttpEntity<Student> entity= new HttpEntity<>(student);
        try
        {
            String response = restTemplate.postForObject("http://localhost:8082/students",entity,String.class);
            System.out.println(response);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void saveStudentsFromFile () {
        Map<String, String> mapping = new
                HashMap<String, String>();
        mapping.put("classOfStudy", "classOfStudy");
        mapping.put("section", "section");
        mapping.put("emailId", "emailId");
        mapping.put("firstName", "firstName");
        mapping.put("middleName", "middleName");
        mapping.put("lastName", "lastName");
        mapping.put("address", "address");
        mapping.put("motherName", "motherName");
        mapping.put("fatherName", "fatherName");
        HeaderColumnNameTranslateMappingStrategy<Student> strategy =
                new HeaderColumnNameTranslateMappingStrategy<Student>();
        strategy.setType(Student.class);
        strategy.setColumnMapping(mapping);

        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader
                    ("C:\\Users\\atejaswi\\Downloads\\student.csv"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CsvToBean csvToBean = new CsvToBean();
        csvToBean.setCsvReader(csvReader);
        csvToBean.setMappingStrategy(strategy);

        // call the parse method of CsvToBean
        // pass strategy, csvReader to parse method
        List<Student> studentList = csvToBean.parse();

        ExecutorService executor=   Executors.newFixedThreadPool(20);

        studentList.parallelStream().
                forEach(student -> executor.submit(new StudentRunnable(student)));

    }
}
