package com.notecloud.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import com.notecloud.backend.repository.NoteRepository;
import com.notecloud.backend.repository.UserRepository;
import com.notecloud.backend.entity.User;
import com.notecloud.backend.dto.CreateNoteRequest;
import com.notecloud.backend.dto.NoteResponse;
import com.notecloud.backend.dto.UpdateNoteRequest;
import com.notecloud.backend.entity.Note;

import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(
            NoteRepository noteRepository,
            UserRepository userRepository
    ) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUsername() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    private User getCurrentUser() {

            String username = getCurrentUsername();

            return userRepository.findByEmail(username)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));
        }

        public NoteResponse createNote(CreateNoteRequest request) {

        User currentUser = getCurrentUser();

        Note note = new Note();

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        note.setOwner(currentUser);

        Note savedNote = noteRepository.save(note);

        return new NoteResponse(
                savedNote.getId(),
                savedNote.getTitle(),
                savedNote.getContent()
        );
    }

    public List<NoteResponse> getAllNotes() {

        User currentUser = getCurrentUser();

        List<Note> notes = noteRepository.findByOwner(currentUser);

        return notes.stream()
                .map(note -> new NoteResponse(
                        note.getId(),
                        note.getTitle(),
                        note.getContent()
                ))
                .collect(Collectors.toList());
    }

    public NoteResponse getNoteById(Long id) {

        User currentUser = getCurrentUser();

        Note note = noteRepository
                .findByIdAndOwner(id, currentUser)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent()
        );
    }

    public NoteResponse updateNote(Long id, UpdateNoteRequest request) {

        User currentUser = getCurrentUser();

        Note note = noteRepository
                .findByIdAndOwner(id, currentUser)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note updatedNote = noteRepository.save(note);

        return new NoteResponse(
                updatedNote.getId(),
                updatedNote.getTitle(),
                updatedNote.getContent()
        );
    }

    public void deleteNote(Long id) {

        User currentUser = getCurrentUser();

        Note note = noteRepository
                .findByIdAndOwner(id, currentUser)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        noteRepository.delete(note);
    }
}