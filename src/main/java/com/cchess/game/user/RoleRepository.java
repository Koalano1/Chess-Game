package com.cchess.game.user;

import com.cchess.game.model.entities.Role;
import com.cchess.game.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);

}
