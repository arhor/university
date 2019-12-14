package by.arhor.university.service.impl;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.university.service.EnrolleeService;
import by.arhor.university.service.dto.EnrolleeDTO;

@Lazy
@Service
@Transactional
public class EnrolleeServiceImpl implements EnrolleeService {
  @Override
  public EnrolleeDTO create(EnrolleeDTO item) {
    return null;
  }

  @Override
  public void delete(EnrolleeDTO item) {

  }

  @Override
  public void deleteById(Long id) {

  }

  @Override
  public EnrolleeDTO findOne(Long id) {
    return null;
  }

  @Override
  public List<EnrolleeDTO> findAll() {
    return null;
  }

  @Override
  public List<EnrolleeDTO> findPage(int page, int size) {
    return null;
  }

  @Override
  public EnrolleeDTO update(EnrolleeDTO item) {
    return null;
  }

  @Override
  public List<EnrolleeDTO> findBestEnrollees(int page, int size) {
    return null;
  }
}
