package com.securenotes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Integer> {

    Page<Notes> findByIsDeletedTrue(Pageable pageable);

    void deleteByNotesIdAndIsDeletedTrue(int notesId);

    Notes findByNotesIdAndIsDeletedTrue(int notesId);

    Page<Notes> findByIsArchivedTrue(Pageable pageable);

    Page<Notes> findByIsPinnedTrue(Pageable pageable);

    Page<Notes> findByIsDeletedFalseOrderByIsPinnedDesc(Pageable pageable);
}
