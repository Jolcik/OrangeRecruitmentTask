Zadanie zaimplementowałem w frameworku Spring. Algorytm został zaimplementowany w klasie `CalendarServiceImpl`. Wywołać go można ręcznie w kodzie (np. w teście jednostkowym) lub przy włączonej aplikacji można wysłać request: `POST /api/possible-meeting-dates`. 

Ciało requesta w formacie JSON odpowiadające przykładowym danym z maila wyglądają następująco:
```
{
	"meetingDuration": "00:30",
	"firstCalendar": {
		"workingHours": {
			"start": "09:00",
			"end": "20:00"
		},
		"plannedMeetings": [
			{
				"start": "09:00",
				"end": "10:30"
			},
			{
				"start": "12:00",
				"end": "13:00"
			},
			{
				"start": "16:00",
				"end": "18:30"
			}
		]
	},
	"secondCalendar": {
		"workingHours": {
			"start": "10:00",
			"end": "18:30"
		},
		"plannedMeetings": [
			{
				"start": "10:00",
				"end": "11:30"
			},
			{
				"start": "12:30",
				"end": "14:30"
			},
			{
				"start": "14:30",
				"end": "15:00"
			},
			{
				"start": "16:00",
				"end": "17:00"
			}
		]
	}
}
```

Uzyskana odpowiedź (w przykładowym wyniku z maila jest błąd, trzeci termin koliduje z pierwszym kalendarzem):
```
[
    [
        "11:30",
        "12:00"
    ],
    [
        "15:00",
        "16:00"
    ]
]
```
