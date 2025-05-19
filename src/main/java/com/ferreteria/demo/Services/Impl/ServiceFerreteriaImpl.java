package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.FerreteriaConverterDTO;
import com.ferreteria.demo.DTO.Ferreteria.CrearFerreteriaDTO;
import com.ferreteria.demo.DTO.Ferreteria.FerreteriaDTO;
import com.ferreteria.demo.DTO.Ferreteria.ListarFerreteriaDTO;
import com.ferreteria.demo.Repositories.RepositoryFerreteria;
import com.ferreteria.demo.Repositories.Entities.Ferreteria;
import com.ferreteria.demo.Services.ServiceFerreteria;

@Service
public class ServiceFerreteriaImpl implements ServiceFerreteria {

    @Autowired
    private RepositoryFerreteria repositoryFerreteria;

    @Autowired
    private FerreteriaConverterDTO convert;

    @Override
    public ListarFerreteriaDTO findAll(String nit, String razonSocial, String representante) {
        Iterable<Ferreteria> ferreteriaIterable = repositoryFerreteria.findAll();
        List<Ferreteria> ferreterias = StreamSupport.stream(ferreteriaIterable.spliterator(), false)
                .collect(Collectors.toList());

        if (nit != null && !nit.isEmpty()) {
            ferreterias = ferreterias.stream()
                    .filter(ferre -> ferre.getNit().toLowerCase().contains(nit.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (razonSocial != null && !razonSocial.isEmpty()) {
            ferreterias = ferreterias.stream()
                    .filter(ferre -> ferre.getRazonSocial().toLowerCase().contains(razonSocial.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (representante != null && !representante.isEmpty()) {
            ferreterias = ferreterias.stream()
                    .filter(ferre -> ferre.getRepresentante().toLowerCase().contains(representante.toLowerCase()))
                    .collect(Collectors.toList());
        }

        List<FerreteriaDTO> ferreteriaDTOs = ferreterias.stream().map(convert::convertToListarDTO)
                .collect(Collectors.toList());
        ListarFerreteriaDTO listarFerreteriaDTO = new ListarFerreteriaDTO();
        listarFerreteriaDTO.setFerreteria(ferreteriaDTOs);
        return listarFerreteriaDTO;
    }

    @Override
    public CrearFerreteriaDTO save(CrearFerreteriaDTO crearFerreteriaDTO) {
        if (repositoryFerreteria.existsByRazonSocial(crearFerreteriaDTO.getRazonSocial())) {
            throw new IllegalArgumentException("La razon social ya está en uso");
        }
        Ferreteria ferreteria = convert.convertToEntity(crearFerreteriaDTO);
        Ferreteria savedFerreteria = repositoryFerreteria.save(ferreteria);
        return convert.convertToDTO(savedFerreteria);
    }

    @Override
    public CrearFerreteriaDTO update(Long id, CrearFerreteriaDTO crearFerreteriaDTO) {
        Optional<Ferreteria> ferreteriaCurrentOptional = repositoryFerreteria.findById(id);
        if (ferreteriaCurrentOptional.isPresent()) {
            Ferreteria ferreteriaCurrent = ferreteriaCurrentOptional.get();
            if (repositoryFerreteria.existsByRazonSocial(crearFerreteriaDTO.getRazonSocial())
                    && !ferreteriaCurrent.getRazonSocial().equals(crearFerreteriaDTO.getRazonSocial())) {
                throw new IllegalArgumentException("La razon social ya está en uso");
            }

            ferreteriaCurrent.setNit(crearFerreteriaDTO.getNit());
            ferreteriaCurrent.setRazonSocial(crearFerreteriaDTO.getRazonSocial());
            ferreteriaCurrent.setRepresentante(crearFerreteriaDTO.getRepresentante());

            Ferreteria updatedFerreteria = repositoryFerreteria.save(ferreteriaCurrent);
            return convert.convertToDTO(updatedFerreteria);
        }
        throw new IllegalArgumentException("La ferretería con ID " + id + " no existe");
    }

    @Override
    public void delete(Long id) {
        Optional<Ferreteria> ferreteriaOptional = repositoryFerreteria.findById(id);
        if (ferreteriaOptional.isPresent()) {
            repositoryFerreteria.delete(ferreteriaOptional.get());
        } else {
            throw new IllegalArgumentException("La ferretería con ID " + id + " no existe");
        }
    }

}
