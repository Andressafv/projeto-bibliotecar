package org.iftm.modelo_api_rest.repositories;

import java.util.List;
import java.util.Optional;

import org.iftm.modelo_api_rest.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNome(String nome);

    List<Usuario> findByTipoUsuario(String tipoUsuario);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipo AND u.bloqueio IS NULL")
    List<Usuario> findAtivosPorTipo(@Param("tipo") String tipo);
}
