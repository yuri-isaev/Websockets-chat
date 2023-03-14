namespace WebSocketServer.Options;

public class MongoDbOptions
{
  public string ConnectionString { get; set; } = null!;
  public string DatabaseName { get; set; } = null!;
  public string UserCollection { get; set; } = null!;
}
