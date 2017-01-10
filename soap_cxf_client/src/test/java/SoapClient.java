import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.DataSetLoader;
import org.dbunit.Assertion;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
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
@DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
public class SoapClient {

    @Autowired
    private SoapUserService soapUserService;

    private User user1;
    private User user2;
    private User newUser;

    public SoapClient() throws ParseException, DatatypeConfigurationException {

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
        user1.setPassword("Qwe1");
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
        user2.setPassword("Qwe1");
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
    public void getUser() {
        GetUserResult result = soapUserService.getUser(user1.getId());
        Assert.assertEquals(ResultCode.OK, result.getResultCode());
        assertUserEquals(user1, result.getUser());
    }

    @Test
    public void getUserNotExist() {
        GetUserResult result = soapUserService.getUser(99L);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.USER_NOT_FOUND, result.getErrorCode());
    }

    @Test
    public void getUsers() throws IOException {
        GetUsersResult result = soapUserService.getUsers();
        Assert.assertEquals(ResultCode.OK, result.getResultCode());

        List<User> users = result.getUserList();
        Assert.assertEquals(users.size(), 2);

        assertUserEquals(user1, users.get(0));
        assertUserEquals(user2, users.get(1));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createUser() {
        UserCreateResult result = soapUserService.createUser(newUser);
        Assert.assertEquals(ResultCode.OK, result.getResultCode());
        Assert.assertNotNull(result.getId());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueLogin() {
        newUser.setLogin(user1.getLogin());

        UserCreateResult result = soapUserService.createUser(newUser);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.NOT_UNIQUE_LOGIN, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueEmail() {
        newUser.setEmail(user1.getEmail());
        UserCreateResult result = soapUserService.createUser(newUser);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.NOT_UNIQUE_EMAIL, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserInvalid() {
        newUser.setPassword("invalid");
        UserCreateResult result = soapUserService.createUser(newUser);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.INVALID_USER, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUser() {
        user1.setPassword("Agent007");
        WebServiceResult result = soapUserService.updateUser(user1);
        Assert.assertEquals(ResultCode.OK, result.getResultCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserInvalid() {
        user1.setPassword("invalid");
        WebServiceResult result = soapUserService.updateUser(user1);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.INVALID_USER, result.getErrorCode());

    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserNotUniqueEmail() {
        user1.setEmail(user2.getEmail());
        WebServiceResult result = soapUserService.updateUser(user1);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.NOT_UNIQUE_EMAIL, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void deleteUser() {
        WebServiceResult result = soapUserService.deleteUser(user1.getId());
        Assert.assertEquals(ResultCode.OK, result.getResultCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void deleteUserNotExisting() {
        WebServiceResult result = soapUserService.deleteUser(99L);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.USER_NOT_FOUND, result.getErrorCode());
    }
}
