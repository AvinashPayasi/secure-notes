package com.securenotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="notes")
public class Notes {
    @Id
    @Column(name = "notes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notesId;
    private String title;
    private String notes;
    @Column(name="created_at", updatable = false, insertable = false)
    private Timestamp createdAt;
    @Column(name="is_deleted")
    private boolean isDeleted=false;
    @Column(name="is_pinned")
    private boolean isPinned=false;
    @Column(name="is_archived")
    private boolean isArchived=false;
    @Column(name="deleted_at")
    private Timestamp deletedAt;

    public int getNotesId(){
        return notesId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes){
        this.notes=notes;
    }

    @JsonIgnore
    public boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(boolean value){
        this.isDeleted=value;
    }

    public void setDeletedAt(@Nullable Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setDeletedAt(){
        deletedAt= Timestamp.valueOf(LocalDateTime.now());
    }

    public boolean getIsPinned(){
        return isPinned;
    }

    public void setIsPinned(boolean value){
        this.isPinned=value;
    }

    public void setIsArchived(boolean value) {
        this.isArchived=value;
    }

    @JsonIgnore
    public boolean getIsArchived() {
        return isArchived;
    }
}