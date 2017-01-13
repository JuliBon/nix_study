import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.h2.api.ErrorCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import soap.ws.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

@ContextConfiguration(locations = "classpath:application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "classpath:/test_data/InitialDataSet.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL)
public class SoapUserServiceTest {

    @Autowired
    private SoapUserService soapUserService;

    private User user1;
    private User user2;
    private User newUser;

    public SoapUserServiceTest() throws ParseException, DatatypeConfigurationException {

        Role roleAdmin = new Role();
        roleAdmin.setId(1L);
        roleAdmin.setName("ADMIN");

        Role roleUser = new Role();
        roleUser.setId(2L);
        roleUser.setName("USER");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar c = new GregorianCalendar();

        user1 = new User();
        user1.setId(1L);
        user1.setLogin("yulya");
        user1.setPassword("Pass123");
        user1.setEmail("yulya@mail.ru");
        user1.setFirstName("yuliya");
        user1.setLastName("bondarenko");
        user1.setRole(roleAdmin);
        c.setTime(formatter.parse("1993-01-10"));
        XMLGregorianCalendar birthday = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        user1.setBirthday(birthday);

        user2 = new User();
        user2.setId(2L);
        user2.setLogin("ivan");
        user2.setPassword("Pass123");
        user2.setEmail("ivan@mail.ru");
        user2.setFirstName("ivan");
        user2.setLastName("grozniy");
        user2.setRole(roleUser);
        c.setTime(formatter.parse("1530-09-03"));
        birthday = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        user2.setBirthday(birthday);

        newUser = new User();
        newUser.setId(3L);
        newUser.setLogin("nata");
        newUser.setPassword("Agent007");
        newUser.setEmail("nata@mail.ru");
        newUser.setFirstName("nataliya");
        newUser.setLastName("bondarenko");
        newUser.setRole(roleUser);

        c.setTime(formatter.parse("1991-09-19"));
        birthday = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        newUser.setBirthday(birthday);
    }

    private void assertUserEquals(User userExpected, User userActual) {
        Assert.assertEquals(userExpected.getLogin(), userActual.getLogin());
        Assert.assertEquals(userExpected.getPassword(), userActual.getPassword());
        Assert.assertEquals(userExpected.getEmail(), userActual.getEmail());
        Assert.assertEquals(userExpected.getFirstName(), userActual.getFirstName());
        Assert.assertEquals(userExpected.getLastName(), userActual.getLastName());
        Assert.assertEquals(userExpected.getBirthday(), userActual.getBirthday());
        Assert.assertEquals(userExpected.getRole().getId(), userActual.getRole().getId());
    }

    @Test
    public void getUser() throws UserNotFoundException_Exception {
        assertUserEquals(user1, soapUserService.getUser(user1.getId()));
    }

    @Test(expected = UserNotFoundException_Exception.class)
    public void getUserNotExist() throws UserNotFoundException_Exception {
        soapUserService.getUser(newUser.getId());
    }

    @Test
    public void getUsers() throws IOException {
        List<User> users = soapUserService.getUsers();
        Assert.assertEquals(users.size(), 2);

        assertUserEquals(user1, users.get(0));
        assertUserEquals(user2, users.get(1));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createUser() throws NotUniqueLoginException_Exception, NotUniqueEmailException_Exception, ValidationException_Exception {
        Assert.assertNotNull(soapUserService.createUser(newUser));
    }

    @Test(expected = NotUniqueLoginException_Exception.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueLogin() throws NotUniqueLoginException_Exception, NotUniqueEmailException_Exception, ValidationException_Exception {
        newUser.setLogin(user1.getLogin());
        soapUserService.createUser(newUser);
    }

    @Test(expected = NotUniqueEmailException_Exception.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueEmail() throws NotUniqueLoginException_Exception, NotUniqueEmailException_Exception, ValidationException_Exception {
        newUser.setEmail(user1.getEmail());
        soapUserService.createUser(newUser);
    }

    @Test(expected = ValidationException_Exception.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserInvalid() throws NotUniqueLoginException_Exception, NotUniqueEmailException_Exception, ValidationException_Exception {
        newUser.setPassword("invalid");
        soapUserService.createUser(newUser);
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUser() throws NotUniqueEmailException_Exception, ValidationException_Exception {
        user1.setPassword("Agent007");
        soapUserService.updateUser(user1);
    }

    @Test(expected = NotUniqueEmailException_Exception.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserNotUniqueEmail() throws NotUniqueEmailException_Exception, ValidationException_Exception {
        user1.setEmail(user2.getEmail());
        soapUserService.updateUser(user1);
    }

    @Test(expected = ValidationException_Exception.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserInvalid() throws NotUniqueEmailException_Exception, ValidationException_Exception {
        user1.setPassword("invalid");
        soapUserService.updateUser(user1);
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void deleteUser() throws UserNotFoundException_Exception {
        soapUserService.deleteUser(user1.getId());
    }

    @Test(expected = UserNotFoundException_Exception.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void deleteUserNotExisting() throws UserNotFoundException_Exception {
        soapUserService.deleteUser(newUser.getId());
    }
}
