package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yc.mhkif.aftas.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByIdentityNumber(String idNum);
    User findByEmail(String email);

}
