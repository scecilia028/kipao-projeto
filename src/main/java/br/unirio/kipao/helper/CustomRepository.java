package br.unirio.kipao.helper;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomRepository <T, ID extends Serializable> extends CrudRepository<T, ID>{
	
	void refresh(T t);
}
