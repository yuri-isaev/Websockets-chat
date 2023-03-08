package main

import (
	"fmt"
	"strconv"
)

const (
	first  = 1
	second = 2
	third  = 3
)

func main() {
	multiplerFunc(first, second, third)

	sumFunc := func(x, y int) int {
		return x + y
	}
	fmt.Println(calculate(first, second, sumFunc))

	subtractFunc := func(x, y int) int {
		return x - y
	}
	fmt.Println(calculate(first, second, subtractFunc))

	// делегирование функции
	divideBy2 := createDivider(2)   // divider = 2
	divideBy10 := createDivider(10) // divider = 10
	fmt.Println(divideBy2(100))     // x = 100
	fmt.Println(divideBy10(10))     // x = 10

	// анонимные функции

}

func calculate(x, y int, callback func(x, y int) int) int {
	return callback(x, y)
}

func multiplerFunc(x, y, z int) {
	// var multipler func(x, y, z int) int

	multipler := func(x, y, z int) int {
		return x * y * z
	}

	fmt.Println(strconv.Itoa(multipler(x, y, z)))
}

func createDivider(divider int /* 2 */) func(x int) int {
	return func(x int) int {
		return x / divider /* 2 */
		/** 50, 1 */
	}
}
