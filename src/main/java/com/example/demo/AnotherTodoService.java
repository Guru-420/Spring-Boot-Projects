package com.example.demo;

import org.springframework.stereotype.Service;

@Service("AnotherTodoService")
public class AnotherTodoService implements TodoService{
    @Override
    public boolean doSomething(){
        System.out.println("Another Todo Service");
        return false;
    }
}
