package sn.dev.crudProduit.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dev.crudProduit.Entities.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserDoa extends JpaRepository<User, Long> {

    // Rechercher un utilisateur par email (utile pour login ou vérification)
    Optional<User> findByEmail(String email);

    // Rechercher des utilisateurs par nom (contient)
    List<User> findAllByNomContaining(String nom);
}
