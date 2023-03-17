package main

import "fmt"

func main() {
	// Nil - значение по указателю
	var intPointer *int
	// *int (*int)(nil)
	fmt.Printf("Type:%T\nValue:%#v\n\n", intPointer, intPointer)

	// panic: runtime error: invalid memory address or nil pointer dereference
	// fmt.Printf("Type:%T\nValue:%#v\n\n", intPointer, *intPointer)

	// nil pointer panic
	// fmt.Printf("%T %#v %#v \n", intPointer, intPointer, *intPointer)

	// Получение not-nil указателей
	var a int64 = 7
	fmt.Printf("Type: %T\nValue: %#v\n\n", a, a)

	// Разыменование указателя для получение его значения
	var pointerA *int64 = &a
	fmt.Printf("Type: %T\nPointer: %v\nValue: %v\n\n", pointerA, pointerA, *pointerA) // *int64 0xc0000a6090 7

	// получить указатель через новое ключевое слово
	var newPointer = new(float32)
	fmt.Printf("Type: %T\nPointer: %v\nValue: %v\n\n", newPointer, newPointer, *newPointer)

	*newPointer = 3
	fmt.Printf("Type: %T\nPointer: %v\nValue: %v\n\n", newPointer, newPointer, *newPointer)

	/**
	Type:*int
	Value:(*int)(nil)

	Type: int64
	Value: 7

	Type: *int64
	Pointer: 0xc0000a6090
	Value: 7

	Type: *float32
	Pointer: 0xc0000a6098
	Value: 0

	Type: *float32
	Pointer: 0xc0000a6098
	Value: 3
	*/

	//
	// // pointers usage
	// // side effects
	num := 3
	square(num)
	fmt.Println(num) // 3

	res := square1(num)
	fmt.Println(res) // 9

	squarePointer(&num)
	fmt.Println(num)

	//
	// empty value flag
	var wallet1 *int
	fmt.Println(hasWallet(wallet1))

	wallet2 := 0
	fmt.Println(hasWallet(&wallet2))

	wallet3 := 100
	fmt.Println(hasWallet(&wallet3))
}

func square(num int) {
	num *= num
}

// 1
func square1(num int) int {
	return num * num
}

func squarePointer(num *int) {
	*num *= *num
}

func hasWallet(money *int) bool {
	return money != nil
}
