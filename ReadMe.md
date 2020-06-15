Конвертер валют
======
На главном экране вы можете выбрать валюты для обмена из списка. Пока что поддерживаются только эти

<img src="https://github.com/freQuensy23-coder/converter/blob/master/screenshorts/main.jpg" data-canonical-src="https://github.com/freQuensy23-coder/converter/blob/master/screenshorts/main.jpg" width="200" height="400" />


Так же вы можете указать цену вручную. 
В случае, если вы используете ценуу из БД, то кнопка *отразить* заменит курс на обратный
Если вводите курс вручную, то цена валюты возведётся в -1 стпень.
Для учёта комиссии есть отдельный чекбокс. Вам необходимо ввести 2 значения: процент и минимальное число. Меньше него во второй валюте не может опускаться комиссия. Например при конвертировании 7500 из RUB -> USD при комисси в 1% не менее чем 5 вы получите результат 95, т.к. 1% = 1$ < минимальная комиссия = 5$.

Во интернет вы можете обновить БД курсов (Пока недоступно()

<img src="https://github.com/freQuensy23-coder/converter/blob/master/screenshorts/internet.jpg" data-canonical-src="https://github.com/freQuensy23-coder/converter/blob/master/screenshorts/internet.jpg" width="200" height="400" />

![internet](https://github.com/freQuensy23-coder/converter/blob/master/screenshorts/internet.jpg=200x100)
Во вкладке уведомлеения вы сможете настроить предупреждение о сильном изменении курса или просто постоянно получать информацию о курсе (Пока недоступно()

<img src="https://github.com/freQuensy23-coder/converter/blob/master/screenshorts/notification.jpg" data-canonical-src="https://github.com/freQuensy23-coder/converter/blob/master/screenshorts/notification.jpg" width="200" height="400" />
