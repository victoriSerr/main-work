package ru.itis.repositories.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.File;
import ru.itis.repositories.FileRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

//@Component
public class FileRepositoryTemplate implements FileRepository {

    //language=SQL
    private final String SQL_SAVE_FILE = "insert into file_info(real_filename, filename_in_storage, size, suffix, storage_url) VALUES (?, ?, ?, ?, ?);";
    private final String SQL_FIND_BY_NAME = "SELECT * FROM file_info WHERE filename_in_storage = ?;";

//    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<File> fileRowMapper = (row, rowNumber) ->
            File.builder()
                    .id(row.getLong("id"))
                    .realName(row.getString("real_filename"))
                    .nameInStorage(row.getString("filename_in_storage"))
                    .size(row.getLong("size"))
                    .suffix(row.getString("suffix"))
                    .storageUrl(row.getString("storage_url"))
                    .build();

    @Override
    public Optional<File> findFile(String name) {
        try {
            File file = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME,  new Object[]{name}, fileRowMapper);
            return Optional.of(file);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<File> findOne(Long s) {
        return Optional.empty();
    }

    @Override
    public List<File> findAll() {
        return null;
    }

    @Override
    public void save(File entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE_FILE);
            statement.setString(1, entity.getRealName());
            statement.setString(2, entity.getNameInStorage());
            statement.setLong(3, entity.getSize());
            statement.setString(4, entity.getSuffix());
            statement.setString(5, entity.getStorageUrl());
            return statement;
        }, keyHolder);

        entity.setId((Long)keyHolder.getKey());
    }

    @Override
    public void update(File entity) {

    }
}
