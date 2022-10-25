package com.AmaruPortafolio.Amaruta.Security.Controller;

import com.AmaruPortafolio.Amaruta.Security.Dto.JwtDto;
import com.AmaruPortafolio.Amaruta.Security.Dto.LoginUsuario;
import com.AmaruPortafolio.Amaruta.Security.Dto.NuevoUsuario;
import com.AmaruPortafolio.Amaruta.Security.Entity.Rol;
import com.AmaruPortafolio.Amaruta.Security.Entity.Usuario;
import com.AmaruPortafolio.Amaruta.Security.Enums.RolNombre;
import com.AmaruPortafolio.Amaruta.Security.Service.RolService;
import com.AmaruPortafolio.Amaruta.Security.Service.UsuarioService;
import com.AmaruPortafolio.Amaruta.Security.jwt.JwtProvider;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "https://frontendprueba-eb86c.web.app")
@CrossOrigin(origins = {"https://frontendprueba-eb86c.web.app","http://localhost:4200"})
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;
    
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email invalido"),HttpStatus.BAD_REQUEST);
        
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        
              if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("Este email ya existe"), HttpStatus.BAD_REQUEST);
              
              Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
              
              Set<Rol> roles = new HashSet<>();
              roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
              
              if(nuevoUsuario.getRoles().contains("admin"))
                  roles.add(rolService.getByRolNombre(RolNombre.ROLE_admin).get());
              usuario.setRoles(roles);
              usuarioService.save(usuario);
              
              return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
    }
    
    
    //cuidado con el bindingResult y su importacion por que me salia bindingResult
    @PostMapping("/login")
    public ResponseEntity<JwtDto> loggin(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(),loginUsuario.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        //se agrego jwtProvider.generateToken
        
        String jwt = jwtProvider.generateToken(authentication);
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
