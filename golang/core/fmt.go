package main

/**
*Пакет "fmt" предоставляет функции для форматирования и вывода данных.
*Он используется для отображения значений в консоли или другом устройстве вывода.
*Функции из пакета "fmt" позволяют форматировать строки, числа, булевые значения и другие типы данных для последующего вывода.
 */
import "fmt"

func main() {
	funcName("hello")
	funcName("привет")
}

// Строка состоит не из байт, а из 'рун'
func funcName(s string) {
	value := []rune(s) // явное приведение типа
	counter := 0

	for index, letter := range value {
		counter++
		fmt.Printf("index: %v letter: %c\n", index, letter)
	}

	fmt.Printf("counter value: %d\n", counter)
	fmt.Println(len(value))

	/**
	index: 0 letter: h
	index: 1 letter: e
	index: 2 letter: l
	index: 3 letter: l
	index: 4 letter: o
	counter value: 5
	5
	index: 0 letter: п
	index: 1 letter: р
	index: 2 letter: и
	index: 3 letter: в
	index: 4 letter: е
	index: 5 letter: т
	counter value: 6
	6
	*/
}
