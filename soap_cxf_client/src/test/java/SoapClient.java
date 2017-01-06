import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import soap.ws.HelloSoap;

@ContextConfiguration(locations = "classpath:application-context.xml")

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class})
public class SoapClient {
    @Autowired
    private HelloSoap helloSoap;

    @Test
    public void test() {
        System.out.println(helloSoap.sayHelloTo("Yulya"));
        System.out.println(helloSoap.getGoods());
    }
}
