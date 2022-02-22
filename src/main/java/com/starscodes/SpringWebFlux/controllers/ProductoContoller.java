package com.starscodes.SpringWebFlux.controllers;

import com.starscodes.SpringWebFlux.models.dao.ProductoDao;
import com.starscodes.SpringWebFlux.models.documents.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;


@Controller
public class ProductoContoller {

    @Autowired
    private ProductoDao productoDao;

    @GetMapping({"/listar", "/"})
    public String listar(Model model){

        //Obtenemos primero un listado Flux de productos de la bd de mongo
        Flux<Producto> productoFlux = productoDao.findAll(); //Aqui no es necesario subscribirse a la vista ya que lo hará automaticamente

        //Añadimos los productos a la vista mediante el modelo
        model.addAttribute("productos", productoFlux);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }
}
