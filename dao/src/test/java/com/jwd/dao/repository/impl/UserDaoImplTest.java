package com.jwd.dao.repository.impl;

import com.jwd.dao.domain.UserRow;
import com.jwd.dao.domain.UserRowDto;
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
        final List<UserRowDto> expectedUsers = getExpectedUsers();
        final int expectedLength = expectedUsers.size();

        // mock set up
        // Mockito.when(userDaoMock.getUsers()).thenReturn(getExpectedUsers());

        // when | act
        final List<UserRowDto> actualUsers = userDao.getUsers();

        // then | assert
        assertEquals(expectedLength, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

    // positive
    @Test
    public void testGetUsers_comprehensive() throws DaoException {
        // given | arrange
        final List<UserRowDto> expectedUsers = getExpectedUsers();
        final int expectedLength = expectedUsers.size();

        final UserRow newUserRow = new UserRow(4L, "dara", "Rex", "Lex", "444");
        final UserRowDto changedUserRowDto = new UserRowDto(4L, "dara", "Rex", "Lex");
        final List<UserRowDto> expectedChangedUsers = new ArrayList<>(getExpectedUsers());
        expectedChangedUsers.add(changedUserRowDto);
        final int expectedChangedLength = expectedLength + 1;

        // when | act
        final List<UserRowDto> actualUsers = userDao.getUsers();
        final UserRowDto actualUserRowDto = userDao.saveUser(newUserRow);
        final List<UserRowDto> actualNewUsers = userDao.getUsers();

        // then | assert
        assertEquals(expectedLength, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);

        assertEquals(changedUserRowDto, actualUserRowDto);
        assertEquals(expectedChangedLength, actualNewUsers.size());
        assertEquals(expectedChangedUsers, actualNewUsers);
    }

    // positive
    @Test
    public void testSaveUser_success() throws DaoException {
        final UserRow newUserRow = new UserRow(4L, "dara", "Rex", "Lex", "444");
        final UserRowDto expectedUserRowDto = new UserRowDto(4L, "dara", "Rex", "Lex");

        final UserRowDto actualUserRowDto = userDao.saveUser(newUserRow);
        final List<UserRowDto> actualUsers = userDao.getUsers();

        assertEquals(expectedUserRowDto, actualUserRowDto);
        assertTrue(actualUsers.contains(expectedUserRowDto));
    }

    // negative
    @Test(expected = DaoException.class)
    public void testSaveUser_nullParameter() throws DaoException {
        userDao.saveUser(null);
    }

    private List<UserRowDto> getExpectedUsers() {
        return Arrays.asList(
                new UserRowDto(1L, "abra", "Andrei", "Rohau"),
                new UserRowDto(2L, "bara", "Valera", "Petrov"),
                new UserRowDto(3L, "cobra", "Serhei", "Skaryna")
        );
    }
}
