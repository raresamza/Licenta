package com.example.baeldunginheritance.repository;

import com.example.baeldunginheritance.collection.JWTToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JWTTokenRepository extends MongoRepository<JWTToken,String> {

//    @Query("db.Tokent inner join User u on t.user.id=u.id.find({\n" +
//            " \"$where\": \"this.ct t from Token t inner join User u on t.user.id == this.u.id\"" +
//            "},{\n" +
//            "   \"t\": 1" +
//            "}\n" +
//            ");")
/*    @Query("db.Token t inner join User u on t.user.id=u.id.find(  { $and : [  { u :userId },  { $or : [  { t.revoked : false }, { t.expired : false } ] }  ] }  )")

    //
    List<JWTToken> findAllValidTokensByUser(Integer userId);*/

    List<JWTToken> findAllByUserIdAndExpiredAndRevoked(String user_id, boolean expired, boolean revoked);

    Optional<JWTToken> findByToken(String token);
}
