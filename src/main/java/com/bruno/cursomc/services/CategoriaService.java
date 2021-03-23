package com.bruno.cursomc.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Categoria;
import com.bruno.cursomc.dto.CategoriaDTO;
import com.bruno.cursomc.repositories.CategoriaRepository;
import com.bruno.cursomc.services.exception.DataIntegrityException;
import com.bruno.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) { 
		 Optional<Categoria> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	} 
  
	public Categoria insert(Categoria obj) {
	  obj.setId(null);
	  return repo.save(obj);
  	}
  	
  	public Categoria update (Categoria obj) {
  		find(obj.getId());
  		return repo.save(obj);
  	}

  	public void  delete(Integer id) {
  		find(id);
  		try {
  		repo.deleteById(id);
  		}
  		catch (DataIntegrityViolationException e) {
  			throw new DataIntegrityException("Nao é Possivel excluir uma categoria que possui produtos");
  		}
  	}
  	
  	public List<Categoria> findAll(){
			return repo.findAll();
		}
  	
  	
  	public Page<Categoria> findPage(
  			Integer pages, Integer linesPerPage, String orderBy, String direction){
  		
  		PageRequest pagerequest = PageRequest.of(
  				pages, linesPerPage, Direction.valueOf(direction), orderBy);
  		
  		return repo.findAll(pagerequest);
  	}
  	
  	public Categoria fromDTO(CategoriaDTO objDto) {
  		return new Categoria(objDto.getId(), objDto.getNome());
  	}
}
