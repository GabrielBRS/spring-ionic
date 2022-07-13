package com.gabrielsousa.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gabrielsousa.domain.Client;
import com.gabrielsousa.dto.ClientDTO;
import com.gabrielsousa.repository.ClientRepository;
import com.gabrielsousa.service.exception.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	public Client find(Integer id) {
		Optional<Client> obj = clientRepository.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName())); 
	}
	
	public List<Client> findAll(){
		return clientRepository.findAll();
	}
	
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), 
				orderBy);
		return clientRepository.findAll(pageRequest);
	}
	
	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		clientRepository.save(obj);
//		adressRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Client update(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj,obj);
		return clientRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			clientRepository.deleteById(id);
		}
		catch(com.gabrielsousa.resources.exception.DataIntegrityViolationException e){
			throw new com.gabrielsousa.resources.exception.DataIntegrityViolationException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public Client fromDTO(ClientDTO objDto) {
//		throw new UnsupportedOperationException();
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
	}
	
//	public Client fromDTO(ClientNewDTO objDto) {
////		throw new UnsupportedOperationException();
//		Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOuCnpj(), ClientType.toEnum(objDto.getType()));
//		City cid = new City(objDto.getCityId(),null,null);
//		Adress adr = new Adress(null, objDto.getLogradouro(),objDto.getNumero(),objDto.getComplemento(),objDto.getBairro(),objDto.getCep(),cli,cid);
//		cli.getEnderecos().add(adr);
//		cli.getTelefones().add(objDto.getTelephone1());
//		if(objDto.getTelephone2()!=null) {
//			cli.getTelefones().add(objDto.getTelephone2());
//		}
//		if(objDto.getTelephone3()!=null) {
//			cli.getTelefones().add(objDto.getTelephone3());
//		}
//		return cli;
//	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
}
