package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.ContactTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactTypesRepository extends JpaRepository<ContactTypes, Integer> {
}