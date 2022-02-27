package com.starscodes.SpringWebFlux.controllers;

import com.starscodes.SpringWebFlux.models.dao.ProductoDao;
import com.starscodes.SpringWebFlux.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoDao productoDao;

    private static final Logger log = LoggerFactory.getLogger(ProductoContoller.class);

    @GetMapping
    public Flux<Producto> index(){
        Flux<Producto> productoFlux = productoDao.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).doOnNext(producto -> log.info(producto.getNombre()));

        return productoFlux;
    }

    @GetMapping("/{id}")
    public Mono<Producto> show(@PathVariable String id){

        //Forma sencilla de recuperar un Mono de la bd
        //Mono<Producto> producto = productoDao.findById(id);

        Flux<Producto> productoFlux = productoDao.findAll();

        //Forma para convertir de un Flux en Mono por el id
        Mono<Producto> producto = productoFlux.filter(p -> p.getId().equals(id)) //Con filter podemos filtrar los resultados
                .next() // con este operador podemos convertir el FLux en un Mono
                .doOnNext(prod -> log.info(prod.getNombre()));;

        return producto;
    }
}
