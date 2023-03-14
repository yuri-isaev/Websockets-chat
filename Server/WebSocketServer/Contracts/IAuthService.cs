using System.Threading.Tasks;
using MongoDB.Driver;
using WebSocketServer.Domains;

namespace WebSocketServer.Contracts;

public interface IAuthService
{
  Task CreateAsync(User user);

  Task<IMongoCollection<User>> GetCollectionAsync();
}
