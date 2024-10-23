package ecommerce.springbootecommerce.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class SignupRequest {

@NotEmpty(message = "Email is required")
@Email
private String email;

@NotBlank(message = "Password is required")
private String password;

@NotBlank(message = "Name is required")
private String name;


}
