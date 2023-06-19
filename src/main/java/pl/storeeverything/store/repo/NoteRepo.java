package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.model.UserDetails;

import java.util.Date;
import java.util.List;
@Repository
public interface NoteRepo extends JpaRepository<NotesDetails,Long> {
    List<NotesDetails> findByDateBetween(Date startDate, Date endDate);
    List<NotesDetails> findALlByUserId(Long id);
    NotesDetails findAllById(Long id);

    @Query("SELECT n FROM NotesDetails n JOIN n.sharedWith u WHERE u = :user")
    List<NotesDetails> findBySharedWith(@Param("user") UserDetails user);

}


