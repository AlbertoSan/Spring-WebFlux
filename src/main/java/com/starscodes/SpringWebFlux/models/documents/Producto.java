package com.starscodes.SpringWebFlux.models.documents;

import lombok.Data;
import org.reactivestreams.Publisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "productos") //En vez de Entity se anota de esta forma para Mongo
public class Producto {

    @Id
    private String id;

    private String nombre;

    private Double precio;

    private Date createAt;

    public Producto() {} //Constructor vacio para que sea manejado por Spring

    public Producto(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

}
