package com.example.videojuegos.controllers;

import com.example.videojuegos.entities.Videojuego;
import com.example.videojuegos.services.ServiceCategoria;
import com.example.videojuegos.services.ServiceEstudio;
import com.example.videojuegos.services.ServiceVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

/*
 * Trabajaremos con thymeleaf, el mismo sirve para modificar archivos html con parámetros que recibamos a partir
 * del controlador, es decir, podemos personalizar con las cosas que nosotros queramos. Seguiremos un patron
 * de diseño mvc, MODELO VISTA CONTROLADOR, siendo el controlador el encargado de comunicar las vistas con
 * el modelo
 * */
@Controller
public class ControllerVideojuego {

    @Autowired
    private ServiceVideojuego serviceVideojuego;

    @Autowired
    private ServiceEstudio serviceEstudio;

    @Autowired
    private ServiceCategoria serviceCategoria;


    @GetMapping("/inicio") //Esta sera la url con la que se comunicara
    public String inicio(Model model) {
        try {
            List<Videojuego> videojuegos = serviceVideojuego.findAllByActivo();
            model.addAttribute("videojuegos", videojuegos);
            /*
             * Este metodo recibe dos parametros, el primero un String, el cual será el nombre del atributo,
             * el segundo el valor del atributo en sí.
             * */
            return "Views/inicio"; //tiene que retornar la ruta al documento HTML
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalleVideojuego(Model model, @PathVariable("id") long id) { //Capturamos el id que enviaremos en url
        try {
            Videojuego videojuego = serviceVideojuego.findByIdAndActivo(id);
            model.addAttribute("videojuego", videojuego);
            return "Views/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/busqueda")
    public String busquedaVideojuego(Model model, @RequestParam(value = "query", required = false) String titulo) {
        try {
            List<Videojuego> videojuegos = serviceVideojuego.findByTitulo(titulo);
            model.addAttribute("videojuegos", videojuegos);
            return "Views/busqueda";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/crud")
    public String crudVideojuego(Model model) {
        try {
            List<Videojuego> videojuegos = serviceVideojuego.findAll();
            model.addAttribute("videojuegos", videojuegos);
            return "Views/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/videojuego/{id}")
    public String formularioVideojuego(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("categorias", serviceCategoria.findAll());
            model.addAttribute("estudios", serviceEstudio.findAll());
            if (id == 0) {
                model.addAttribute("videojuego", new Videojuego());
            } else {
                model.addAttribute("videojuego", serviceVideojuego.findById(id));
            }
            return "Views/Formularios/videojuego";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/videojuego/{id}")
    public String guardarVideojuego(
            @RequestParam("archivo") MultipartFile archivo,
            @Valid @ModelAttribute("videojuego") Videojuego videojuego,
            BindingResult result,
            Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("categorias", serviceCategoria.findAll());
            model.addAttribute("estudios", serviceEstudio.findAll());
            if (result.hasErrors()) {
                return "Views/Formularios/videojuego";
            }
            String ruta = "C://Videojuegos/imagenes";
            int index = archivo.getOriginalFilename().indexOf(".");
            String extension = "";
            extension = "." + archivo.getOriginalFilename().substring(index + 1);
            String nombreFoto = Calendar.getInstance().getTimeInMillis() + extension;
            Path rutaAbsoluta = id != 0 ? Paths.get(ruta + "//" + videojuego.getImagen()) :
                    Paths.get(ruta + "//" + nombreFoto);
            if (id == 0) {
                if (archivo.isEmpty()) {
                    model.addAttribute("imageErrorMsg", "La imagen es requerida");
                    return "Views/Formularios/videojuego";
                }
                if (!validarExtension(archivo)) {
                    model.addAttribute("imageErrorMsg", "La extension no es valida");
                    return "Views/Formularios/videojuego";
                }
                if (archivo.getSize() >= 15000000) {
                    model.addAttribute("imageErrorMsg", "No puede exceder de 15MB");
                    return "Views/Formularios/videojuego";
                }
                Files.write(rutaAbsoluta, archivo.getBytes());
                videojuego.setImagen(nombreFoto);
                serviceVideojuego.saveOne(videojuego);
            } else {
                if (!archivo.isEmpty()) {
                    if (!validarExtension(archivo)) {
                        model.addAttribute("imageErrorMsg", "La extension no es valida");
                        return "Views/Formularios/videojuego";
                    }
                    if (archivo.getSize() >= 15000000) {
                        model.addAttribute("imageErrorMsg", "No puede exceder de 15MB");
                        return "Views/Formularios/videojuego";
                    }
                    Files.write(rutaAbsoluta, archivo.getBytes());
                }
                serviceVideojuego.updateOne(videojuego, id);
            }
            return "Views/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage() + "hola");
            return "error";
        }
    }

    @GetMapping("/eliminar/videojuego/{id}")
    public String eliminarVideojuego(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("videojuego", serviceVideojuego.findById(id));
            return "Views/Formularios/eliminar";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/videojuego/{id}")
    public String desactivarVideojuego(Model model, @PathVariable("id") long id) {
        try {
            serviceVideojuego.deleteById(id);
            return "redirect:/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    public boolean validarExtension(MultipartFile archivo) {
        try {
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
