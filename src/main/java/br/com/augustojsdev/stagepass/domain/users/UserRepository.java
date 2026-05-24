package br.com.augustojsdev.stagepass.domain.users;

import br.com.augustojsdev.stagepass.domain.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
