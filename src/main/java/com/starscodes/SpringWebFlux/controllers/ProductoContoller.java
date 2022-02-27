package com.starscodes.SpringWebFlux.controllers;

import com.starscodes.SpringWebFlux.models.dao.ProductoDao;
import com.starscodes.SpringWebFlux.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Locale;


@Controller
public class ProductoContoller {

    @Autowired
    private ProductoDao productoDao;

    private static final Logger log = LoggerFactory.getLogger(ProductoContoller.class);

    @GetMapping({"/listar", "/"})
    public String listar(Model model){

        //Obtenemos primero un listado Flux de productos de la bd de mongo
        Flux<Producto> productoFlux = productoDao.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }); //Aqui no es necesario subscribirse a la vista ya que lo hará automaticamente

        productoFlux.subscribe(producto -> log.info(producto.getNombre())); //Añadimos otro subcritor para el log

        //Añadimos los productos a la vista mediante el modelo
        model.addAttribute("productos", productoFlux);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/listar-datadriver")
    public String listarDataDriver(Model model){

        //Obtenemos primero un listado Flux de productos de la bd de mongo
        Flux<Producto> productoFlux = productoDao.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).delayElements(Duration.ofSeconds(1));

        productoFlux.subscribe(producto -> log.info(producto.getNombre())); //Añadimos otro subcritor para el log

        //Añadimos los productos a la vista mediante el modelo
        //Con ReactiveDataDriver podemos setear el buffer y mostar los elementos en la vista según vayan apareciendo.
        model.addAttribute("productos", new ReactiveDataDriverContextVariable(productoFlux, 1));
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/listar-full")
    public String listarFull(Model model){

        //Obtenemos primero un listado Flux de productos de la bd de mongo
        Flux<Producto> productoFlux = productoDao.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).repeat(5000);

        //Añadimos los productos a la vista mediante el modelo
        model.addAttribute("productos", productoFlux);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/listar-chunked")
    public String listarChunked(Model model){

        //Obtenemos primero un listado Flux de productos de la bd de mongo
        Flux<Producto> productoFlux = productoDao.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).repeat(5000);

        //Añadimos los productos a la vista mediante el modelo
        model.addAttribute("productos", productoFlux);
        model.addAttribute("titulo", "Listado de productos");
        return "listar-chunked";
    }
}
