![Database](assets/FilmorateBD.PNG)
https://yapx.ru/album/YW1J7
Примеры запросов:
1) Запрос для получения фильма c id = 1 : 
SELECT f.* 
FROM Films f 
WHERE film_id = 1;
2) Запрос для получения друзей пользователя c id = 1:
 SELECT friend_id 
 From Friends
 WHERE user_id = 1;
3) Запрос для получения жанров фильма c id = 1:
SELECT g.genre
FROM Genre
JOIN Genre_film gf ON g.genre_id = gf.genre_id
WHERE film_id = 1;
