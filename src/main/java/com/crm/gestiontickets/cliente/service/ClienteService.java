/* Patrón: estructural: Facade, centraliza la lógica de negocio, interacción con el repositorio
   creacional: Factory Method,  construlle el ClienteDetalle y IdCliente desde entidades */

package com.crm.gestiontickets.cliente.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.crm.gestiontickets.cliente.dto.ClienteDetalle;
import com.crm.gestiontickets.cliente.dto.IdCliente;
import com.crm.gestiontickets.cliente.dto.NuevoCliente;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.cliente.exception.ClienteNotFoundException;
import com.crm.gestiontickets.cliente.repository.ClienteRepository;

import org.springframework.data.domain.Pageable;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDetalle> obtenerClientes(String valorBusqueda) {

        List<Cliente> listaClientes = clienteRepository.buscarPorCualquierCampo(valorBusqueda);

        List<ClienteDetalle> listaClientesDTO = new ArrayList<>();

        for (Cliente cliente : listaClientes) {
            ClienteDetalle clienteDTO = new ClienteDetalle();
            clienteDTO.setIdCliente(cliente.getIdCliente());
            clienteDTO.setNombre(cliente.getNombre());
            clienteDTO.setApellido(cliente.getApellido());
            clienteDTO.setCelular(cliente.getCelular());
            clienteDTO.setCorreo(cliente.getCorreo());
            clienteDTO.setNumeroIdentidad(cliente.getNumeroIdentidad());

            listaClientesDTO.add(clienteDTO);
        }

        return listaClientesDTO;
    }

    public ClienteDetalle obtenerCliente(Long idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new ClienteNotFoundException(idCliente));

        ClienteDetalle clienteDTO = new ClienteDetalle();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setCelular(cliente.getCelular());
        clienteDTO.setCorreo(cliente.getCorreo());
        clienteDTO.setNumeroIdentidad(cliente.getNumeroIdentidad());

        return clienteDTO;
    }

    public IdCliente editarCliente(ClienteDetalle clienteActualizado) {

        Cliente cliente = clienteRepository.findById(clienteActualizado.getIdCliente()).get();

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setApellido(clienteActualizado.getApellido());
        cliente.setCelular(clienteActualizado.getCelular());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setNumeroIdentidad(clienteActualizado.getNumeroIdentidad());
        cliente.setFechaActualizacion(LocalDateTime.now());

        clienteRepository.save(cliente);

        IdCliente idCliente = new IdCliente(cliente.getIdCliente());

        return idCliente;
    }

    public IdCliente crearCliente(NuevoCliente nvoClienteDTO) {
        Cliente nvoCliente = new Cliente();
        nvoCliente.setNombre(nvoClienteDTO.getNombre());
        nvoCliente.setApellido(nvoClienteDTO.getApellido());
        nvoCliente.setCelular(nvoClienteDTO.getCelular());
        nvoCliente.setCorreo(nvoClienteDTO.getCorreo());
        nvoCliente.setNumeroIdentidad(nvoClienteDTO.getNumeroIdentidad());
        nvoCliente.setActivo('S');

        clienteRepository.save(nvoCliente);

        IdCliente idCliente = new IdCliente(nvoCliente.getIdCliente());

        return idCliente;
    }

    //pajinacion

    public Page<Cliente> listarClientes(Pageable pageable) {
    return clienteRepository.findAll(pageable);
}

}
