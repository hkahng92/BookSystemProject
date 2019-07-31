package com.trilogyed.Noteservice.dao;


import com.trilogyed.Noteservice.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDaoJdbcTemplateImpl implements NoteDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_NOTE_SQL =
            "insert into note(book_id,note) value(?,?)";

    private static final String SELECT_NOTE_SQL =
            "select * from note where note_id = ?";

    private static final String SELECT_NOTE_BY_BOOK =
            "select * from note where book_id =?";

    private static final String SELECT_ALL_NOTES_SQL =
            "select * from note";

    private static final String UPDATE_NOTE_SQL =
            "update note set book_id = ?,note = ? where note_id = ?";

    private static final String DELETE_NOTE_SQL =
            "delete from note where note_id =?";





    @Override
    @Transactional
    public Note createNote(Note note) {
        jdbcTemplate.update(
                INSERT_NOTE_SQL,
                note.getBookId(),
                note.getNote()
        );

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        note.setNoteId(id);

        return note;
    }

    @Override
    public Note getNoteById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_NOTE_SQL, this::mapRowToNote, id);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Note> getNotesByBook(int bookId) {
        return jdbcTemplate.query(SELECT_NOTE_BY_BOOK, this::mapRowToNote,bookId);
    }

    @Override
    public List<Note> getAllNotes() {
        return jdbcTemplate.query(SELECT_ALL_NOTES_SQL,this::mapRowToNote);
    }

    @Override
    public void updateNote(Note note) {
        jdbcTemplate.update(
                UPDATE_NOTE_SQL,
                note.getBookId(),
                note.getNote(),
                note.getNoteId());

    }

    @Override
    public void deleteNote(int id) {
        jdbcTemplate.update(DELETE_NOTE_SQL,id);

    }

    private Note mapRowToNote(ResultSet rs, int rowNum) throws SQLException{

        Note note = new Note();

        note.setBookId(rs.getInt("book_id"));
        note.setNoteId(rs.getInt("note_id"));
        note.setNote(rs.getString("note"));

        return note;
    }

}
