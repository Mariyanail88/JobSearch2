package kg.attractor.jobsearch.mappers;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomUserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAvatar(rs.getString("avatar"));
        user.setAccountType(rs.getString("account_type"));
        return user;
    }

    public static UserDto toUserDto(UserWithAvatarFileDto userWithAvatarFileDto) {
        return UserDto.builder()
                .id(userWithAvatarFileDto.getId())
                .name(userWithAvatarFileDto.getName())
                .age(userWithAvatarFileDto.getAge())
                .email(userWithAvatarFileDto.getEmail())
                .password(userWithAvatarFileDto.getPassword())
                .phoneNumber(userWithAvatarFileDto.getPhoneNumber())
                .avatar(userWithAvatarFileDto.getAvatar().getOriginalFilename())
                .accountType(userWithAvatarFileDto.getAccountType())
                .enabled(userWithAvatarFileDto.isEnabled())
                .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .enabled(user.isEnabled())
                .build();
    }

    public static User toUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .avatar(userDto.getAvatar())
                .accountType(userDto.getAccountType())
                .enabled(userDto.isEnabled())
                .build();
    }
}