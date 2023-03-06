package main

import "fmt"

func main() {
	a := 2
	b := &a
	*b = 3
	c := &b
	fmt.Println(b) // 0xc00001c098
	fmt.Println(c) // 0xc00000a028

	// получение указателя на переменную типа int
	// инициализированного значением по-умолчанию
	d := new(int)  // = 0
	fmt.Println(d) // 0xc00001c0d0 / 0
	*d = 12
	fmt.Println(d) // 0xc00001c0d0 / 12
	*c = d
	fmt.Println(c) //  0xc00000a028 / 0xc00001c0d0
	*d = 13        // c,a - не изменились
	fmt.Println(d) // 0xc00001c0d0 / 13

	c = &d
	// c = 14 // -> '14' (type untyped int) cannot be represented by the type **in
	fmt.Println(c) // 0xc00000a038 / 0xc00001c0d0
}
