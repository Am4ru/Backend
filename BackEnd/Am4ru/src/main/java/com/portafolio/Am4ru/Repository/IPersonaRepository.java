
package com.portafolio.Am4ru.Repository;

import com.portafolio.Am4ru.Entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Long>{
    
}
