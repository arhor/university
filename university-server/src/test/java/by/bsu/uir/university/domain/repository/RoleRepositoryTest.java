package by.bsu.uir.university.domain.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@DirtiesContext
@RunWith(SpringRunner.class)
public class RoleRepositoryTest {

  @Autowired
  private RoleRepository repository;

  @Test
  public void findAllTest() {
    System.out.println(repository.findAll());
  }

}
