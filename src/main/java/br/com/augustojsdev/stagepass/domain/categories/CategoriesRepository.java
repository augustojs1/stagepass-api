package br.com.augustojsdev.stagepass.domain.categories;

import br.com.augustojsdev.stagepass.domain.categories.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository< Category, UUID> {
    Optional<Category> findByName(String name);
}
