![Database](assets/FilmorateBD.PNG)

https://yapx.ru/album/YXar0

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
SELECT g.name
FROM Genre
JOIN film_genre gf ON g.id = gf.genre_id
WHERE film_id = 1;
