package br.com.augustojsdev.stagepass.domain.users;

import br.com.augustojsdev.stagepass.domain.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @NativeQuery("SELECT * FROM users u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
