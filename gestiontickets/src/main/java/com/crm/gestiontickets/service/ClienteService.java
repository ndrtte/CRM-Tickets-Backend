package com.crm.gestiontickets.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.ClienteDTO;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.exception.ClienteNotFoundException;
import com.crm.gestiontickets.repository.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> obtenerCliente(String valorBusqueda) {

        List<Cliente> listaClientes = clienteRepository.buscarPorCualquierCampo(valorBusqueda);
        
        List<ClienteDTO> listaClientesDTO = new ArrayList<>();

        for (Cliente cliente : listaClientes) {
            ClienteDTO clienteDTO = new ClienteDTO();
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

    public ClienteDTO obtenerCliente(Long idCliente){

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new ClienteNotFoundException(idCliente));

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setCelular(cliente.getCelular());
        clienteDTO.setCorreo(cliente.getCorreo());
        clienteDTO.setNumeroIdentidad(cliente.getNumeroIdentidad());

        return clienteDTO;
    }

}
