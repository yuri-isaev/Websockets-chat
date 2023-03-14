using System;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace WebSocketServer.Domains;

public class User
{
  [BsonId]
  [BsonRepresentation(BsonType.ObjectId)]
  public string Id { get; set; } = null!;

  public string UserName { get; set; } = null!;
  
  public string Phone { get; set; } = null!;
  
  public string Password { get; set; } = null!;
  
  public DateTime CreatedAt { get; set; }
}