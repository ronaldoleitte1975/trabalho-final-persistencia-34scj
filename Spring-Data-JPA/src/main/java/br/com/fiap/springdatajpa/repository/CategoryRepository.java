package br.com.fiap.springdatajpa.repository;

import br.com.fiap.springdatajpa.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{

    Optional<List<Category>> findByIdIn(Collection<Integer> ids);
}
