package com.example.demo;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
class Demo1ApplicationTests {
    Calculator underTest = new Calculator();

    @Test
    void itShouldAddTwoNumbers() {
       //given
        int num1 = 20;
        int num2 =30;

        //when
        int result = underTest.add(num1, num2);

        //then
        assertThat(result).isEqualTo(50);
    }

    class Calculator {
        int add(int a, int b){
            return a+b;
        }
    }

}

