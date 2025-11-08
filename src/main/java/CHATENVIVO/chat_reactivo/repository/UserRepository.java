package CHATENVIVO.chat_reactivo.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import CHATENVIVO.chat_reactivo.model.User;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Integer> {

    @Query("""
        SELECT id_user AS idUser, full_name AS fullName
        FROM message.user
        WHERE id_user = :idUser
    """)
    Mono<User> findByIdUser(Integer idUser);
}
