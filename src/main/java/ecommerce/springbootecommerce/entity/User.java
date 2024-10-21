package ecommerce.springbootecommerce.entity;

import ecommerce.springbootecommerce.enums.UserRole;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Data
@Table (name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;


    @Getter
    @Setter
    private String name;

    private UserRole role;

    private Long phoneNumber;

    private Instant created;
    private boolean enabled;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] images;


}
