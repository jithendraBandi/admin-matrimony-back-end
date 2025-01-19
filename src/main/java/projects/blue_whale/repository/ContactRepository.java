package projects.blue_whale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.blue_whale.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
