package com.fullstack.spring.app.Repository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  void deleteById(Long id);
}