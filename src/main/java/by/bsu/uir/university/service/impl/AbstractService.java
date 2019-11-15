package by.bsu.uir.university.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import by.bsu.uir.university.service.trait.Deleter;
import by.bsu.uir.university.service.trait.Reader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractService<T, D, K>
    implements Reader<D, K>
             , Deleter<D, K> {

  @NonNull protected final Class<D> dtoClass;
  @NonNull protected final JpaRepository<T, K> repository;
  @NonNull protected final ModelMapper mapper;

  protected D toDto(T entity) {
    return mapper.map(entity, dtoClass);
  }

  @Override
  @Transactional(readOnly = true)
  public D findOne(K id) {
    return repository
        .findById(id)
        .map(this::toDto)
        .orElseThrow(() -> new RuntimeException());
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
    final var item = repository.findById(id).orElseThrow(() -> new RuntimeException());
    repository.delete(item);
  }

}
