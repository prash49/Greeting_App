package com.bridgelabz.greetingapp.controller;

import com.bridgelabz.greetingapp.dto.UserDto;
import com.bridgelabz.greetingapp.model.Greeting;
import com.bridgelabz.greetingapp.model.User;
import com.bridgelabz.greetingapp.service.IGreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private static final String template2 = "Hello, %s , %s!";
    private final AtomicLong counter = new AtomicLong();

    /*
     *curl localhost:8080/greeting
     @return={id =1 , content="hello world!}
     * curllocalhost:8080/greeting?name=prashanth
     * @return= { id=2, content="hello prashanth!
     */
    @GetMapping(value = {"/greeting", "/greeting/", "/greeting/home"})
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    /*
    *curl localhost:8080/greeting/prashanth
    @return={id =1 , content="hello prashanth!}
     */
    @GetMapping("greeting/{name}")
    public Greeting greetings(@PathVariable String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @Autowired
    private IGreetingService greetingService;

    /*
       *curl localhost:8080/greeting/service
       @return={id =1 , content="hello world!}
        */
    @GetMapping("greeting/service")
    public Greeting greeting() {
        return greetingService.greetingMessage();

    }

    @PostMapping("/greeting")
    public String greetingMessage(@RequestBody UserDto userDto) {
        return greetingService.greetingMessageByName(userDto);
    }
    @GetMapping("/find")
    public User findGreetById(@RequestParam long id) {
        return greetingService.getById(id);
    }


}
