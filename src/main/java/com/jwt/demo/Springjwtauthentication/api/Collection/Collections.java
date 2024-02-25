package com.jwt.demo.Springjwtauthentication.api.Collection;


import com.jwt.demo.Springjwtauthentication.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "collections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer photographerId;
    private String previewUrl;
    @Length(min = 5, max = 100)
    private String description;


    public Collections(Integer photographerId, String previewUrl, String description) {
        this.photographerId = photographerId;
        this.previewUrl = previewUrl;
        this.description = description;
    }
}
