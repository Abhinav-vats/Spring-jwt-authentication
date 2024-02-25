package com.jwt.demo.Springjwtauthentication.api.Collection;

import com.jwt.demo.Springjwtauthentication.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionReq {

    private String previewUrl;

    @Length(min = 5, max = 100)
    private String description;

    private List<ImageUrlsReq> imagesUrls;
}
