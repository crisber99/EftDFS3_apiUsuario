package it.pkg.Usuario;

import org.springframework.web.bind.annotation.RestController;

import it.pkg.Exception.ForoNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuario")
public class UsuariosController {

    @Autowired
    private UsuariosService userService;
    private static final Logger log = LoggerFactory.getLogger(UsuariosController.class);

    @PostMapping("/add")
    public void addUser(@RequestBody Usuarios user) {
        userService.creaUsuario(user);
    }
    
    @GetMapping("/{id}")
    public EntityModel<Usuarios> getUsuarioByid(@PathVariable Long id) {
        log.info("getRolByid");
        Optional<Usuarios> usu = userService.getUsuario(id);
        if(usu.isPresent()){
            return EntityModel.of(usu.get(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioByid(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuario()).withRel("all-Usuarios"));
        }
        else{
            throw new ForoNotFoundException("Usuario no funciona con el id: " + id);
        }  
    }
    
    @GetMapping
    public List<Usuarios> getAllUsuario() {
        return userService.getAllUsuario();
    }

    @PutMapping("/{id}")
    public Usuarios updUsuarios(@PathVariable Long id, @RequestBody Usuarios user) {
        return userService.updUsuario(id, user);
    }

    @DeleteMapping("/{id}")
    public void delUsuario(@PathVariable Long id){
        userService.delUsuario(id);
    }

    @PostMapping("/login")
    public Usuarios Login(@RequestBody UsuarioAuth user) {
        String rut = user.getRut();
        String pass = user.getPass();

        Usuarios usr = userService.getLogin(rut, pass);

        if(usr == null){
            throw new ForoNotFoundException("Nombre de usuario o contraseña no validos.");
        }

        return usr;
    }
    
    

}
