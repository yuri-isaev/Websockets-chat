using System;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;

namespace WebSocketServerTest
{
    public class Startup
    {
        public void ConfigureServices(IServiceCollection services)
        {}
        
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            app.UseWebSockets(); // 1rd request delegate

            app.Use(async (context, next) =>
            {
                WriteRequestParam(context);
                
                if (context.WebSockets.IsWebSocketRequest)
                {
                    await context.WebSockets.AcceptWebSocketAsync();
                    Console.WriteLine("WebSocket connected");
                }
                else
                {
                    Console.WriteLine("Hello from 2rd request delegate");
                    await next();
                }
            });

            app.Run(async httpContext =>
            {
                Console.WriteLine("Hello from 3rd request delegate");
                await httpContext.Response.WriteAsync("Hello from 3rd request delegate");
            });
        }

        public void WriteRequestParam(HttpContext httpContext)
        {
            Console.WriteLine("Request method: " + httpContext.Request.Method);
            Console.WriteLine("Request protocol: " + httpContext.Request.Protocol);

            foreach (var header in httpContext.Request.Headers)
            {
                Console.WriteLine("--> " + header.Key + " : " + header.Value);
            }
        }
    }
}
