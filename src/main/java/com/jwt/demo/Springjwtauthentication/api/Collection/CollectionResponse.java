package com.jwt.demo.Springjwtauthentication.api.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponse {

    private Integer photographerId;
    private String previewUrl;
    private String description;
    private List<ImageUrlsReq> imageUrlsReqs =  new ArrayList<>();
}
