package com.unitechApi;

import com.unitechApi.user.Repository.PassWordRepository;
import com.unitechApi.user.Repository.RoleRepository;
import com.unitechApi.user.Repository.UserRepository;
import com.unitechApi.user.model.ERole;
import com.unitechApi.user.model.PasswordEntity;
import com.unitechApi.user.model.Role;
import com.unitechApi.user.model.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class UnitechApplication implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PassWordRepository passWordRepository;

    public UnitechApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, PassWordRepository passWordRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passWordRepository = passWordRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(UnitechApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        User use = new User();
        PasswordEntity pass= new PasswordEntity();
        pass.setPassword(passwordEncoder.encode("admin"));
        use.setUsername("Admin");
        use.setSurName("surname");
        use.setDob(LocalDate.parse("1996-09-02"));
        use.setEmail("adminmain@gmail.com");
        use.setIndentification("Don't KnoW");
        use.setMaritalStatus("single");
        use.setNationality("India");
        use.setPhoneno("1234567890");
        use.setBloodGroup("b+");
        use.setNativePalace("Nikol");
        use.setPasswordEntity(pass);
        use.setPinCode(360003);
        use.setTelephoneNumber("1234567890");
        use.setAddress("Rajkot");

        User user = new User();
        PasswordEntity password = new PasswordEntity();
        password.setPassword(passwordEncoder.encode("admin@0987"));
        user.setUsername("Admin");
        user.setSurName("surname");
        user.setDob(LocalDate.parse("1996-09-02"));
        user.setEmail("admin@gmail.com");
        user.setIndentification("Don't KnoW");
        user.setMaritalStatus("single");
        user.setNationality("India");
        user.setPhoneno("9898985975");
        user.setBloodGroup("b+");
        user.setNativePalace("Nikol");
        user.setPasswordEntity(password);
        user.setPinCode(360003);
        user.setTelephoneNumber("9898985975");
        user.setAddress("Rajkot");

        User user1 = new User();
        PasswordEntity password1 = new PasswordEntity();
        password1.setPassword(passwordEncoder.encode("admin@987"));
        user1.setUsername("sub admin");
        user1.setSurName("surname");
        user1.setDob(LocalDate.parse("1996-09-02"));
        user1.setEmail("subadmin@gmail.com");
        user1.setIndentification("Don't KnoW");
        user1.setMaritalStatus("single");
        user1.setNationality("India");
        user1.setPhoneno("9913278903");
        user1.setBloodGroup("b+");
        user1.setNativePalace("Nikol");
        user1.setPasswordEntity(password1);
        user1.setPinCode(360003);
        user1.setTelephoneNumber("9913278903");
        user1.setAddress("Rajkot");

        User user2 = new User();
        PasswordEntity password2 = new PasswordEntity();
        password2.setPassword(passwordEncoder.encode("qc@123"));
        user2.setUsername("QC manager");
        user2.setSurName("surname");
        user2.setDob(LocalDate.parse("1996-09-02"));
        user2.setEmail("qc@gmail.com");
        user2.setIndentification("Don't KnoW");
        user2.setMaritalStatus("single");
        user2.setNationality("India");
        user2.setPhoneno("7016866827");
        user2.setBloodGroup("b+");
        user2.setNativePalace("Nikol");
        user2.setPasswordEntity(password2);
        user2.setPinCode(360003);
        user2.setTelephoneNumber("7016866827");
        user2.setAddress("Rajkot");

        User user3 = new User();
        PasswordEntity password3 = new PasswordEntity();
        password3.setPassword(passwordEncoder.encode("super1@1234"));
        user3.setDob(LocalDate.parse("1996-09-02"));
        user3.setSurName("surname");
        user3.setEmail("superviser1@gmail.com");
        user3.setUsername("SUPERVISOR 1");
        user3.setIndentification("Don't KnoW");
        user3.setMaritalStatus("single");
        user3.setNationality("India");
        user3.setPhoneno("7990283273");
        user3.setBloodGroup("b+");
        user3.setNativePalace("Nikol");
        user3.setPasswordEntity(password3);
        user3.setPinCode(360003);
        user3.setTelephoneNumber("7990283273");
        user3.setAddress("Rajkot");

        User user4 = new User();
        PasswordEntity password4 = new PasswordEntity();
        password4.setPassword(passwordEncoder.encode("super2@1234"));
        user4.setUsername("SUPERVISOR 2");
        user4.setSurName("surname");
        user4.setDob(LocalDate.parse("1996-09-02"));
        user4.setEmail("superviser2@gmail.com");
        user4.setIndentification("Don't KnoW");
        user4.setMaritalStatus("single");
        user4.setNationality("India");
        user4.setPhoneno("9687998416");
        user4.setBloodGroup("b+");
        user4.setNativePalace("Nikol");
        user4.setPasswordEntity(password4);
        user4.setPinCode(360003);
        user4.setTelephoneNumber("9687998416");
        user4.setAddress("Rajkot");


        User user5 = new User();
        PasswordEntity password5 = new PasswordEntity();
        password5.setPassword(passwordEncoder.encode("admin"));
        user5.setUsername("MAINTENANCE");
        user5.setSurName("surname");
        user5.setDob(LocalDate.parse("1996-09-02"));
        user5.setEmail("MAINTENANCE@gmail.com");
        user5.setIndentification("Don't KnoW");
        user5.setMaritalStatus("single");
        user5.setNationality("India");
        user5.setPhoneno("1234567891");
        user5.setBloodGroup("b+");
        user5.setNativePalace("Nikol");
        user5.setPasswordEntity(password5);
        user5.setPinCode(360003);
        user5.setTelephoneNumber("1234567891");
        user5.setAddress("Rajkot");

        User user6 = new User();
        PasswordEntity password6 = new PasswordEntity();
        password6.setPassword(passwordEncoder.encode("admin"));
        user6.setUsername("STORE");
        user6.setSurName("surname");
        user6.setDob(LocalDate.parse("1996-09-02"));
        user6.setEmail("STORE@gmail.com");
        user6.setIndentification("Don't KnoW");
        user6.setMaritalStatus("single");
        user6.setNationality("India");
        user6.setPhoneno("1234567892");
        user6.setBloodGroup("b+");
        user6.setNativePalace("Nikol");
        user6.setPasswordEntity(password6);
        user6.setPinCode(360003);
        user6.setTelephoneNumber("1234567892");
        user6.setAddress("Rajkot");

        User user7 = new User();
        PasswordEntity password7 = new PasswordEntity();
        password7.setPassword(passwordEncoder.encode("admin"));
        user7.setDob(LocalDate.parse("1996-09-02"));
        user7.setSurName("surname");
        user7.setEmail("GENERALMANAGER@gmail.com");
        user7.setUsername("GENERALMANAGER");
        user7.setIndentification("Don't KnoW");
        user7.setMaritalStatus("single");
        user7.setNationality("India");
        user7.setPhoneno("1234567893");
        user7.setBloodGroup("b+");
        user7.setNativePalace("Nikol");
        user7.setPasswordEntity(password7);
        user7.setPinCode(360003);
        user7.setTelephoneNumber("1234567893");
        user7.setAddress("Rajkot");

        User user8 = new User();
        PasswordEntity password8 = new PasswordEntity();
        password8.setPassword(passwordEncoder.encode("admin"));
        user8.setUsername("ACCOUNT");
        user8.setSurName("surname");
        user8.setDob(LocalDate.parse("1996-09-02"));
        user8.setEmail("ACCOUNT@gmail.com");
        user8.setIndentification("Don't KnoW");
        user8.setMaritalStatus("single");
        user8.setNationality("India");
        user8.setPhoneno("1234567894");
        user8.setBloodGroup("b+");
        user8.setNativePalace("Nikol");
        user8.setPasswordEntity(password8);
        user8.setPinCode(360003);
        user8.setTelephoneNumber("1234567894");
        user8.setAddress("Rajkot");

        //  user.setConfirmed(true);
        Role role = new Role(ERole.ROLE_ADMIN);
        Role role1 = new Role(ERole.ROLE_SUBADMIN);
        Role role2 = new Role(ERole.ROLE_GENERALMANAGER);
        Role role3 = new Role(ERole.ROLE_PURCHASER);
        Role role4 = new Role(ERole.ROLE_ACCOUNT);
        Role role5 = new Role(ERole.ROLE_QC);
        Role role6 = new Role(ERole.ROLE_SECURITY);
        Role role7 = new Role(ERole.ROLE_HR);
        Role role8 = new Role(ERole.ROLE_CASTER);
        Role role9 = new Role(ERole.ROLE_SUPERVISOR);
        Role role10 = new Role(ERole.ROLE_LEBOR);
        Role role11 = new Role(ERole.ROLE_MAINTENANCE);
        Role role12 = new Role(ERole.ROLE_STORE);


        List<User> DataCheck = userRepository.findAll();
        if (DataCheck.isEmpty()) {

            roleRepository.save(role);
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);
            roleRepository.save(role4);
            roleRepository.save(role5);
            roleRepository.save(role6);
            roleRepository.save(role7);
            roleRepository.save(role8);
            roleRepository.save(role9);
            roleRepository.save(role10);
            roleRepository.save(role11);
            roleRepository.save(role12);

            use.getRoles().add(role);
            use.getPasswordEntity().idUpdate(use);
            userRepository.save(use);

            user.getRoles().add(role);
            user.getPasswordEntity().idUpdate(user);
            userRepository.save(user);

            user1.getRoles().add(role1);
            user1.getPasswordEntity().idUpdate(user1);
            userRepository.save(user1);

            user2.getRoles().add(role5);
            user2.getPasswordEntity().idUpdate(user2);
            userRepository.save(user2);

            user3.getRoles().add(role9);
            user3.getPasswordEntity().idUpdate(user3);
            userRepository.save(user3);

            user4.getRoles().add(role9);
            user4.getPasswordEntity().idUpdate(user4);
            userRepository.save(user4);

            user5.getRoles().add(role11);
            user5.getPasswordEntity().idUpdate(user5);
            userRepository.save(user5);

            user6.getRoles().add(role12);
            user6.getPasswordEntity().idUpdate(user6);
            userRepository.save(user6);

            user7.getRoles().add(role2);
            user7.getPasswordEntity().idUpdate(user7);
            userRepository.save(user7);

            user8.getRoles().add(role4);
            user8.getPasswordEntity().idUpdate(user8);
            userRepository.save(user8);
        }
    }

}
