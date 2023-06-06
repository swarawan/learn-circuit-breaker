package com.swarawan.cb.service;

import com.swarawan.cb.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    public List<UserData> findAllStudents() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("student-cb");
        return circuitBreaker.run(
                () -> {
                    ResponseEntity<UserData[]> response = restTemplate.getForEntity("http://localhost:3001/api/students", UserData[].class);
                    return Arrays.asList(response.getBody());
                },
                (error) -> {
                    UserData defaultData = new UserData();
                    defaultData.setName("tono");
                    defaultData.setEmail("tono@investree.id");
                    List<UserData> defaultResponse = new ArrayList<>();
                    defaultResponse.add(defaultData);

                    return defaultResponse;
                }
        );
    }

}
