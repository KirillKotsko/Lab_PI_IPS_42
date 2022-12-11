package ua.kotsko.project.Examinator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.kotsko.project.Examinator.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
