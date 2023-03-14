namespace WebSocketServer.Models.Output;

public class ServerResponse
{
  public string Status { get; set; }
  public string Message { get; set; }

  public ServerResponse(string status, string message)
  {
    Status = status;
    Message = message;
  }
}
