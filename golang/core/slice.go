package main

/**
Panic — это встроенная функция, которая останавливает обычный поток управления и начинает паниковать.
Когда функция F вызывает panic, выполнение F останавливается,
все отложенные вызовы в F выполняются нормально,
затем F возвращает управление вызывающей функции.
Для вызывающей функции вызов F ведёт себя как вызов panic.
Процесс продолжается вверх по стеку, пока все функции в текущей го-процедуре не завершат выполнение,
после чего аварийно останавливается программа.
Паника может быть вызвана прямым вызовом panic,
а также вследствие ошибок времени выполнения, таких как доступ вне границ массива.

Recover — это встроенная функция, которая восстанавливает контроль над паникующей го-процедурой.
Recover полезна только внутри отложенного вызова функции. Во время нормального выполнения,
recover возвращает nil и не имеет других эффектов.
Если же текущая го-процедура паникует, то вызов recover возвращает значение,
которое было передано panic и восстанавливает нормальное выполнение.
*/

import (
	"errors"
	"fmt"
	"reflect"
	"unsafe"
)

func main() {
	// panic вызывается при случае выхода из функции при возникновении ошибки
	// defer handlerPanic()
	// arr1 := [3]string{"1", "2", "3"}   // array initial #1
	// arr2 := [...]string{"1", "2", "3"} // array initial #1

	// initSliceExpirience()

	// getSliceMemory()

	initSliceEx()

	// master()

	// getMemStats
}

func getSliceMemory() {
	nums := []int{1, 2, 3, 4, 5}
	sl1 := nums[1:4] // 2, 3, 4
	sl2 := nums[:2]  // 1, 2
	sl3 := nums[2:]  // 3, 4, 5
	fmt.Println(sl1, sl2, sl3)
}

func initSliceExpirience() {
	s1 := make([]int, 0, 1024)
	fmt.Printf("Slice #s1: %v\n", s1)

	// slice0 := make([]int, 0, 10)      // slice1 initial #0 -> значение не присвоено но память зарезервированна в размере 80 байт
	slice1 := []string{"1", "2", "3"} // slice1 initial #1
	slice2 := make([]string, 5, 10)   // slice1 initial #2

	fmt.Println(slice2, len(slice2), cap(slice2)) // [    ] 5 10

	fmt.Printf("Slice #2: %v\n", slice2) // Slice #2: [    ]

	// fmt.Printf("Slice #0 ->: %v\n", slice0[0]) // panic: runtime error: index out of range [0] with length 0

	// добавление другого слайса
	slice3 := make([]string, 3)
	slice2 = append(slice2, slice3...)
	fmt.Println(slice2, len(slice2), cap(slice2)) // [       ] 8 10

	slice4 := append(slice2, "test")
	fmt.Println(slice4, len(slice4), cap(slice4)) // [        test] 9 10

	slice4 = append(slice4, "test2", "test3")     // создает новый массив с объемом в два раза больше основного массива
	fmt.Println(slice4, len(slice4), cap(slice4)) // [        test test2 test3] 11 20

	err := printMessage2(slice1)
	if err != nil {
		return
	}

	fmt.Println(slice1) // [1 5 3]

	// перебор элементов массива
	for _, s := range slice1 {
		fmt.Println(s)
	}
}

func handlerPanic() {
	if r := recover(); r != nil {
		fmt.Println(r)
	}
	fmt.Println("Panic handler")
}

// функция с копией массива msg
func printMessage(messages [3]string) error {
	if len(messages) == 0 {
		return errors.New("error")
	}

	messages[1] = "5"
	fmt.Println(messages)

	return nil
}

// функция с копией среза msg -> работает с сылкой исходного массива
func printMessage2(messages []string) error {
	if len(messages) == 0 {
		return errors.New("error")
	}

	messages[1] = "5"
	fmt.Println(messages) // [1 5 3]

	return nil
}

func initSliceEx() {
	initSlice := make([]int, 0, 10)
	for i := 0; i < 10; i++ {
		initSlice = append(initSlice, i)
	}
	fmt.Printf("| cut initSlice %v, len: %d, cap = %d\n", initSlice, len(initSlice), cap(initSlice))

	fmt.Println("----")
	cut := initSlice[2:4]
	fmt.Printf("| cut slice %v, len: %d, cap = %d\n", cut, len(cut), cap(cut))
	// nums := make([]int, 1, 2)
	// fmt.Println(nums)
	// reflect.AppendSlice(nums, 1024)
	// fmt.Println(nums)
	// mutateSlice(nums, 1, 512)
	fmt.Println("----")
	cut = cut[:cap(cut)] // создаем длинну равную объёму
	// _ = cut[2]
	fmt.Printf("| cut extended %v, len: %d, cap = %d\n", cut, len(cut), cap(cut))

	fmt.Println("----")
	fmt.Printf("| initSlice = %v, sh = %v, sp = %d\n",
		initSlice,
		*(*reflect.SliceHeader)(unsafe.Pointer(&initSlice)),
		reflect.ValueOf(initSlice).Pointer(),
	)

	fmt.Println("----")
	fmt.Printf("| cut = `%v`, sh = %v, sp = %d\n",
		cut,
		*(*reflect.SliceHeader)(unsafe.Pointer(&cut)),
		reflect.ValueOf(cut).Pointer(),
	) // cut = [2 3 4 5 6 7 8 9], sh = {824633787584 8 8}, sp = 824633787584

	fmt.Println("----")
	cut = append(cut, 1<<10) // теперь `cut` самостоятельная ячейка в памяти
	// cut[0] = 1 << 18

	fmt.Printf("| cut = %v, sh = %v, sp = %d\n",
		cut,
		*(*reflect.SliceHeader)(unsafe.Pointer(&cut)),
		reflect.ValueOf(cut).Pointer(),
	) // cut = [2 3 4 5 6 7 8 9 1024], sh = {824634769792 9 16}, sp = 824634769792
}

func master() {
	nums := make([]int, 0, 2) // [] Len 0, cap 2
	fmt.Println(nums, len(nums), cap(nums))

	nums = appendSlice(nums, 1024)
	fmt.Println(nums, len(nums), cap(nums)) // [1024] 1 2

	mutateSlice(nums, 0, 512)
	fmt.Println(nums, len(nums), cap(nums)) // [1024] 1 2
}

func appendSlice(sl []int, value int) []int {
	sl = append(sl, value)
	return sl
}

func mutateSlice(sl []int, idx, value int) {
	sl2 := make([]int, 2, cap(sl))
	copy(sl2, sl)
	// sl2 := sl
	sl2[idx] = value
	fmt.Println(sl2, len(sl2), cap(sl2)) // [512 0] 2 2
}
