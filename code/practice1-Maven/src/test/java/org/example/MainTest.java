package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.example.Main.add;

public class MainTest{

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @Test
    void testadd() {
        int c=add(1,2);
        Assertions.assertEquals(3,c,"add failed");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testadd(int a,int b){
        int c=add(a,b);
    }
}
