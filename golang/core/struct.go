package main

import "fmt"

// Age - custom type
type Age int

func (a Age) isAdult() bool {
	return a >= 18
}

type User struct {
	name   string
	age    Age
	gender string
	weight int
	height int
}

// метод связи со струтурой
func (u User) getName() string {
	return u.name
}

func (u *User) setName(name string) {
	u.name = name
}

func main() {
	user := User{"Vasya", 23, "Male", 75, 185}
	fmt.Printf("%+v\n", user)
	user.setName("Igor")
	fmt.Printf("%+v\n", user)
}
