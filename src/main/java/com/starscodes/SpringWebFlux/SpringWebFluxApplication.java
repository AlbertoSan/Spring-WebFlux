package com.starscodes.SpringWebFlux;

import com.starscodes.SpringWebFlux.models.dao.ProductoDao;
import com.starscodes.SpringWebFlux.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringWebFluxApplication implements CommandLineRunner {

	@Autowired
	private ProductoDao dao;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger log = LoggerFactory.getLogger(SpringWebFluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// No es recomendable en produccion
		mongoTemplate.dropCollection("productos").subscribe(); //No ocurre nada si no se subscribe ya que devuelve un mono

		Flux.just(new Producto("Monitor Samsung 34 Panel VA", 459.95),
				new Producto("Monitor Samsung 27 Curvo Panel VA", 159.95),
				new Producto("Procesador AMD 3900", 389.95),
				new Producto("Procesador AMD 1700x", 149.95),
				new Producto("Memoria RAM 32Gb GSKILL-RGB", 269.95),
				new Producto("Portatil ASUS", 399.99),
				new Producto("iPad Pro 11", 829.95),
				new Producto("Telefono Xiaomi Mi Mix 3", 249.95)
		)
				//.map(dao::save) //El map solo devuelve un Mono y no un Flux de productos.
				//flatMap aplana los Mono y lo convierte en un producto
				.flatMap(producto -> {
					producto.setCreateAt(new Date());
					return dao.save(producto);
				}) //flatMap aplana los Mono y lo convierte en un producto
				.subscribe(producto -> log.info("Insert: " + producto.getId() + " " + producto.getNombre()));

	}
}
