package main

import (
	"fmt"
	"net/http"
	"sync"
	"time"
)

func main() {
	urls := []string{
		"https://google.com",
		"https://youtube.com",
		"https://github.com",
		"https://ru.wikipedia.org",
		"https://habr.com/ru/all/",
	}

	var wg sync.WaitGroup

	for _, url := range urls {
		wg.Add(1)

		go func(url string) {
			doHTTP(url)
			wg.Done()
		}(url)
	}

	wg.Wait()
}

func doHTTP(url string) {
	t := time.Now()

	resp, err := http.Get(url)
	if err != nil {
		fmt.Printf("Failed to get <%s>: $%s\n", url, err.Error())
	}

	defer resp.Body.Close()

	fmt.Printf("<%s> - Status Code [%d] - delay %d ms \n",
		url, resp.StatusCode, time.Since(t).Microseconds())
}
