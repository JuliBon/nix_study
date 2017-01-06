import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import soap.ws.Role;
import soap.ws.User;
import soap.ws.UserService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@ContextConfiguration(locations = "classpath:application-context.xml")

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class})
public class SoapClient {
    @Autowired
    private UserService userService;

    private User user1;
    private User newUser;

    public SoapClient() throws ParseException, DatatypeConfigurationException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Role roleAdmin = new Role();
        roleAdmin.setId(1L);
        roleAdmin.setName("ADMIN");

        Role roleUser = new Role();
        roleUser.setId(2L);
        roleUser.setName("USER");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        user1 = new User();
        user1.setId(1L);
        user1.setLogin("yulya");
        user1.setPassword("12345");
        user1.setEmail("yulya@mail.ru");
        user1.setFirstName("yuliya");
        user1.setLastName("bondarenko");
        user1.setRole(roleAdmin);

        GregorianCalendar c = new GregorianCalendar();

        c.setTime(formatter.parse("1993-01-10"));
        XMLGregorianCalendar birthday = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        user1.setBirthday(birthday);

        newUser = new User();
        newUser.setId(3L);
        newUser.setLogin("nata");
        newUser.setPassword("Agent007");
        newUser.setEmail("nata@mail.ru");
        newUser.setFirstName("nataliya");
        newUser.setLastName("bondarenko");
        newUser.setRole(roleAdmin);

        c.setTime(formatter.parse("1991-09-19"));
        birthday = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        newUser.setBirthday(birthday);
    }

    @Test
    public void test() {
        System.out.println(userService.getUser("yulya"));
        System.out.println(userService.getUsers());
    }

    @Test
    public void getUserByLogin() {
        User yulya = userService.getUser("yulya");

        Assert.assertEquals(user1.getLogin(), yulya.getLogin());
        Assert.assertEquals(user1.getPassword(), yulya.getPassword());
        Assert.assertEquals(user1.getEmail(), yulya.getEmail());
        Assert.assertEquals(user1.getFirstName(), yulya.getFirstName());
        Assert.assertEquals(user1.getLastName(), yulya.getLastName());
        //Assert.assertEquals(user1.getBirthday(), yulya.getBirthday());
        Assert.assertEquals(user1.getRole().getId(), yulya.getRole().getId());
    }
}
