using System.Threading.Tasks;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using WebSocketServer.Contracts;
using WebSocketServer.Domains;
using WebSocketServer.Options;

namespace WebSocketServer.Persistence;

public class AuthService : IAuthService
{
  readonly IMongoCollection<User> _userCollection;

  public AuthService(IOptions<MongoDbOptions> dbSettings)
  {
    var mongoClient = new MongoClient(dbSettings.Value.ConnectionString);
    var mongoDatabase = mongoClient.GetDatabase(dbSettings.Value.DatabaseName);
    _userCollection = mongoDatabase.GetCollection<User>(dbSettings.Value.UserCollection);
  }

  public async Task CreateAsync(User user) => await _userCollection.InsertOneAsync(user);

  public Task<IMongoCollection<User>> GetCollectionAsync() => Task.FromResult(_userCollection);
}
