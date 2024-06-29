Все дз хранятся в папке homeworks. 

# Стэк технологий
- Java 17
- Apache Maven

# ДЗ №1

## Как пользоваться приложением 
  Дз представляет собой консольное приложение. При запуске основного класса(Main) в консоль выведется список допустимых команд. Эти команды нужно вводить для взаимодействия с приложением. Список команд CRUD операций можно вывести на экран, введя команду edit. Дату и время бронирования нужно вводить в формате ГГГГ-ММ-ДДTЧЧ:ММ:CC (пример: 2024-06-21T12:00:00).

## Ссылки
- https://github.com/Aleksey1123/ylab/tree/master/homeworks/hw1
- https://github.com/Aleksey1123/ylab/pull/2

# ДЗ №2

## Как пользоваться приложением
  В папке проекта по пути src/main/resources/db лежит docker-compose-файл - его нужно запустить. Он создаст три контейнера: bd - в нём лежит postgres, app - в нём лежит сам jar-файл, liquibase - в нём находятся миграции. Так как приложение консольное, то чтобы взаимодействовать с ним в docker-контейнере нужно ввести данную команду в терминале: 
````
docker exec -it app java -cp app/app.jar org.example.Main
````
После этого можно взаимодействовать в своём терминале с приложением также, как если бы оно было запущено не в контейнере.
