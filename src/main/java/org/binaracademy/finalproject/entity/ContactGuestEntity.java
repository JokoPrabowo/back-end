package org.binaracademy.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contact_guest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContactGuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "no_telp", nullable = false)
    private String noTelp;
    @Column(name = "email", nullable = false)
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
