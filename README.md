## Задание: HTTP сервер своими руками

Принцип работы:
1. Написать HTTP Server
2. Написать HTTP Client
3. Клиент отправляет [JSON](#пример-json)
4. Сервер:
   - Парсит JSON
   - Возвращает клиенту [HTML](#пример-html)
5. Клиент сохраняет файл на диске в формате HTML

### Пример JSON
```json
{
 "info": "salary.by",
 "employees": [
   {
     "id": "01",
     "name": "Иванов И.И.",
     "salary": 500,
     "tax": 200
   },
   {
     "id": "02",
     "name": "Петров П.П.",
     "salary": 1500,
     "tax": 100
   },
 ]
}
```

### Пример HTML
```html
<!DOCTYPE html>
<html lang="en">
    <head>
       <title>Salary</title>
    </head>
    <body>
    <table>
       <tr>
           <th>Total income</th>
           <th>Total tax</th>
           <th>Total profit</th>
       </tr>
       <tr>
           <td>${total_income}</td>
           <td>${total_tax}</td>
           <td>${total_profit}</td>
       </tr>
    </table>
    </body>
</html>
```
- total_income - суммарный доход
- total_tax- суммарный налог 
- total_profit - итоговая чистая прибыль