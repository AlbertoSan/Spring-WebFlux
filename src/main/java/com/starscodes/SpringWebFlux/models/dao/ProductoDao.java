package com.starscodes.SpringWebFlux.models.dao;

import com.starscodes.SpringWebFlux.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {
}
