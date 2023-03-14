using System.ComponentModel.DataAnnotations;

namespace WebSocketServer.Models.Input;

public class RegistrationRequest
{
  [Required(ErrorMessage = "User name is required")]
  [StringLength(50, MinimumLength = 3, ErrorMessage = "User name must be between 3 and 50 characters")]
  public string UserName { get; set; } = null!;

  [Phone(ErrorMessage = "Invalid phone number")]
  [Required(ErrorMessage = "Phone number is required")]
  public string Phone { get; set; } = null!;

  [Required(ErrorMessage = "Password is required")]
  [StringLength(100, MinimumLength = 6, ErrorMessage = "Password must be between 6 and 100 characters")]
  public string Password { get; set; } = null!;
}