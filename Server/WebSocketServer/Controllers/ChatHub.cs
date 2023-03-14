using System.Collections.Concurrent;
using System.Threading.Tasks;
using Microsoft.AspNetCore.SignalR;

namespace WebSocketServer.Controllers;

public class ChatHub : Hub
{
  public static ConcurrentDictionary<string, string> Users = new();

  public async Task Connected(string phone)
  {
    Users[phone] = Context.ConnectionId;
    await Clients.All.SendAsync("UserConnected", phone);
  }

  public async Task SendMessage(string user, string message)
  {
    await Clients.All.SendAsync("ReceiveMessage", user, message);
  }
}