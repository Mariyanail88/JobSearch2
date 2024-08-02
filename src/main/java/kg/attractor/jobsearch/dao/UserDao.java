package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.UserMapper;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    // Получение всех пользователей
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    // Получение пользователя по ID
    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new UserMapper(), id)
                )
        );
    }


    public Optional<User> getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE name = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM users WHERE phone_number = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), phoneNumber);
        return Optional.ofNullable(user);
    }


    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), email);
        return Optional.ofNullable(user);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public void create(User user) {
        String sql = "INSERT INTO users (name, password, age, email, phone_number, avatar, account_type) " +
                "VALUES (:name, :password, :age, :email, :phoneNumber, :avatar, :accountType)";
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", user.getName())
                        .addValue("password", user.getPassword())
                        .addValue("age", user.getAge())
                        .addValue("email", user.getEmail())
                        .addValue("phoneNumber", user.getPhoneNumber())
                        .addValue("avatar", user.getAvatar())
                        .addValue("accountType", user.getAccountType())
        );
    }


    public Integer create(String username, String password, Integer age, String email, String phoneNumber, String avatar, String accountType) {
        String sql = "INSERT INTO users (name, password, age, email, phone_number, avatar, account_type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, age);
            ps.setString(4, email);
            ps.setString(5, phoneNumber);
            ps.setString(6, avatar);
            ps.setString(7, accountType);
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    // Получение пользователей по возрасту
    public List<User> getUsersByAge(int age) {
        String sql = "SELECT * FROM users WHERE age = ?";
        return jdbcTemplate.query(sql, new UserMapper(), age);
    }

    // Получение пользователей по типу аккаунта
    public List<User> getUsersByAccountType(String accountType) {
        String sql = "SELECT * FROM users WHERE account_type = ?";
        return jdbcTemplate.query(sql, new UserMapper(), accountType);
    }
}