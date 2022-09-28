package com.main.server.service;

import com.example.mainserver.AbstractSpringBootTests;
import com.example.mainserver.util.TestException;
import com.main.server.controllers.UserController;
import com.main.server.model.Role;
import com.main.server.model.User;
import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceImplTest extends AbstractSpringBootTests {
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
//    private static final long USER_ID_1 = 1L;
//    private static final long USER_ID_2 = 2L;
//    private static final long ROLE_ADMIN_ID = 1L;
//    private static final long ROLE_USER_ID = 2L;
//
//    @Spy
//    private final User user1 = new User();
//    @Spy
//    private final User user2 = new User();
//    @Spy
//    private final UserRequest userRequest = new UserRequest();
//    @Spy
//    private final Role adminRole = new Role();
//    @Spy
//    private final Role userRole = new Role();
//    @Captor
//    private ArgumentCaptor<User> userArgumentCaptor;
//
//    @BeforeEach
//    void setUp() {
//        // Test user1
//        user1.setId(USER_ID_1);
//        user1.setFirstName("testName1");
//        user1.setLastName("testLastName1");
//        user1.setEmail("test1@test.com");
//        user1.setPassword("test1");
//        user1.setBirthDate(new Date());
//        user1.setResidentialAddress("testAdresss1");
//        user1.setDeleted(false);
//        user1.setSex(Sex.FEMALE);
//        // Test user2
//        user2.setId(USER_ID_2);
//        user2.setFirstName("testName2");
//        user2.setLastName("testLastName2");
//        user2.setEmail("test2@test.com");
//        user2.setPassword("test2");
//        user2.setBirthDate(new Date());
//        user2.setResidentialAddress("testAdresss2");
//        user2.setDeleted(false);
//        user2.setSex(Sex.FEMALE);
//        // Test role1
//        adminRole.setId(ROLE_ADMIN_ID);
//        adminRole.setName("ROLE_ADMIN");
//        // Test role2
//        userRole.setId(ROLE_USER_ID);
//        userRole.setName("ROLE_USER");
//
//        user1.addRole(adminRole);
//        user1.addRole(userRole);
//        user2.addRole(userRole);
//
//        HashSet<User> users = new HashSet<>();
//        users.add(user1);
//        users.add(user2);
//
//        //Test UserRequest
//        userRequest.setFirstName("testName1");
//        userRequest.setLastName("testLastName1");
//        userRequest.setEmail("test1@test.com");
//        userRequest.setPassword("test1");
//        userRequest.setBirthDate(new Date());
//        userRequest.setResidentialAddress("testAdresss1");
//        userRequest.setSex(Sex.FEMALE);
//        userRequest.setRoleIds(new long[]{ROLE_ADMIN_ID, ROLE_USER_ID});
//
//        given(userRepository.findById(USER_ID_1)).willReturn(Optional.of(user1));
//        given(userRepository.getAll()).willReturn(users);
//        given(userRepository.save(any())).willReturn(user1);
//        given(roleRepository.findById(ROLE_ADMIN_ID)).willReturn(Optional.of(adminRole));
//        given(roleRepository.findById(ROLE_USER_ID)).willReturn(Optional.of(userRole));
//    }
//
//    @Test
//    public void shouldGetUserDTOByIdIfUserExist() {
//        UserDto userDTO;
//        try {
//            userDTO = userService.getUser(USER_ID_1);
//        } catch (ResourceNotFoundException e) {
//            LOGGER.debug(Objects.isNull(e.getMessage()) ?
//                    String.format("Can't get user by id %s", USER_ID_1) :
//                    String.format("Can't get user by id %s, because %s: ", USER_ID_1, e.getMessage()));
//            throw new TestException(e.getMessage(), e);
//        }
//        assertEquals(userDTO.getEmail(), user1.getEmail());
//        assertEquals(userDTO.getFirstName(), user1.getFirstName());
//        assertEquals(userDTO.getLastName(), user1.getLastName());
//        assertEquals(userDTO.getResidentialAddress(), user1.getResidentialAddress());
//        assertEquals(userDTO.getBirthDate(), user1.getBirthDate());
//        assertEquals(userDTO.isDeleted(), user1.isDeleted());
//        assertEquals(userDTO.getSex(), user1.getSex());
//        assertNotNull(userDTO.getRoles());
//        assertEquals(2, userDTO.getRoles().size());
//    }
//
//    @Test
//    public void getUserShouldThrowResourceNotFountExceptionIfUserNotExist() {
//        given(userRepository.findById(USER_ID_1)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> userService.getUser(USER_ID_1));
//    }
//
//    @Test
//    public void shouldGetAllUsers() {
//        List<UserDto> userDTOS = userService.getAllUsers();
//        assertNotNull(userDTOS);
//        assertEquals(2, userDTOS.size());
//    }
//
//    public void invokeSaveUser() {
//        try {
//            userService.saveUser(userRequest);
//        } catch (ResourceNotFoundException | ResourceAlreadyExistException e) {
//            LOGGER.debug(Objects.isNull(e.getMessage()) ?
//                    String.format("Can't save user %s", userRequest) :
//                    String.format("Can't save user %s, because %s: ", userRequest, e.getMessage()));
//            throw new TestException(e.getMessage(), e);
//        }
//    }
//
//    @Test
//    public void shouldSaveUserInDB() {
//        invokeSaveUser();
//        verify(userRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void saveUserShouldMapsRoleIdsToRoleEntity() {
//        invokeSaveUser();
//        Set<Role> expectedRoles = new HashSet<>();
//        expectedRoles.add(adminRole);
//        expectedRoles.add(userRole);
//        verify(userRepository).save(userArgumentCaptor.capture());
//        Set<Role> roles = userArgumentCaptor.getValue().getRoles();
//        assertFalse(roles.isEmpty());
//        assertEquals(expectedRoles.size(), roles.size());
//        assertTrue(expectedRoles.containsAll(roles));
//        assertTrue(roles.containsAll(expectedRoles));
//    }
//
//    @Test
//    public void saveUserShouldThrowResourceNotFoundExceptionIfRoleByIdNotExist() {
//        given(roleRepository.findById(ROLE_ADMIN_ID)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> userService.saveUser(userRequest));
//    }
//
//    @Test
//    public void saveUserShouldThrowResourceAlreadyExistExceptionIfUserWithSameEmailExist() {
//        given(userRepository.findByEmail(user1.getEmail())).willReturn(Optional.of(user1));
//        assertThrows(ResourceAlreadyExistException.class, () -> userService.saveUser(userRequest));
//    }
//
//    public void invokeUpdateUser() {
//        try {
//            userService.updateUser(USER_ID_1, userRequest);
//        } catch (ResourceNotFoundException | ResourceAlreadyExistException e) {
//            LOGGER.debug(Objects.isNull(e.getMessage()) ?
//                    String.format("Can't update user %s", userRequest) :
//                    String.format("Can't update user %s, because %s: ", userRequest, e.getMessage()));
//            throw new TestException(e.getMessage(), e);
//        }
//    }
//
//    @Test
//    public void shouldUpdateUserInDB() {
//        invokeUpdateUser();
//        verify(userRepository, times(1)).save(user1);
//    }
//
//    @Test
//    public void shouldUpdateFirstName() {
//        userRequest.setFirstName("testFirstName");
//        invokeUpdateUser();
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User user = userArgumentCaptor.getValue();
//        assertEquals(userRequest.getFirstName(), user.getFirstName());
//    }
//
//    @Test
//    public void shouldUpdateLastName() {
//        userRequest.setLastName("testLastName");
//        invokeUpdateUser();
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User user = userArgumentCaptor.getValue();
//        assertEquals(userRequest.getLastName(), user.getLastName());
//    }
//
//    @Test
//    public void shouldUpdateEmail() {
//        userRequest.setEmail("tesEmail");
//        invokeUpdateUser();
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User user = userArgumentCaptor.getValue();
//        assertEquals(userRequest.getEmail(), user.getEmail());
//    }
//
//    @Test
//    public void updateShouldThrowExceptionIfUserWithSameEmailExist() {
//        given(userRepository.findByEmail(userRequest.getEmail())).willReturn(Optional.of(new User()));
//        assertThrows(ResourceAlreadyExistException.class,
//                () -> userService.saveUser(userRequest));
//    }
//
//    @Test
//    public void shouldUpdatePassword() {
//        userRequest.setPassword("tesPassword");
//        invokeUpdateUser();
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User user = userArgumentCaptor.getValue();
//        assertEquals(userRequest.getPassword(), user.getPassword());
//    }
//
//    @Test
//    public void shouldUpdateBirthDate() {
//        userRequest.setBirthDate(new Date());
//        invokeUpdateUser();
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User user = userArgumentCaptor.getValue();
//        assertEquals(userRequest.getBirthDate(), user.getBirthDate());
//    }
//
//    @Test
//    public void shouldUpdateResidentialAddress() {
//        userRequest.setResidentialAddress("TestResidentialAddress");
//        invokeUpdateUser();
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User user = userArgumentCaptor.getValue();
//        assertEquals(userRequest.getResidentialAddress(), user.getResidentialAddress());
//    }
//
//    @Test
//    public void shouldUpdateSex() {
//        userRequest.setSex(Sex.MALE);
//        invokeUpdateUser();
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User user = userArgumentCaptor.getValue();
//        assertEquals(userRequest.getSex(), user.getSex());
//    }
//
//    @Test
//    public void updateUserShouldMapsRoleIdsToRoleEntity() {
//        invokeUpdateUser();
//        Set<Role> expectedRoles = new HashSet<>();
//        expectedRoles.add(adminRole);
//        expectedRoles.add(userRole);
//        verify(userRepository).save(userArgumentCaptor.capture());
//        Set<Role> roles = userArgumentCaptor.getValue().getRoles();
//        assertFalse(roles.isEmpty());
//        assertEquals(expectedRoles.size(), roles.size());
//        assertTrue(expectedRoles.containsAll(roles));
//        assertTrue(roles.containsAll(expectedRoles));
//    }
//
//    @Test
//    public void updateUserShouldThrowResourceNotFoundExceptionIfRoleByIdNotExist() {
//        given(roleRepository.findById(ROLE_ADMIN_ID)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(USER_ID_1, userRequest));
//    }
//
//    @Test
//    public void updateUserShouldThrowResourceNotFoundExceptionIfUserByIdNotFound() {
//        given(userRepository.findById(ROLE_ADMIN_ID)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(USER_ID_1, userRequest));
//    }
//
//    public void invokeDeleteUser() {
//        try {
//            userService.deleteUser(USER_ID_1);
//        } catch (ResourceNotFoundException e) {
//            LOGGER.debug(Objects.isNull(e.getMessage()) ?
//                    String.format("Can't update user %s", userRequest) :
//                    String.format("Can't update user %s, because %s: ", userRequest, e.getMessage()));
//            throw new TestException(e.getMessage(), e);
//        }
//    }
//
//    @Test
//    public void deleteUserShouldSetIsDeletedToTrue() {
//        given(userRepository.findById(USER_ID_1)).willReturn(Optional.of(user1));
//        invokeDeleteUser();
//        verify(user1, times(1)).setDeleted(true);
//        assertTrue(user1.isDeleted());
//    }
//
//    @Test
//    public void deleteUserShouldThrowResourceNotFoundExceptionIfUserNotExist() {
//        given(userRepository.findById(USER_ID_1)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class,
//                () -> userService.deleteUser(USER_ID_1)
//        );
//    }
//
//    @Test
//    public void deleteUserShouldNotDeleteFromDB() {
//        invokeDeleteUser();
//        verify(userRepository, times(0)).delete(user1);
//    }

}