package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class User {
//    private Long id;
//    private String email;
//    private String login;
//    private String name;
//    private LocalDate birthday;
//    private final Set<Long> friends = new HashSet<>();

    private Long id;

    @NotEmpty
    @Email(message = "Неправильно введен Email")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^\\S*")
    private String login;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if(name == null || name.isEmpty() || name.isBlank()){
            this.name = login;
        } else{
            this.name = name;
        }
        this.birthday = birthday;
    }

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        if(name == null || name.isEmpty() || name.isBlank()){
            this.name = login;
        } else{
            this.name = name;
        }
        this.birthday = birthday;
    }
}