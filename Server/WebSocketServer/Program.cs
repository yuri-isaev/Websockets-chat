using System;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.WebSockets;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using WebSocketServer.Contracts;
using WebSocketServer.Controllers;
using WebSocketServer.Options;
using WebSocketServer.Persistence;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllers();
builder.Services.AddWebSockets(options => options.KeepAliveInterval = TimeSpan.FromMinutes(2));
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddSignalR();

builder.Services.AddCors(options =>
{
  options.AddPolicy("CorsPolicy", policyBuilder =>
  {
    policyBuilder
      //.AllowAnyOrigin()
      //.AllowCredentials()
      .WithOrigins("http://chat.isaev.com")
      .AllowAnyHeader()
      .AllowAnyMethod();
  });
});

builder.Services.Configure<MongoDbOptions>(builder.Configuration.GetSection("MongoDB"));

#region Dependency container

builder.Services.AddScoped<IAuthService, AuthService>();

#endregion

// Configure the HTTP request pipeline.
var app = builder.Build();

if (app.Environment.IsDevelopment())
{
  app.UseSwagger();
  app.UseSwaggerUI();
  app.UseDeveloperExceptionPage();
}

app.UseWebSockets();
app.UseCors("CorsPolicy");
app.UseRouting();
app.UseAuthorization();

app.UseEndpoints(endpoints =>
{
  endpoints.MapControllers();
  endpoints.MapHub<ChatHub>("/chatHub");
});

// Add this line to make the application listen on all network interfaces
app.Urls.Add("http://0.0.0.0:5206");

app.Run();
