package ru.itis.repositories.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.AppUser;
import ru.itis.models.Role;
import ru.itis.repositories.UserRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

//@Component
public class UserRepositoryTemplate implements UserRepository {


    private final String SQL_FIND_BY_LOGIN = "SELECT * FROM app_user WHERE login = ?;";
    private final String SQL_FIND_BY_LINK = "SELECT * FROM app_user WHERE link = ?;";
    private final String SQL_SAVE_USER = "insert into app_user(login, password, email, link, is_confirmed, role) values (?, ?, ?, ?, ?, ?);";
    //language=SQL
    private final String UPDATE_USER = "update app_user set login = ?, password = ?, email = ?, link = ?, is_confirmed = ? where id = ?;";

//    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<AppUser> userRowMapper = (row, rowNumber) ->
            AppUser.builder()
                    .id(row.getLong("id"))
                    .login(row.getString("login"))
                    .hashPassword(row.getString("password"))
                    .email(row.getString("email"))
                    .link(row.getString("link"))
                    .isConfirmed(row.getBoolean("is_confirmed"))
                    .role(Role.valueOf(row.getString("role")))
                    .build();

    @Override
    public Optional<AppUser> findByLogin(String login) {
        try {
            AppUser appUser = jdbcTemplate.queryForObject(SQL_FIND_BY_LOGIN,  new Object[]{login}, userRowMapper);
            return Optional.of(appUser);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    @Override
    public Optional<AppUser> findByLink(String link) {
        try {
            AppUser appUser = jdbcTemplate.queryForObject(SQL_FIND_BY_LINK,  new Object[]{link}, userRowMapper);
//            System.out.println("id: " + user.getId());
            return Optional.of(appUser);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save (AppUser entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE_USER);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getHashPassword());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getLink());
            statement.setBoolean(5, entity.getIsConfirmed());
            statement.setString(6, entity.getRole().toString());
            return statement;
        }, keyHolder);

        entity.setId((Long)keyHolder.getKey());
    }

    @Override
    public void update(AppUser entity) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getHashPassword());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getLink());
            statement.setBoolean(5, entity.getIsConfirmed());
            statement.setLong(6, entity.getId());
            return statement;
        });
    }

    @Override
    public Optional<AppUser> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<AppUser> findAll() {
        return jdbcTemplate.query("select * from app_user", userRowMapper);
    }
}
