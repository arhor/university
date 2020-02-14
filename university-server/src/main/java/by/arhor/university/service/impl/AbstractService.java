package by.arhor.university.service.impl;

import static by.arhor.core.Either.error;
import static by.arhor.university.service.error.ServiceError.notFound;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.core.Either;
import by.arhor.university.service.dto.DTO;
import by.arhor.university.service.error.ServiceError;
import by.arhor.university.service.trait.Deleter;
import by.arhor.university.service.trait.Reader;

public abstract class AbstractService<T, D extends DTO<K>, K>
    implements Reader<D, K>, Deleter<D, K> {

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
  public Either<D, ServiceError> findOne(K id) {
    return repository
        .findById(id)
        .map(this::toDto)
        .map(Either::<D, ServiceError>success)
        .orElseGet(() -> error(notFound(dtoClass.getSimpleName(), "id", id)));
  }

  @Override
  @Transactional(readOnly = true)
  public List<D> findAll() {
    return repository.findAll().stream().map(this::toDto).collect(toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<D> findPage(int page, int size) {
    return repository.findAll(PageRequest.of(page, size)).stream()
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
