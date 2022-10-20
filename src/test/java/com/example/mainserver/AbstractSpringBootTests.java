package com.example.mainserver;

import com.main.server.repository.RoleRepository;
import com.main.server.repository.UserRepository;
import com.main.server.service.impl.UserServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AbstractSpringBootTests {
    @Mock
    protected UserRepository userRepository;
    @Mock
    protected RoleRepository roleRepository;
    @InjectMocks
    protected UserServiceImpl userService;

}
