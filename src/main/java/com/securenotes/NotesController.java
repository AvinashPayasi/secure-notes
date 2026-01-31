package com.securenotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NotesController {
    private NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService){
        this.notesService=notesService;
    }

    @GetMapping("/notes")
    public PagedModel<Notes> getNotes(@RequestParam(required = false) String state,
                               @RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue = "10")int size,
                               @RequestParam(defaultValue = "createdAt") String sortBy,
                               @RequestParam(defaultValue = "asc") String direction)
    {
        Sort sort= Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,sortBy);

        if(state==null) {
            return notesService.getNotes(page, size, sort);
        }
        switch(state.toLowerCase()){
            case "archived":
                return notesService.getArchivedNotes(page, size, sort);
            case "pinned":
                return notesService.getPinnedNotes(page, size, sort);
            case "trashed":
                return notesService.getTrashedNotes(page, size, sort);
            default:
                throw new IllegalArgumentException("Invalid State: "+state);
        }
    }

    @PostMapping("/notes")
    public void addNotes(@RequestBody Notes notes){
        notesService.addNote(notes);
    }

    @PostMapping("/notes/bulk")
    public void addBulkNotes(@RequestBody List<Notes> notes){
        notesService.addBulkNotes(notes);
    }

    @GetMapping("/notes/{notesId}")
    public Notes getNotesById(@PathVariable int notesId){
        return notesService.getNotesById(notesId);
    }

    @DeleteMapping("/notes/{notesId}")
    public void deleteNotesById(@PathVariable int notesId){
        notesService.deleteNotesById(notesId);
    }

    @GetMapping("/notes/{notesId}/trash")
    public Notes getTrashNoteById(@PathVariable int notesId){
        return notesService.getTrashNoteById(notesId);
    }

    @PatchMapping("/notes/{notesId}/recover")
    public void recoverDeletedNote(@PathVariable int notesId){
        notesService.recoverDeletedNotes(notesId);
    }

    @PatchMapping("/notes/{notesId}")
    public void updateNotes(@PathVariable int notesId, @RequestBody Notes notes){
        notesService.updateNotes(notesId,notes);
    }

    @DeleteMapping("/notes/{notesId}/trash")
    public void deletePermanently(@PathVariable int notesId){
        notesService.deletePermanently(notesId);
    }

    @PatchMapping("/notes/{notesId}/pin")
    public void pinNote(@PathVariable int notesId){
        notesService.pinNote(notesId);
    }

    @PatchMapping("/notes/{notesId}/unpin")
    public void unpinNote(@PathVariable int notesId){
        notesService.unpinNote(notesId);
    }

    @PatchMapping("/notes/{notesId}/archive")
    public void archiveNote(@PathVariable int notesId){
        notesService.archiveNote(notesId);
    }

    @PatchMapping("/notes/{notesId}/unarchive")
    public void unarchiveNote(@PathVariable int notesId){
        notesService.unarchiveNotes(notesId);
    }
}
