
package com.AmaruPortafolio.Amaruta.Repository;

import com.AmaruPortafolio.Amaruta.Entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Long>{
    
}
