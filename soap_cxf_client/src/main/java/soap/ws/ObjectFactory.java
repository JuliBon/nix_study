
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

    private final static QName _NotUniqueLoginException_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "NotUniqueLoginException");
    private final static QName _UpdateUser_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "updateUser");
    private final static QName _NotUniqueEmailException_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "NotUniqueEmailException");
    private final static QName _GetUser_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "getUser");
    private final static QName _CreateUserResponse_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "createUserResponse");
    private final static QName _GetUserResponse_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "getUserResponse");
    private final static QName _DeleteUserResponse_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "deleteUserResponse");
    private final static QName _GetUsersResponse_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "getUsersResponse");
    private final static QName _VerifyUserExistenceResponse_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "verifyUserExistenceResponse");
    private final static QName _UpdateUserResponse_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "updateUserResponse");
    private final static QName _User_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "user");
    private final static QName _CreateUser_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "createUser");
    private final static QName _DeleteUser_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "deleteUser");
    private final static QName _GetUsers_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "getUsers");
    private final static QName _VerifyUserExistence_QNAME = new QName("http://service.study.bondarenko.nixsolutions.com/", "verifyUserExistence");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: soap.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUsers }
     * 
     */
    public GetUsers createGetUsers() {
        return new GetUsers();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link GetUsersResponse }
     * 
     */
    public GetUsersResponse createGetUsersResponse() {
        return new GetUsersResponse();
    }

    /**
     * Create an instance of {@link VerifyUserExistenceResponse }
     * 
     */
    public VerifyUserExistenceResponse createVerifyUserExistenceResponse() {
        return new VerifyUserExistenceResponse();
    }

    /**
     * Create an instance of {@link GetUserResponse }
     * 
     */
    public GetUserResponse createGetUserResponse() {
        return new GetUserResponse();
    }

    /**
     * Create an instance of {@link UpdateUser }
     * 
     */
    public UpdateUser createUpdateUser() {
        return new UpdateUser();
    }

    /**
     * Create an instance of {@link VerifyUserExistence }
     * 
     */
    public VerifyUserExistence createVerifyUserExistence() {
        return new VerifyUserExistence();
    }

    /**
     * Create an instance of {@link DeleteUserResponse }
     * 
     */
    public DeleteUserResponse createDeleteUserResponse() {
        return new DeleteUserResponse();
    }

    /**
     * Create an instance of {@link UpdateUserResponse }
     * 
     */
    public UpdateUserResponse createUpdateUserResponse() {
        return new UpdateUserResponse();
    }

    /**
     * Create an instance of {@link NotUniqueEmailException }
     * 
     */
    public NotUniqueEmailException createNotUniqueEmailException() {
        return new NotUniqueEmailException();
    }

    /**
     * Create an instance of {@link GetUser }
     * 
     */
    public GetUser createGetUser() {
        return new GetUser();
    }

    /**
     * Create an instance of {@link NotUniqueLoginException }
     * 
     */
    public NotUniqueLoginException createNotUniqueLoginException() {
        return new NotUniqueLoginException();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link DeleteUser }
     * 
     */
    public DeleteUser createDeleteUser() {
        return new DeleteUser();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotUniqueLoginException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "NotUniqueLoginException")
    public JAXBElement<NotUniqueLoginException> createNotUniqueLoginException(NotUniqueLoginException value) {
        return new JAXBElement<NotUniqueLoginException>(_NotUniqueLoginException_QNAME, NotUniqueLoginException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "updateUser")
    public JAXBElement<UpdateUser> createUpdateUser(UpdateUser value) {
        return new JAXBElement<UpdateUser>(_UpdateUser_QNAME, UpdateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotUniqueEmailException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "NotUniqueEmailException")
    public JAXBElement<NotUniqueEmailException> createNotUniqueEmailException(NotUniqueEmailException value) {
        return new JAXBElement<NotUniqueEmailException>(_NotUniqueEmailException_QNAME, NotUniqueEmailException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "getUser")
    public JAXBElement<GetUser> createGetUser(GetUser value) {
        return new JAXBElement<GetUser>(_GetUser_QNAME, GetUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "getUserResponse")
    public JAXBElement<GetUserResponse> createGetUserResponse(GetUserResponse value) {
        return new JAXBElement<GetUserResponse>(_GetUserResponse_QNAME, GetUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "deleteUserResponse")
    public JAXBElement<DeleteUserResponse> createDeleteUserResponse(DeleteUserResponse value) {
        return new JAXBElement<DeleteUserResponse>(_DeleteUserResponse_QNAME, DeleteUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "getUsersResponse")
    public JAXBElement<GetUsersResponse> createGetUsersResponse(GetUsersResponse value) {
        return new JAXBElement<GetUsersResponse>(_GetUsersResponse_QNAME, GetUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyUserExistenceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "verifyUserExistenceResponse")
    public JAXBElement<VerifyUserExistenceResponse> createVerifyUserExistenceResponse(VerifyUserExistenceResponse value) {
        return new JAXBElement<VerifyUserExistenceResponse>(_VerifyUserExistenceResponse_QNAME, VerifyUserExistenceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "updateUserResponse")
    public JAXBElement<UpdateUserResponse> createUpdateUserResponse(UpdateUserResponse value) {
        return new JAXBElement<UpdateUserResponse>(_UpdateUserResponse_QNAME, UpdateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "user")
    public JAXBElement<User> createUser(User value) {
        return new JAXBElement<User>(_User_QNAME, User.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "createUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "deleteUser")
    public JAXBElement<DeleteUser> createDeleteUser(DeleteUser value) {
        return new JAXBElement<DeleteUser>(_DeleteUser_QNAME, DeleteUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "getUsers")
    public JAXBElement<GetUsers> createGetUsers(GetUsers value) {
        return new JAXBElement<GetUsers>(_GetUsers_QNAME, GetUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyUserExistence }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.study.bondarenko.nixsolutions.com/", name = "verifyUserExistence")
    public JAXBElement<VerifyUserExistence> createVerifyUserExistence(VerifyUserExistence value) {
        return new JAXBElement<VerifyUserExistence>(_VerifyUserExistence_QNAME, VerifyUserExistence.class, null, value);
    }

}
