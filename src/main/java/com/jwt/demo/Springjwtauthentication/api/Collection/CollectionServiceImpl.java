package com.jwt.demo.Springjwtauthentication.api.Collection;


import com.jwt.demo.Springjwtauthentication.user.User;
import com.jwt.demo.Springjwtauthentication.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUrlsRepository imageUrlsRepository;

    @Override
    public CollectionResponse addNewCollection(CollectionReq collectionsReq, String userId){

        Optional<User> user = userRepository.findByEmail(userId);

        if(!user.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User ".concat(userId).concat(" is an invalid user for this request"));
        }

        Collections collections = new Collections(user.get().getId(), collectionsReq.getPreviewUrl(), collectionsReq.getDescription());

        Collections addedCollection = repository.save(collections);


        collectionsReq.getImagesUrls().forEach(imageUrls -> {
            ImagesUrls imagesUrls = new ImagesUrls(imageUrls.getUrls(), addedCollection.getId());
            imageUrlsRepository.save(imagesUrls);
        });

        List<ImageUrlsReq> imageUrlsRes = new ArrayList<>();

        for (ImagesUrls imagesUrls : imageUrlsRepository.findAllByColId(addedCollection.getId())) {
           imageUrlsRes.add( new ImageUrlsReq(imagesUrls.getUrls()));
        }

        return new CollectionResponse(addedCollection.getPhotographerId(), addedCollection.getPreviewUrl(), addedCollection.getDescription(), imageUrlsRes );
    }
}
