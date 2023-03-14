package main

import "fmt"

func main() {
	mapUsers()
	// initMap()
}

func initMap() {
	// создание мапы с нужной емкостью, для того чтобы не расширять её в рантайме и выделять дополнительную память
	profile := make(map[string]string, 10)
	fmt.Println(profile) // map[]
}

func mapUsers() {
	users := map[string]int{
		"Vasya":  15,
		"Petya":  23,
		"Kostya": 48,
	}
	// !! Особенность
	// при выводе значения мапы, она будет отсортирована функцией fmt
	// но сама мапа - это не отсортированная структура данных
	fmt.Println(users)

	// !! Особенность
	name := users["Pavel"]
	fmt.Println(name) // если ключа нет - вернется значение по умолчанию для типа -> 0

	// проверка на сушествование ключа
	_, exist := users["Pavel"]
	fmt.Println(name, exist) // 0 false

	// проверка
	name, ex := users["Petya"]
	fmt.Println(name, ex) // 23 false

	// проверка
	age, ex := users["Kostya"]
	if ex {
		fmt.Println("Kostya", age) // Kostya 48
	} else {
		fmt.Println("not exists")
	}

	// перебор содержимого
	for key, value := range users {
		fmt.Println(key, value)
	}

	// удаление ключа
	delete(users, "Vasya")
	fmt.Printf("%v\n", users) // map[Kostya:48 Petya:23]
}
