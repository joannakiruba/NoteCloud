package com.notecloud.backend.controller;

import com.notecloud.backend.dto.CreateNoteRequest;
import com.notecloud.backend.dto.NoteResponse;
import com.notecloud.backend.dto.UpdateNoteRequest;
import com.notecloud.backend.service.NoteService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public NoteResponse createNote(
            @RequestBody CreateNoteRequest request
    ) {
        return noteService.createNote(request);
    }

    @GetMapping
    public List<NoteResponse> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public NoteResponse getNoteById(
            @PathVariable Long id
    ) {
        return noteService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public NoteResponse updateNote(
            @PathVariable Long id,
            @RequestBody UpdateNoteRequest request
    ) {
        return noteService.updateNote(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(
            @PathVariable Long id
    ) {
        noteService.deleteNote(id);
    }
}