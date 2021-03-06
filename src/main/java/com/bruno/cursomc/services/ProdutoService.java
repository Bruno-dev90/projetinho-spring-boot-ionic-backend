package com.bruno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.cursomc.domain.Categoria;
import com.bruno.cursomc.domain.Produto;
import com.bruno.cursomc.repositories.CategoriaRepository;
import com.bruno.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	

  public Produto find(Integer id) { 
		 Optional<Produto> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName(), null)); 
		}
  
  
  public Page<Produto>search (String nome, List<Integer> ids, Integer pages, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of (
				pages, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
	  
  }

}
