package com.U2A5SB.CasinoRoyale.repository;

import com.U2A5SB.CasinoRoyale.model.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {

    List<Tiro> findByPartidaId(Long partidaId);
}