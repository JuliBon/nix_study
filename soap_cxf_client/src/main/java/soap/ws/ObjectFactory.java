
package soap.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the soap.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TestService_QNAME = new QName("http://ws.soap.study.bondarenko.nixsolutions.com/", "testService");
    private final static QName _GetGoods_QNAME = new QName("http://ws.soap.study.bondarenko.nixsolutions.com/", "getGoods");
    private final static QName _SayHelloTo_QNAME = new QName("http://ws.soap.study.bondarenko.nixsolutions.com/", "sayHelloTo");
    private final static QName _TestServiceResponse_QNAME = new QName("http://ws.soap.study.bondarenko.nixsolutions.com/", "testServiceResponse");
    private final static QName _Goods_QNAME = new QName("http://ws.soap.study.bondarenko.nixsolutions.com/", "goods");
    private final static QName _GetGoodsResponse_QNAME = new QName("http://ws.soap.study.bondarenko.nixsolutions.com/", "getGoodsResponse");
    private final static QName _SayHelloToResponse_QNAME = new QName("http://ws.soap.study.bondarenko.nixsolutions.com/", "sayHelloToResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: soap.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Goods }
     * 
     */
    public Goods createGoods() {
        return new Goods();
    }

    /**
     * Create an instance of {@link GetGoodsResponse }
     * 
     */
    public GetGoodsResponse createGetGoodsResponse() {
        return new GetGoodsResponse();
    }

    /**
     * Create an instance of {@link SayHelloTo }
     * 
     */
    public SayHelloTo createSayHelloTo() {
        return new SayHelloTo();
    }

    /**
     * Create an instance of {@link TestServiceResponse }
     * 
     */
    public TestServiceResponse createTestServiceResponse() {
        return new TestServiceResponse();
    }

    /**
     * Create an instance of {@link SayHelloToResponse }
     * 
     */
    public SayHelloToResponse createSayHelloToResponse() {
        return new SayHelloToResponse();
    }

    /**
     * Create an instance of {@link TestService }
     * 
     */
    public TestService createTestService() {
        return new TestService();
    }

    /**
     * Create an instance of {@link GetGoods }
     * 
     */
    public GetGoods createGetGoods() {
        return new GetGoods();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.soap.study.bondarenko.nixsolutions.com/", name = "testService")
    public JAXBElement<TestService> createTestService(TestService value) {
        return new JAXBElement<TestService>(_TestService_QNAME, TestService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoods }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.soap.study.bondarenko.nixsolutions.com/", name = "getGoods")
    public JAXBElement<GetGoods> createGetGoods(GetGoods value) {
        return new JAXBElement<GetGoods>(_GetGoods_QNAME, GetGoods.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloTo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.soap.study.bondarenko.nixsolutions.com/", name = "sayHelloTo")
    public JAXBElement<SayHelloTo> createSayHelloTo(SayHelloTo value) {
        return new JAXBElement<SayHelloTo>(_SayHelloTo_QNAME, SayHelloTo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.soap.study.bondarenko.nixsolutions.com/", name = "testServiceResponse")
    public JAXBElement<TestServiceResponse> createTestServiceResponse(TestServiceResponse value) {
        return new JAXBElement<TestServiceResponse>(_TestServiceResponse_QNAME, TestServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Goods }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.soap.study.bondarenko.nixsolutions.com/", name = "goods")
    public JAXBElement<Goods> createGoods(Goods value) {
        return new JAXBElement<Goods>(_Goods_QNAME, Goods.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.soap.study.bondarenko.nixsolutions.com/", name = "getGoodsResponse")
    public JAXBElement<GetGoodsResponse> createGetGoodsResponse(GetGoodsResponse value) {
        return new JAXBElement<GetGoodsResponse>(_GetGoodsResponse_QNAME, GetGoodsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloToResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.soap.study.bondarenko.nixsolutions.com/", name = "sayHelloToResponse")
    public JAXBElement<SayHelloToResponse> createSayHelloToResponse(SayHelloToResponse value) {
        return new JAXBElement<SayHelloToResponse>(_SayHelloToResponse_QNAME, SayHelloToResponse.class, null, value);
    }

}
