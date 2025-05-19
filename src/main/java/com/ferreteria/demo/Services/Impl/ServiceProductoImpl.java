package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.ProductoDTOConverter;
import com.ferreteria.demo.DTO.Producto.CrearProductoDTO;
import com.ferreteria.demo.DTO.Producto.ListarProductoDTO;
import com.ferreteria.demo.DTO.Producto.ProductoDTO;
import com.ferreteria.demo.Repositories.RepositoryFerreteria;
import com.ferreteria.demo.Repositories.RepositoryProducto;
import com.ferreteria.demo.Repositories.Entities.Ferreteria;
import com.ferreteria.demo.Repositories.Entities.Producto;
import com.ferreteria.demo.Services.ServiceProducto;

@Service
public class ServiceProductoImpl implements ServiceProducto {

        @Autowired
        private RepositoryProducto repositoryProducto;

        @Autowired
        private RepositoryFerreteria repositoryFerreteria;

        @Autowired
        private ProductoDTOConverter convert;

        @Override
        public ListarProductoDTO findAll(String nombreProducto, String categoria,
                        String razonSocial) {
                Iterable<Producto> productoIterable = repositoryProducto.findAll();
                List<Producto> productos = StreamSupport.stream(productoIterable.spliterator(), false)
                                .collect(Collectors.toList());

                if (nombreProducto != null && !nombreProducto.isEmpty()) {
                        productos = productos.stream()
                                        .filter(prod -> prod.getNombreProducto().toLowerCase()
                                                        .contains(nombreProducto.toLowerCase()))
                                        .collect(Collectors.toList());
                }

                if (categoria != null && !categoria.isEmpty()) {
                        productos = productos.stream()
                                        .filter(prod -> prod.getCategoria().toLowerCase()
                                                        .contains(categoria.toLowerCase()))
                                        .collect(Collectors.toList());
                }

                if (razonSocial != null && !razonSocial.isEmpty()) {
                        productos = productos.stream()
                                        .filter(prod -> prod.getFerreteria().getRazonSocial().toLowerCase()
                                                        .contains(razonSocial.toLowerCase()))
                                        .collect(Collectors.toList());
                }

                List<ProductoDTO> productosDTO = productos.stream().map(convert::convertToListarDTO)
                                .collect(Collectors.toList());

                ListarProductoDTO listarProductoDTO = new ListarProductoDTO();
                listarProductoDTO.setProductos(productosDTO);

                return listarProductoDTO;
        }

        @Override
        public CrearProductoDTO save(CrearProductoDTO crearProductoDTO) {
                // Validar que el nombre del producto no esté en uso
                if (repositoryProducto.existsByNombreProducto(crearProductoDTO.getNombreProducto())) {
                        throw new IllegalArgumentException("El nombre del producto ya está en uso");
                }

                if (repositoryProducto.existsByCategoria(crearProductoDTO.getCategoria())) {
                        throw new IllegalArgumentException("El nombre de la categoría ya está en uso");
                }

                // Convertir el DTO a entidad
                Producto producto = convert.convertToEntity(crearProductoDTO);

                // Buscar la ferretería asociada al producto
                Ferreteria ferreteria = repositoryFerreteria.findById(crearProductoDTO.getIdFerreteria())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Ferretería no encontrada con ID: "
                                                                + crearProductoDTO.getIdFerreteria()));

                // Asignar la ferretería al producto antes de guardarlo
                producto.setFerreteria(ferreteria);

                // Guardar el producto en la base de datos
                Producto savedProducto = repositoryProducto.save(producto);

                // Convertir la entidad guardada nuevamente a DTO y retornarla
                return convert.convertToDTO(savedProducto);
        }

        @Override
        public CrearProductoDTO update(Long id, CrearProductoDTO crearProductoDTO) {
                Optional<Producto> productoCurrentOptional = repositoryProducto.findById(id);
                if (productoCurrentOptional.isPresent()) {
                        Producto productoCurrent = productoCurrentOptional.get();

                        // Validar que el nombre del producto no esté en uso
                        if (repositoryProducto.existsByNombreProducto(crearProductoDTO.getNombreProducto())
                                        && !productoCurrent.getNombreProducto()
                                                        .equals(crearProductoDTO.getNombreProducto())) {
                                throw new IllegalArgumentException("El nombre del producto ya está en uso");
                        }

                        productoCurrent.setNombreProducto(crearProductoDTO.getNombreProducto());
                        productoCurrent.setDescripcion(crearProductoDTO.getDescripcion());
                        productoCurrent.setCategoria(crearProductoDTO.getCategoria());
                        productoCurrent.setPrecio(crearProductoDTO.getPrecio());
                        productoCurrent.setStock(crearProductoDTO.getStock());

                        // Buscar la ferretería asociada al producto
                        Ferreteria ferreteria = repositoryFerreteria.findById(crearProductoDTO.getIdFerreteria())
                                        .orElseThrow(() -> new IllegalArgumentException(
                                                        "Ferretería no encontrada con ID: "
                                                                        + crearProductoDTO.getIdFerreteria()));

                        // Asignar la ferretería al producto antes de guardarlo
                        productoCurrent.setFerreteria(ferreteria);

                        // Actualizar el producto en la base de datos
                        Producto updatedProducto = repositoryProducto.save(productoCurrent);
                        return convert.convertToDTO(updatedProducto);
                }
                throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }

        @Override
        public void delete(Long id) {
                Optional<Producto> productoOptional = repositoryProducto.findById(id);
                if (productoOptional.isPresent()) {
                        repositoryProducto.delete(productoOptional.get());
                } else {
                        throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
                }
        }

}
