package com.jwd.dao.repository.impl;

import org.junit.*;

public class UserDaoImplTest {

    /*
        AAA - arrange - act - assert
        given - when - then
     */

    @BeforeClass
    public static void setup() {
        System.out.println("BeforeClass");
    }

    @Before
    public void init() {
        System.out.println("Before");
    }

    @After
    public void teardown() {
        System.out.println("After");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("AfterClass");
    }

    // positive
    @Test
    public void testGetUsers_simple() {
        // given | arrange

        // mock set up
        // Mockito.when(userDaoMock.getUsers()).thenReturn(getExpectedUsers());

        // when | act

        // then | assert
    }
}
