package ecommerce.springbootecommerce.exception;

public class SpringEcommerceException extends RuntimeException {

    public SpringEcommerceException(String exmessage,Exception exception) {
        super(exmessage,exception);
    }

    public SpringEcommerceException(String exmessage) {
        super(exmessage);
    }
}
