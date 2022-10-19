package com.AmaruPortafolio.Amaruta.Repository;

import com.AmaruPortafolio.Amaruta.Entity.Educacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface REducacion extends JpaRepository<Educacion, Integer>{
    public Optional <Educacion> findByNombreE(String nombreE);
    public boolean existsByNombreE(String nombreE);
}
