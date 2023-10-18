package edu.padraodeprojeto.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.padraodeprojeto.service.ClienteService;
import edu.padraodeprojeto.model.Cliente;
import edu.padraodeprojeto.model.ClienteRepository;
import edu.padraodeprojeto.model.Endereco;
import edu.padraodeprojeto.model.EnderecoRepository;
import edu.padraodeprojeto.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {
    
	// Singleton: Injetar os componentes do Spring com @Autowired.
    // TODO: implementar método ClienteRepository:
	@Autowired
	private ClienteRepository clienteRepository;
    //TODO: implementar método do EnderecoRepository.
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Cliente> buscarTodos() {
		// Buscar todos os Clientes.
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// Buscar Cliente por ID.
		try {
            Optional<Cliente> cliente = clienteRepository.findById(id);

            if(!cliente.isEmpty()){
                return cliente.get();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return null;
        }

		
	}

	@Override
	public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		try{
            // Buscar Cliente por ID, caso exista:
            Optional<Cliente> clienteBd = clienteRepository.findById(id);
            if (clienteBd.isPresent()) {
                salvarClienteComCep(cliente);
            } else {
                throw new Exception();
            }
        } catch (Exception e){
            System.out.println(e);
        }
	}

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();

        //o Optional do findById trás métodos de auxilio ao código, que nesse caso se não existir esse cep nesse DB iremos tomar outra ação dentro do Lambda:
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

		cliente.setEndereco(endereco);

        //TODO: Inserir o cliente com o novo Endereco.

        clienteRepository.save(cliente);
	}

}

