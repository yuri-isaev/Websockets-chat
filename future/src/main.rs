use std::{thread, time};
use std::time::Instant;

use futures::future::join_all;

use async_std;
use futures::executor::block_on;

/*
number 1 is running
number 2 is running
time elapsed for join vec 2.0074893s
Here is the result: [2, 2]
 */

fn main() {
    let outcome = async {
        let mut future_vec = Vec::new();
        let future_first = delay(1);
        let future_second = delay(2);

        future_vec.push(future_first);
        future_vec.push(future_second);

        let handles = future_vec         // вектор заполенный futures
            .into_iter()                 // функция вызывает итератор для цикла через фьючерсы
            .map(async_std::task::spawn) // функция порождает асинхронную задачу в том же потоке
            // таким образом одновременно запускаются оба фьючерса в одном и том же потоке
            .collect::<Vec<_>>();        // функция collect для сбора результатов этого отображения в вектор под названием handles

        let results = join_all(handles).await;
        return results;
    };
    let now = Instant::now();
    let result = block_on(outcome);
    println!("time elapsed for join vec {:?}", now.elapsed());
    println!("Here is the result: {:?}", result);
}

async fn delay(number: i8) -> i8 {
    println!("number {} is running", number);
    let two_seconds = time::Duration::new(2, 0);
    thread::sleep(two_seconds);
    return 2;
}
