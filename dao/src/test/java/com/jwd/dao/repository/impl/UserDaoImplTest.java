package com.jwd.dao.repository.impl;

import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.exception.DaoException;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    /*
        AAA - arrange - act - assert
        given - when - then
     */

    private UserDaoImpl userDao = new UserDaoImpl(); // Mockito.mock(UserDaoImpl.class);

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
        final List<UserDto> expectedUsers = getExpectedUsers();
        final int expectedLength = expectedUsers.size();

        // mock set up
        // Mockito.when(userDaoMock.getUsers()).thenReturn(getExpectedUsers());

        // when | act
        final List<UserDto> actualUsers = userDao.getUsers();

        // then | assert
        assertEquals(expectedLength, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

    // positive
    @Test
    public void testGetUsers_comprehensive() throws DaoException {
        // given | arrange
        final List<UserDto> expectedUsers = getExpectedUsers();
        final int expectedLength = expectedUsers.size();

        final User newUser = new User(4L, "dara", "Rex", "Lex", "444");
        final UserDto changedUserDto = new UserDto(4L, "dara", "Rex", "Lex");
        final List<UserDto> expectedChangedUsers = new ArrayList<>(getExpectedUsers());
        expectedChangedUsers.add(changedUserDto);
        final int expectedChangedLength = expectedLength + 1;

        // when | act
        final List<UserDto> actualUsers = userDao.getUsers();
        final UserDto actualUserDto = userDao.saveUser(newUser);
        final List<UserDto> actualNewUsers = userDao.getUsers();

        // then | assert
        assertEquals(expectedLength, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);

        assertEquals(changedUserDto, actualUserDto);
        assertEquals(expectedChangedLength, actualNewUsers.size());
        assertEquals(expectedChangedUsers, actualNewUsers);
    }

    // positive
    @Test
    public void testSaveUser_success() throws DaoException {
        final User newUser = new User(4L, "dara", "Rex", "Lex", "444");
        final UserDto expectedUserDto = new UserDto(4L, "dara", "Rex", "Lex");

        final UserDto actualUserDto = userDao.saveUser(newUser);
        final List<UserDto> actualUsers = userDao.getUsers();

        assertEquals(expectedUserDto, actualUserDto);
        assertTrue(actualUsers.contains(expectedUserDto));
    }

    // negative
    @Test(expected = DaoException.class)
    public void testSaveUser_nullParameter() throws DaoException {
        userDao.saveUser(null);
    }

    private List<UserDto> getExpectedUsers() {
        return Arrays.asList(
                new UserDto(1L, "abra", "Andrei", "Rohau"),
                new UserDto(2L, "bara", "Valera", "Petrov"),
                new UserDto(3L, "cobra", "Serhei", "Skaryna")
        );
    }
}
