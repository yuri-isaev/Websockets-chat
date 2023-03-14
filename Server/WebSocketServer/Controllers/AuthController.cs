using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;
using WebSocketServer.Contracts;
using WebSocketServer.Domains;
using WebSocketServer.Models.Input;
using WebSocketServer.Models.Output;

namespace WebSocketServer.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController : ControllerBase
{
  readonly IAuthService _userService;

  public AuthController(IAuthService userService)
  {
    _userService = userService;
  }

  [HttpPost("Registration")]
  public async Task<IActionResult> Registration([FromBody] RegistrationRequest request)
  {
    var usersCollection = await _userService.GetCollectionAsync();

    var user = await usersCollection.Find(u => u.Phone == request.Phone).FirstOrDefaultAsync();

    if (user != null)
    {
      return BadRequest(new ServerResponse("error", "The phone already exists."));
    }

    var newUser = new User
    {
      UserName = request.UserName,
      Phone = request.Phone,
      Password = BCrypt.Net.BCrypt.HashPassword(request.Password),
      CreatedAt = DateTime.UtcNow
    };

    await _userService.CreateAsync(newUser);

    return Ok(new ServerResponse("success", "Registration successful."));
  }
}
