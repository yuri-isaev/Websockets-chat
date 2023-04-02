// use std::thread::sleep;
// use log::Level;
use tokio::io::{AsyncReadExt, AsyncWriteExt};
use tokio::net;
// use tokio::net::{TcpListener, TcpStream};
// use tokio::process::Command;

// fn fib(n: i32) -> i32 {
//     return if n <= 0 {
//         0
//     } else if n == 1 {
//         1
//     } else {
//         fib(n - 1) + fib(n - 2)
//     }
// }

// #[tokio::main]
// async fn main() {
//     simple_logger::init_with_level(Level::Info).unwrap();
//     let start = std::time::Instant::now();
//     run().await;
//     let end = std::time::Instant::now();
//     println!("FINISH -> {:?} seconds", end - start);
// }

// async fn run() {
//     sleeper().await;
//     reader().await;
// }

// async fn run() {
//     tokio::join!(
//         sleeper(),
//         reader(),
//         reader(),
//         reader(),
//         reader(),
//         reader(),
//         reader(),
//         reader(),
//         reader(),
//         reader(),
//         reader(),
//    );
// }

// async fn sleeper() {
//     log::info!("Sleeping");
//     tokio::time::sleep(tokio::time::Duration::from_secs(10)).await;
//     log::info!("Awake!");
// }
//
// async fn reader() {
//     log::info!("Reading some data");
//     let mut f = tokio::fs::File::open("res/file.csv").await.unwrap();
//     let mut vector = vec![];
//     f.read_to_end(&mut vector).await.unwrap();
//     log::info!("Read file {} bytes", vector.len());
//
//     // нагрузка на исполнение в 6 sec
//     comp_fib().await;
//
//     //fib(40); // нагрузка на исполнение в 28 sec
// }
//
// async fn comp_fib() {
//     // преобразование неасинхронной в ассинхроную функцию
//     tokio::task::spawn_blocking(move || {
//         log::info!("Computing fib(40)");
//         fib(40);
//         log::info!("Done computing fib(40)");
//     }).await.unwrap();
// }

// /**
// INFO  [toki] Do something
// INFO  [toki] Sleeping
// INFO  [toki] Awake!
// INFO  [toki] Computing fib(40)
// INFO  [toki] Done computing fib(40)
// INFO  [toki] fun was had
// FINISH -> 8.8126885s seconds
//  */

// на случай если необходимо выполнить будущее, но не ждать его завершение
// async fn run() {
//     tokio::spawn(async {
//         delay().await;
//     });
//     do_something_fun().await;
// }
//
// async fn do_something_fun() {
//     log::info!("Do something");
//     tokio::time::sleep(tokio::time::Duration::from_secs(6)).await;
//     tokio::task::spawn_blocking(move || {
//         log::info!("Computing fib(40)");
//         fib(40);
//         log::info!("Done computing fib(40)");
//     }).await.unwrap(); // нагрузка на исполнение в 3 sec
//     log::info!("fun was had");
// }
//
// async fn delay() {
//     log::info!("Sleeping");
//     tokio::time::sleep(tokio::time::Duration::from_secs(1)).await;
//     log::info!("Awake!");
// }

// tokio channel // sender <--> receiver
// #[tokio::main]
// async fn main() {
//     let (sender, mut receiver) = tokio::sync::mpsc::channel(10);
//
//     tokio::spawn(async move {
//         for i in 0..10 {
//             sender.send(i).await.unwrap();
//         }
//     });
//
//     while let Some(value) = receiver.recv().await{
//         println!("=> {}", value);
//     }
// }


// tokio net
#[tokio::main]
async fn main() {
    let host = "localhost:8080";
    let srv = net::TcpListener::bind(host).await.unwrap();

    loop {
        // Accept a new connection
        let (mut sock, _) = srv.accept().await.unwrap();

        // Spawn a new task to handle the connection
        tokio::spawn(async move {
            let mut buf = [0; 1024];
            let z = sock.read(&mut buf).await.unswap();
            sock.write_all(&buf[0..z]).await.unswap();

            let data = str::from_utf8(&buf[0..z]).unswap();
            println!("Echoed: {:?}", data);
            sock.shutdown().await.unswap();
        });
    }
}
//
// use tokio::prelude::*;
// use tokio::net::TcpStream;
//
// #[tokio::main]
// async fn main() -> Result<(), Box<dyn std::error::Error>> {
//     let mut stream = TcpStream::connect("localhost:8080").await?;
//     let (mut read, mut write) = tokio::io::split(stream);
//
//     tokio::spawn(async move {
//         loop {
//             let mut buf = [0u8; 32];
//             read.read(&mut buf).await.unwrap();
//             println!("{:?}", std::str::from_utf8(&buf));
//         }
//     });
//
//     Ok(())
// }

// #[tokio::main]
// async fn main() {
//     let listener = TcpListener::bind("127.0.0.1:6379").await.unwrap();
//
//     loop {
//         let (socket, _) = listener.accept().await.unwrap();
//         // A new task is spawned for each inbound socket. The socket is
//         // moved to the new task and processed there.
//         tokio::spawn(async move {
//             process(socket).await;
//         });
//     }
// }
//
// async fn process(socket: TcpStream) {
//     use std::collections::HashMap;
//
//     // A hashmap is used to store data
//     let mut db = HashMap::new();
//
//     // Connection, provided by `mini-redis`, handles parsing frames from
//     // the socket
//     let mut connection = Connection::new(socket);
//
//     // Use `read_frame` to receive a command from the connection.
//     while let Some(frame) = connection.read_frame().await.unwrap() {
//         let response = match Command::from_frame(frame).unwrap() {
//             Set(cmd) => {
//                 // The value is stored as `Vec<u8>`
//                 db.insert(cmd.key().to_string(), cmd.value().to_vec());
//                 Frame::Simple("OK".to_string())
//             }
//             Get(cmd) => {
//                 if let Some(value) = db.get(cmd.key()) {
//                     // `Frame::Bulk` expects data to be of type `Bytes`. This
//                     // type will be covered later in the tutorial. For now,
//                     // `&Vec<u8>` is converted to `Bytes` using `into()`.
//                     Frame::Bulk(value.clone().into())
//                 } else {
//                     Frame::Null
//                 }
//             }
//             cmd => panic!("unimplemented {:?}", cmd),
//         };
//
//         // Write the response to the client
//         connection.write_frame(&response).await.unwrap();
//     }
// }

