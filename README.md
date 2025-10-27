Практическая работа №1
Приложение с типовой архитектурой JakartaEE

Выполнили студенты 6132-010402D Старыгин Виталий, Ненашев Данила

```
IntelliJ IDEA Ultimate

OpenJDK 17.0.15

Сервер приложение GlassFish 7.0.25
```

<img width="1906" height="499" alt="{999CABEB-A284-40DA-9CEF-9E5960113932}" src="https://github.com/user-attachments/assets/634bab64-e316-4bb6-9ffc-26d513b76312" />

СУБД PostgreSQL

```
Предметная область - система управления задачами

Сущности: пользователь (User), задача (Task)
```

Скрипт для создания и заполнения базы данных:
```
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INTEGER NOT NULL CHECK (age >= 18 AND age <= 75)
);

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_date DATE NOT NULL DEFAULT CURRENT_DATE,
    completed BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO users (name, email, age) VALUES 
('Старыгин Виталий', 'vs@mail.ru', 22),
('Ненашев Данила', 'nd@gmail.com', 22),
('Иванов Иван', 'ii@yandex.ru', 18),
('Петров Петр', 'pp@inbox.ru', 75);

INSERT INTO tasks (title, user_id, created_date, completed) VALUES 
('Написать код программы', 1, '2025-10-20', false),
('Протестировать код программы', 2, '2025-10-21', false),
('Написать фидбек', 2, '2025-10-22', false),
('Написать README', 3, '2025-10-23', false);
```
Реализовано добавление, удаление, редактирование пользователей и задач
<img width="1668" height="206" alt="{7514BFD3-9648-452E-BC9F-4D230CBDC3F8}" src="https://github.com/user-attachments/assets/90999ff3-3846-4fe9-83f9-db37174c2132" />

<img width="1918" height="994" alt="{EF58C003-3674-4BE1-A401-B753E650C945}" src="https://github.com/user-attachments/assets/f3af58fe-43c1-4be0-81da-529c17f97134" />

<img width="1920" height="990" alt="{477C3D72-1E00-47D1-9635-ACBCF24E575A}" src="https://github.com/user-attachments/assets/cdbfc36d-75c0-4d5b-a462-e464c892db1b" />

<img width="1920" height="996" alt="{A61B11A5-5DAB-4779-BB5D-110821B79E27}" src="https://github.com/user-attachments/assets/cd0e4402-3c0f-49f4-a721-f1d540f01dd4" />

<img width="1920" height="997" alt="{6E7AC853-7749-4293-A8A6-3FC632719B47}" src="https://github.com/user-attachments/assets/c5099f1d-9b2a-4ae6-aca6-1f5b91710d20" />

**Важно - перед удалением пользователя у него не должно быть задач**

<img width="1920" height="991" alt="{639A950B-9E50-4321-BCA1-9EEC17F7CB13}" src="https://github.com/user-attachments/assets/0af973c9-791d-4503-92f0-c06491ccb68d" />

<img width="1920" height="993" alt="{F06B4688-B6FC-4AE1-A4EE-96E3BA72382B}" src="https://github.com/user-attachments/assets/f4f18e89-a25b-4cdd-a9dd-4db758997416" />

<img width="1920" height="996" alt="{E9AA434C-28FC-4A6A-A476-0408948F6E64}" src="https://github.com/user-attachments/assets/e74829c6-a6e7-4d69-8ebf-22da3abd7372" />

<img width="1920" height="994" alt="{C9120777-4E5E-4917-8538-061075183C79}" src="https://github.com/user-attachments/assets/4d27d373-4bd2-4393-a13b-ef2dd8b161e0" />

<img width="1920" height="994" alt="{8ABDDA09-DFBD-4306-B5FB-8B81C4D06580}" src="https://github.com/user-attachments/assets/3894dfb7-5418-4bc7-8d5a-4555a1fe80f7" />

<img width="1920" height="995" alt="{F52B821F-B9CF-410C-B489-63A0A7F775A0}" src="https://github.com/user-attachments/assets/c93692e6-ea59-4780-9ae9-28fc62055593" />

<img width="1920" height="991" alt="{E2619DC5-3E9F-4F4A-AF84-119B3B2AACD2}" src="https://github.com/user-attachments/assets/0081d94a-5803-4289-bf7b-c084a9d743a1" />






















