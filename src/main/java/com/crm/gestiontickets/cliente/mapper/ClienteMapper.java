package com.crm.gestiontickets.cliente.mapper;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.cliente.dto.ClienteDetalle;
import com.crm.gestiontickets.cliente.entity.Cliente;

@Component
public class ClienteMapper {

    public ClienteDetalle mapearClienteADetalle(Cliente cliente) {
        ClienteDetalle clienteDTO = new ClienteDetalle();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setCelular(cliente.getCelular());
        clienteDTO.setCorreo(cliente.getCorreo());
        clienteDTO.setNumeroIdentidad(cliente.getNumeroIdentidad());

        return clienteDTO;
    }

}
