package com.securenotes;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotesService {
    private NotesRepo notesRepo;

    @Autowired
    public NotesService(NotesRepo notesRepo){
        this.notesRepo=notesRepo;
    }

    public void addNote(Notes notes){
        notesRepo.save(notes);
    }

    public PagedModel<Notes> getNotes(int page, int size, Sort sort) {
        Pageable pageable= PageRequest.of(page,size, sort);
        Page<Notes> pages=notesRepo.findByIsDeletedFalseOrderByIsPinnedDesc(pageable);
        return new PagedModel<>(pages);
    }

    public Notes getNotesById(int notesId) {
        return notesRepo.findById(notesId).orElse(new Notes());
    }

    @Transactional
    public void deleteNotesById(int notesId) {
        Notes notes=getNotesById(notesId);
        notes.setIsDeleted(true);
        notes.setDeletedAt();
    }

    public PagedModel<Notes> getTrashedNotes(int page, int size, Sort sort) {
        Pageable pageable=PageRequest.of(page, size, sort);
        Page<Notes> pages=notesRepo.findByIsDeletedTrue(pageable);
        return new PagedModel<>(pages);
    }

    @Transactional
    public void recoverDeletedNotes(int notesId){
        Notes notes=getNotesById(notesId);
        notes.setIsDeleted(false);
        notes.setDeletedAt(null);
    }

    @Transactional
    public void updateNotes(int notesId, Notes notes) {
        Notes notes1=notesRepo.getReferenceById(notesId);
        String title=notes.getTitle();
        String note=notes.getNotes();
        if(title==null&&note==null){
            return;
        }else if(title==null){
            notes1.setNotes(note);
        }else if (note==null){
            notes1.setTitle(title);
        }else{
            notes1.setTitle(title);
            notes1.setNotes(note);
        }
    }

    @Transactional
    public void deletePermanently(int notesId) {
        notesRepo.deleteByNotesIdAndIsDeletedTrue(notesId);
    }

    public Notes getTrashNoteById(int notesId) {
        return notesRepo.findByNotesIdAndIsDeletedTrue(notesId);
    }

    @Transactional
    public void pinNote(int notesId) {
        Notes notes=getNotesById(notesId);
        if(notes.getIsDeleted()==true){}
        else if(notes.getIsArchived()==true){}
        else if(notes.getIsPinned()){}
        else {
            notes.setIsPinned(true);
        }
    }

    @Transactional
    public void archiveNote(int notesId) {
        Notes notes=getNotesById(notesId);
        notes.setIsArchived(true);
    }

    @Transactional
    public void unarchiveNotes(int notesId) {
        Notes notes=getNotesById(notesId);
        notes.setIsArchived(false);
    }

    public PagedModel<Notes> getArchivedNotes(int page, int size, Sort sort) {
        Pageable pageable=PageRequest.of(page,size, sort);
        Page<Notes> pages=notesRepo.findByIsArchivedTrue(pageable);
        return new PagedModel<>(pages);
    }

    public PagedModel<Notes> getPinnedNotes(int page,int size, Sort sort){
        Pageable pageable=PageRequest.of(page,size, sort);
        Page<Notes> pages=notesRepo.findByIsPinnedTrue(pageable);
        return new PagedModel<>(pages);
    }

    @Transactional
    public void unpinNote(int notesId) {
        Notes notes=getNotesById(notesId);
        notes.setIsPinned(false);
    }

    public void addBulkNotes(List<Notes> notes) {
        notesRepo.saveAll(notes);
    }

}
