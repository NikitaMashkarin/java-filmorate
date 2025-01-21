package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.yandex.practicum.filmorate.Create;
import ru.yandex.practicum.filmorate.Update;

@Data
@EqualsAndHashCode(of = "id")
public class Mpa {

    @NotNull(groups = {Create.class, Update.class})
    private int id;
    @NotBlank(groups = {Create.class, Update.class})
    private String name;

}