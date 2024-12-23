package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("FakeTodoService")
public class FakeServiceTodo implements TodoService{
    @Override
    public boolean doSomething() {
        System.out.println("FakeService Todo");
        return false;
    }
}
