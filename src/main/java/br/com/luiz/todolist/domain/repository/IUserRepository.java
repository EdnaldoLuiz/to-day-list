package br.com.luiz.todolist.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.luiz.todolist.domain.model.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    UserDetails findByLogin(String login);
}
