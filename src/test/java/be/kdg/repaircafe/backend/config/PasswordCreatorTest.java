package be.kdg.repaircafe.backend.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BackendContextConfig.class)
public class PasswordCreatorTest
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createDummyDataPasswords()
    {
        System.out.println("clarence : " + passwordEncoder.encode("clarence"));
        System.out.println("scott : " + passwordEncoder.encode("scott"));
        System.out.println("john : " + passwordEncoder.encode("john"));
        System.out.println("peter : " + passwordEncoder.encode("peter"));
        System.out.println("jacky : " + passwordEncoder.encode("jacky"));
        System.out.println();
    }
}
