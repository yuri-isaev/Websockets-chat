package main

import (
	"fmt"
	"time"
)

// параллели́зм — это свойство систем, при котором несколько вычислений выполняются одновременно,
// и при этом, возможно, взаимодействуют друг с другом.
//
// Примечание. нередко путаются термины «параллелизм» и «конкурентность».
// Оба термина означают одновременность процессов,
// но параллелизм — на физическом уровне (параллельное исполнение нескольких процессов,
// нацеленное только на повышение скорости исполнения за счёт использования соответствующей аппаратной поддержки),
// а конкурентность — на логическом (парадигма проектирования систем, идентифицирующая процессы как независимые,
// что в том числе позволяет их исполнять физически параллельно,
// но в первую очередь нацелено на упрощение написания многопоточных программ и повышение их устойчивости).

// func main() {
// 	// s := []int{7, 2, 8, -9, 4, 0}
// 	// c := make(chan int)
// 	//
// 	// go sum(s[:len(s)/2], c)
// 	// go sum(s[len(s)/2:], c)
// 	// x, y := <-c, <-c // receive from c
// 	//
// 	// fmt.Println(x, y, x+y)
//
// 	result2 := <-myAsyncFunction2()
//
// 	result := <-myAsyncFunction1()
// 	// outputs `2` after two seconds
//
// 	fmt.Println(result2)
// 	fmt.Println(result)
// }

func sum(s []int, c chan int) {
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum // send sum to c
}

func myAsyncFunction1() <-chan int32 {
	result := make(chan int32)
	go func() {
		defer close(result)
		// func() core (meaning, the operation to be completed)
		time.Sleep(time.Second * 8)
		result <- 1
	}()
	return result
}

func myAsyncFunction2() <-chan int32 {
	result := make(chan int32)
	go func() {
		defer close(result)
		// func() core (meaning, the operation to be completed)
		time.Sleep(time.Second * 4)
		result <- 2
	}()
	return result
}

// // Promise.all
// func main() {
// 	firstChannel, secondChannel := asyncFunction(2), asyncFunction(3)
//
// 	first, second := <-firstChannel, <-secondChannel
//
// 	// outputs `2, 3` after three seconds
// 	fmt.Println(first, second)
// }
//

// func asyncFunction(s int32) <-chan int32 {
// 	r := make(chan int32)
// 	go func() {
// 		defer close(r)
// 		time.Sleep(time.Second * 2)
// 		r <- s
// 	}()
// 	return r
// }

// Promise.race
// func main() {
// 	var r int32
// 	select {
// 	case r = <-asyncFunction(2):
// 	case r = <-asyncFunction(3):
// 	}
// 	// outputs `2` after three seconds
// 	fmt.Println(r)
// }
//
// func asyncFunction(s int32) <-chan int32 {
// 	r := make(chan int32)
// 	go func() {
// 		defer close(r)
// 		time.Sleep(time.Second * 2)
// 		r <- s
// 	}()
// 	return r
// }

// Самое интересное в каналах то, что вы можете использовать select инструкцию Go для реализации шаблонов параллелизма
// и ожидать выполнения операций с несколькими каналами.
// Во фрагменте выше мы используем select для ожидания обоих значений одновременно,
// выбирая, в данном случае, первое, которое поступает: еще раз, 2 всегда возвращается до того,
// как появится значение, извлеченное из канала, заполненного asyncFunction(3).
//
// Однако мы видели, что базовые отправки и получения по каналам блокируются.
// Мы можем использовать select с default предложением для реализации неблокирующих отправок,
// приемов и даже неблокирующих многоходовых выборок.

func main() {
	messages := make(chan string)
	signals := make(chan bool)

	select {
	case msg := <-messages:
		fmt.Println("received message", msg)
	default:
		fmt.Println("no message received")
	}

	msg := "hi"
	select {
	case messages <- msg:
		fmt.Println("sent message", msg)
	default:
		fmt.Println("no message sent")
	}

	select {
	case msg := <-messages:
		fmt.Println("received message", msg)
	case sig := <-signals:
		fmt.Println("received signal", sig)
	default:
		fmt.Println("no activity")
	}
}
