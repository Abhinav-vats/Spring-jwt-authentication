package com.jwt.demo.Springjwtauthentication.api.Collection;

import java.util.List;

public interface CollectionService {

    CollectionResponse addNewCollection(CollectionReq collections, String userId);
}
