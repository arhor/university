package by.arhor.university.service.impl;

import by.arhor.university.service.dto.DTO;
import by.arhor.university.service.trait.Deleter;
import by.arhor.university.service.trait.Reader;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class AbstractService<T, D extends DTO, K>
    implements Reader<D, K>
             , Deleter<D, K> {

  protected final Class<D> dtoClass;
  protected final JpaRepository<T, K> repository;
  protected final ModelMapper mapper;

  public AbstractService(Class<D> dtoClass, JpaRepository<T, K> repository, ModelMapper mapper) {
    this.dtoClass = dtoClass;
    this.repository = repository;
    this.mapper = mapper;
  }

  protected D toDto(T entity) {
    return mapper.map(entity, dtoClass);
  }

  @Override
  @Transactional(readOnly = true)
  public D findOne(K id) {
    return repository
        .findById(id)
        .map(this::toDto)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public List<D> findAll() {
    return repository
        .findAll()
        .stream()
        .map(this::toDto)
        .collect(toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<D> findPage(int page, int size) {
    return repository
        .findAll(PageRequest.of(page, size))
        .stream()
        .map(this::toDto)
        .collect(toList());
  }

  @Override
  public void delete(D item) {
    // FIXME: Stub
  }

  @Override
  public void deleteById(K id) {
    final var item = repository.findById(id).orElseThrow(RuntimeException::new);
    repository.delete(item);
  }

}
