package com.jwt.demo.Springjwtauthentication.api.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagesUrls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urls;

    private Long colId;

    public ImagesUrls(String urls, Long colId) {
        this.urls = urls;
        this.colId = colId;
    }
}
