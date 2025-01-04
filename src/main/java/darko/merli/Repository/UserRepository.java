package darko.merli.Repository;

import darko.merli.Model.UserDTOS.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@EnableJpaRepositories
//@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByUsername(String username);
}
